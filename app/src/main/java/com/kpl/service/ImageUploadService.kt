package com.kpl.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Binder
import android.os.Environment
import android.os.IBinder
import com.kpl.database.AppDatabase
import com.kpl.database.SurveyAnswer
import com.kpl.model.ImageUploadModel
import com.kpl.network.CallbackObserver
import com.kpl.network.Networking
import com.kpl.utils.Logger
import com.kpl.utils.SessionManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.Companion.createFormData
import okhttp3.RequestBody
import java.io.File


class ImageUploadService : Service() {

    var sessionManager: SessionManager? = null
    var appDatabase: AppDatabase? = null
    var ansArray: ArrayList<SurveyAnswer>? = null

    var disposable: Disposable? = null


    // Binder interface given to clients
    private val mBinder: IBinder = MyBinder()

    inner class MyBinder : Binder() {
        val service: ImageUploadService
            get() = this@ImageUploadService
    }

    override fun onBind(intent: Intent): IBinder? {
        return mBinder
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
        appDatabase = AppDatabase.getDatabase(this)!!
        ansArray = ArrayList()


        //this 'if command' executes when users tap on 'Stop Tracking' button on notification bar
        val isStopTracking = intent.getBooleanExtra("isTracking", true)


        // startForeground(NOTIFICATION_ID, ""+updatedNotification(distance))


        getImageListFromDB(this).execute()

        return START_STICKY
    }

    inner class getImageListFromDB(var context: Context) :
        AsyncTask<Void, Void, ArrayList<SurveyAnswer>>() {
        override fun doInBackground(vararg params: Void?): ArrayList<SurveyAnswer>? {
            ansArray?.addAll(appDatabase!!.surveyAnswerDao().getAllImages(""))
            appDatabase!!.surveyDao().uploadDataDone()


            return ansArray
        }

        override fun onPostExecute(result: ArrayList<SurveyAnswer>?) {

            for (item in ansArray?.indices!!)

                sendImageList(ansArray!!.get(item).Image.toString(),ansArray!!.get(item).SurveyAnswerID)
        }
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


    fun sendImageList(imagepath: String, AnsId: Long?) {

        var selectedimageName = imagepath.replace("file:///storage/emulated/0/.kpl/", "")

        val file = File(Environment.getExternalStorageDirectory().toString() + "/.kpl/", selectedimageName)

        val requestFile: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        val body: MultipartBody.Part = createFormData("ImageData", file.name, requestFile)

        val imageName: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), file.name)
        val methodName: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), "addSurveyImage")



        Networking
            .with(this)
            .getServices()
            .ImageUploadApi(
                body,
                methodName,
                imageName
            )//wrapParams Wraps parameters in to Request body Json format
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CallbackObserver<ImageUploadModel>() {
                override fun onSuccess(response: ImageUploadModel) {
                    if (response.error == 200) {
                        getUpdateUploadImageStatus(this@ImageUploadService,AnsId).execute()
                    }
                }

                override fun onFailed(code: Int, message: String) {
                    Logger.d("data", message.toString())
                }

            })
    }

    inner class getUpdateUploadImageStatus(var context: Context, var AndId: Long?) :
        AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean? {
            appDatabase!!.surveyAnswerDao().updateUploadedStatus(AndId)

            return true
        }

        override fun onPostExecute(result: Boolean?) {

        }
    }


}


