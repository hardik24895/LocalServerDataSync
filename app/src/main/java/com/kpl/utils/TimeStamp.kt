package com.kpl.utils

import android.text.TextUtils

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


object TimeStamp {
    private val SECOND_MILLIS = 1000
    private val MINUTE_MILLIS = 60 * SECOND_MILLIS
    private val HOUR_MILLIS = 60 * MINUTE_MILLIS
    private val DAY_MILLIS = 24 * HOUR_MILLIS

    val FULL_DATE_FORMAT = "dd MMM yyyy, hh:mm:ss a"
    val DATE_FORMAT_MMM_YYYY = "MMM yyyy"
    val TIME_FORMAT_SHORT_24 = "HH:mm"
    val TIME_FORMAT_SHORT_12 = "hh:mm a"
    val TIME_FORMAT_FULL = "hh:mm:ss a"

    fun formatToSeconds(value: String, format: String): Long {
        val timeZone = TimeZone.getDefault()
        return formatToSeconds(value, format, timeZone)
    }

    fun formatToSeconds(value: String, format: String, timeZone: TimeZone): Long {
        try {
            val sdf = SimpleDateFormat(format, Locale.ENGLISH)
            sdf.timeZone = timeZone
            val mDate = sdf.parse(value)
            return TimeUnit.MILLISECONDS.toSeconds(mDate.time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return 0
    }

    fun formatToMillis(value: String, format: String): Long {
        val timeZone = TimeZone.getDefault()
        return formatToMillis(value, format, timeZone)
    }

    fun formatToMillis(value: String, format: String, timeZone: TimeZone): Long {
        try {
            val sdf = SimpleDateFormat(format, Locale.ENGLISH)
            sdf.timeZone = timeZone
            val mDate = sdf.parse(value)
            return TimeUnit.MILLISECONDS.toMillis(mDate.time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return 0
    }

    fun millisToFormat(millis: Long?): String {
        val timeZone = TimeZone.getDefault()
        return millisToFormat(millis!!, FULL_DATE_FORMAT, timeZone)
    }

    fun millisToFormat(millis: String): String {
        val timeZone = TimeZone.getDefault()
        return millisToFormat(java.lang.Long.parseLong(millis), FULL_DATE_FORMAT, timeZone)
    }

    fun millisToFormat(millis: Long, format: String): String {
        val timeZone = TimeZone.getDefault()
        return millisToFormat(millis, format, timeZone)
    }

    fun millisToFormat(millis: String, format: String): String {
        val timeZone = TimeZone.getDefault()
        return millisToFormat(java.lang.Long.parseLong(millis), format, timeZone)
    }

    fun millisToFormat(milis: Long, format: String, tz: TimeZone): String {
        var millis = milis
        if (millis < 1000000000000L) {
            millis *= 1000
        }
        val cal = Calendar.getInstance(tz)
        cal.timeInMillis = millis
        val sdf = SimpleDateFormat(format, Locale.ENGLISH)
        sdf.timeZone = tz
        return sdf.format(cal.time)
    }

    fun formatDateFromString(inputFormat: String, outputFormat: String, inputDate: String): String {
        var parsed: Date? = null
        var outputDate = ""

        val dfInput = SimpleDateFormat(inputFormat, Locale.getDefault())
        val dfOutput = SimpleDateFormat(outputFormat, Locale.getDefault())

        try {
            parsed = dfInput.parse(inputDate)
            outputDate = dfOutput.format(parsed)

        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return outputDate
    }

    fun getLocalfromUtc(utc_data: String, inputPattern: String, outputPattern: String): String {
        var formattedDate = ""
        if (!TextUtils.isEmpty(utc_data)) {
            try {
                val df = SimpleDateFormat(inputPattern, Locale.getDefault())
                df.timeZone = TimeZone.getTimeZone("UTC")
                var date: Date? = null
                try {
                    date = df.parse(utc_data)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }

                //System.out.println(utc_data);
                df.timeZone = TimeZone.getDefault()
                val df1 = SimpleDateFormat(outputPattern, Locale.getDefault())
                formattedDate = df1.format(date)
                //System.out.println("finalLocalTimeDate -> "+formattedDate);
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return formattedDate
    }

    fun getServerDate(date: String): String {
        var convertDate = ""
        try {
            val f: DateFormat = SimpleDateFormat(Constant.DATE_FORMAT)
            val d: Date = f.parse(date)
            val date: DateFormat = SimpleDateFormat("dd-MM-yyyy")
            val time: DateFormat = SimpleDateFormat("HH:mm")
            System.out.println("Date: " + date.format(d))
            System.out.println("Time: " + time.format(d))
            convertDate = date.format(d).toString()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return convertDate

    }

    fun getServerTime(date: String): String {
        var convertTime = ""
        try {
            val f: DateFormat = SimpleDateFormat(Constant.DATE_FORMAT)
            val d: Date = f.parse(date)
            val date: DateFormat = SimpleDateFormat("dd-MM-yyyy")
            val time: DateFormat = SimpleDateFormat("hh:mm a")
            convertTime = time.format(d).toString()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return convertTime

    }

    fun getServerTimeMinSec(date: String): String {
        var convertTime = ""
        try {
            val f: DateFormat = SimpleDateFormat(Constant.DATE_FORMAT)
            val d: Date = f.parse(date)
            val date: DateFormat = SimpleDateFormat("dd-MM-yyyy")
            val time: DateFormat = SimpleDateFormat("hh:mm:ss")
            convertTime = time.format(d).toString()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return convertTime

    }

    fun getAge(dobString: String): Int {
        val f: DateFormat = SimpleDateFormat(Constant.DATE_FORMAT)
        val d: Date = f.parse(dobString)
        val date: DateFormat = SimpleDateFormat("dd")
        val moth: DateFormat = SimpleDateFormat("MM")
        val year: DateFormat = SimpleDateFormat("yyyy")
        val dobDate = date.format(d).toInt()
        val dobMonth = moth.format(d).toInt()
        val dobYear = year.format(d).toInt()

        val dob = Calendar.getInstance()
        val today = Calendar.getInstance()

        dob.set(dobYear, dobMonth, dobDate)

        var age = today[Calendar.YEAR] - dob[Calendar.YEAR]

        if (today[Calendar.DAY_OF_YEAR] < dob[Calendar.DAY_OF_YEAR]) {
            age--
        }

        val ageInt = age


        return ageInt+1
    }
}
