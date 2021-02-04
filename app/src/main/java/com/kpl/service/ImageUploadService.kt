package com.kpl.service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.CountDownTimer
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.kpl.R
import com.kpl.activity.HomeActivity
import com.kpl.network.CallbackObserver
import com.kpl.network.Networking
import com.kpl.utils.Constant
import com.kpl.utils.Logger
import com.kpl.utils.SessionManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONException
import org.json.JSONObject
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.concurrent.TimeUnit


class ImageUploadService : Service() {
    private val NOTIFICATION_ID = 123
    private val MICROSECONDS_IN_ONE_MINUTE: Long = 60000000
    var leftmillisec: Long = 10800000
    var timer: CountDownTimer? = null
    var isTracking: Boolean = false //to know when to disable all buttons on MainActivity = false
    var isResumeTracking: Boolean = false
    var seconds: Long = 0
    var index = 1f
    var sessionManager: SessionManager? = null
    var avgSpeed = 0f

    var disposable: Disposable? = null

    //Variables used in calculations
    private var stepCount = 0
    private val stepTimestamp: Long = 0
    private val startTime: Long = 0
    var timeInMilliseconds: Long = 0
    var elapsedTime: Long = 0
    var updatedTime: Long = 0
    private var distance = 0.0

    // Binder interface given to clients
    private val mBinder: IBinder = MyBinder()

    inner class MyBinder : Binder() {
        val service: ImageUploadService
            get() = this@ImageUploadService
    }

    override fun onBind(intent: Intent): IBinder? {
        return mBinder
    }

    override fun onRebind(intent: Intent) {
        super.onRebind(intent)
    }

    override fun onUnbind(intent: Intent): Boolean {
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()

        disposable?.dispose();
    }

    override fun onCreate() {
        super.onCreate()

    }

    override fun onStartCommand(
            intent: Intent,
            flags: Int,
            startId: Int
    ): Int {

        sessionManager = SessionManager(this)


        //this 'if command' executes when users tap on 'Stop Tracking' button on notification bar
        val isStopTracking = intent.getBooleanExtra("isTracking", true)


        startForeground(NOTIFICATION_ID, updatedNotification(distance))
        // cronJob()

        return START_STICKY
    }


    //Calculates the number of steps and the other calculations related to them
    private fun countSteps(step: Float) {

        //Step count
        // stepCount += step.toInt()
        stepCount++
        Logger.d("steps total====" + stepCount)
        //Distance calculation
        distance = stepCount * 0.8 //Average step length in an average adult
        val distanceString = java.lang.String.format("%.2f", distance)

        //send broadcast to display data on UI
        val intent = Intent()
        intent.putExtra("metre", roundUp(distance))
        //intent.putExtra("currentSpeed", roundUp(currentSpeed))
        //  intent.putExtra("avgSpeed", roundUp(avgSpeed))
        intent.putExtra("steps", stepCount.toString())
        intent.action = "metres_speed"
        sendBroadcast(intent)
    }


    fun cronJob() {
        disposable = Observable.interval(
                60000, 60000,
                TimeUnit.MILLISECONDS
        )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::data, this::onError)
    }

    fun data(long: Long) {
        sendEventActivity(
                sessionManager?.getDataByKeyInt(Constant.USER_ID)!!,
                stepCount.toString(),
                roundUp(distance)
        )
    }

    private fun onError(throwable: Throwable) {
        Toast.makeText(
                this, "OnError in Observable Timer",
                Toast.LENGTH_LONG
        ).show()
    }


    fun updatedNotification(totalMetres: Double): Notification {
        val notificationIntent =
                Intent(this@ImageUploadService, HomeActivity::class.java)
        val content =
                PendingIntent.getActivity(this@ImageUploadService, 0, notificationIntent, 0)

        //send the pending intent to itself to stop tracking(the 'if command' at line 62).
        val broadcastIntent =
                Intent(this, ImageUploadService::class.java)
        broadcastIntent.putExtra("Image Uploading", false)
        val pendingIntent = PendingIntent.getService(
                this,
                0,
                broadcastIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        )

        //create notification
        val notification =
                NotificationCompat.Builder(
                        this@ImageUploadService,
                        CHANNEL_ID
                )
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Sprinters is tracking your event")
                        .setContentText("You have run " + roundUp(totalMetres) + " km so far")
                        .setContentIntent(content)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setOnlyAlertOnce(true)
        //.addAction(R.drawable.login_logo, "Stop Tracking", pendingIntent)
        return notification.build()
    }

    fun initializeTimer(leftmillisecss: Long) {
        val totalSeconds: Long = 10800 // 3 hours max
        val intervalSeconds: Long = 1
        timer = object : CountDownTimer(leftmillisecss, intervalSeconds * 1000) {
            override fun onTick(millisUntilFinished: Long) {
                leftmillisec = millisUntilFinished
                //reverse counting down timer to counting up timer
                val min = (millisUntilFinished / (1000 * 60));
                // seconds = ((millisUntilFinished / 1000) - min * 60);
                seconds = (totalSeconds * 1000 - millisUntilFinished) / 1000

                //convert to hh:mm:ss format
                val formatString =
                        secondFormatString(seconds)


                //send broadcast to display data on UI
                val intent = Intent()
                intent.putExtra("totalTime", formatString)
                intent.action = "timer"
                sendBroadcast(intent)
            }

            override fun onFinish() {
                stopTracking()
            }
        }
    }

    fun stopTracking() {
        isTracking = false
        disposable?.dispose()


        //stop timer
        timerPause()

        stopForeground(true)
        stopSelf()

        /*  //start SaveActivity
        Intent i = new Intent(getApplicationContext(), SaveActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtras(bundle);
        startActivity(i);*/
    }


    fun resumeTracking() {
        cronJob()
        isTracking = true
        isResumeTracking = true

        initializeTimer(leftmillisec)
        timer!!.start()

        startForeground(NOTIFICATION_ID, updatedNotification(distance))

    }

    fun pauseTracking() {
        disposable?.dispose()
        isTracking = true
        isResumeTracking = true


        timerPause()
        stopForeground(true)

        /*  //start SaveActivity
        Intent i = new Intent(getApplicationContext(), SaveActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtras(bundle);
        startActivity(i);*/
    }

    fun timerPause() {
        timer!!.cancel()
    }

    fun getTotalMetres(): String {
        return roundUp(distance)
    }

    fun getTotalSteps(): String {
        System.out.println("steps count: " + stepCount.toString())
        return stepCount.toString()
    }

    //round up float number
    fun roundUp(num: Double): String {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(num.toDouble())
    }


    companion object {
        private const val CHANNEL_ID = "123"

        //this function is used for converting seconds to hh:mm:ss format
        fun secondFormatString(seconds: Long): String {
            val s = seconds % 60
            var h = seconds / 60
            val m = h % 60
            h = h / 60
            return String.format("%02d", h) + ":" + String.format(
                    "%02d",
                    m
            ) + ":" + String.format("%02d", s)
        }
    }


    fun sendEventActivity(userID: Int, steps: String, distance: String) {
        var result = ""
        try {

            val jsonBody = JSONObject()
            jsonBody.put("eventID", 1)
            jsonBody.put("userID", userID)
            jsonBody.put("stepsCount", steps.toDouble())
            jsonBody.put("distanceCovered", distance.toDouble())
            jsonBody.put("activityType", "walking")
            jsonBody.put("latitude", "1")
            jsonBody.put("longitude", "1")
            result = Networking.setParentJsonData(
                    "eventactivity",
                    jsonBody
            )

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        Networking
                .with(this)
                .getServices()
                .sendEventActivity(Networking.wrapParams(result))//wrapParams Wraps parameters in to Request body Json format
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : CallbackObserver<EventActivityModal>() {
                    override fun onSuccess(response: EventActivityModal) {
                        val data = response.data

                        if (data != null) {
                            if (response.status != 0L) {
                                Logger.d("data", response.message.toString())
                            }
                        }
                        Logger.d("data", response.message.toString())
                    }

                    override fun onFailed(code: Int, message: String) {
                        Logger.d("data", message.toString())
                    }

                })
    }


}


