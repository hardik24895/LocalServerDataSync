package com.kpl.utils

import android.app.NotificationManager
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kpl.R


import java.lang.reflect.Type


class SessionManager(val context: Context) {
    val pref = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)


    var isLoggedIn: Boolean
        get() = pref.contains(KEY_IS_LOGIN) && pref.getBoolean(KEY_IS_LOGIN, false)
        set(isLoggedIn) = storeDataByKey(KEY_IS_LOGIN, isLoggedIn)

//    var isRegester: Boolean
//        get() = pref.contains(KEY_IS_REGISTER) && pref.getBoolean(KEY_IS_REGISTER, false)
//        set(isRegester) = storeDataByKey(KEY_IS_REGISTER, isRegester)

   /* var user: UserModal
        get() {
            val gson = Gson()
            val json = getDataByKey(KEY_USER_INFO, "")
            return gson.fromJson(json, UserModal::class.java)
        }
        set(user) {
            val gson = Gson()
            val json = gson.toJson(user)
            pref.edit().putString(KEY_USER_INFO, json).apply()
            isLoggedIn = true
        }*/



    @JvmOverloads
    fun getDataByKey(Key: String, DefaultValue: String = ""): String {
        return if (pref.contains(Key)) {
            pref.getString(Key, DefaultValue)!!
        } else {
            DefaultValue
        }
    }

    @JvmOverloads
    fun getDataByKeyBoolean(Key: String, DefaultValue: Boolean): Boolean {
        return if (pref.contains(Key)) {
            pref.getBoolean(Key, DefaultValue)!!
        } else {
            DefaultValue
        }
    }

    @JvmOverloads
    fun getDataByKeyInt(Key: String, DefaultValue: Int = 0): Int {
        return if (pref.contains(Key)) {
            pref.getInt(Key, DefaultValue)!!
        } else {
            DefaultValue
        }
    }

    fun storeDataByKey(key: String, Value: String) {
        pref.edit().putString(key, Value).apply()
    }

    fun storeDataByKey(key: String, Value: Int) {
        pref.edit().putInt(key, Value).apply()
    }

    fun storeDataByKey(key: String, Value: Boolean) {
        pref.edit().putBoolean(key, Value).apply()
    }

    operator fun contains(key: String): Boolean {
        return pref.contains(key)
    }

    fun remove(key: String) {
        pref.edit().remove(key).apply()
    }

    fun clearSession() {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancelAll()

        val editor = context.getSharedPreferences("Session", Context.MODE_PRIVATE).edit()
        editor.clear().apply()

        pref.edit().clear().apply()
    }



    companion object {
        const val KEY_IS_LOGIN = "isLogin"
      //  const val KEY_IS_REGISTER = "isRegister"
        const val SPUserID = "SPUserID"
        const val SPRoleID = "SPRoleID"
        const val SPEmailID = "SPEmailID"
        const val SPPassword = "SPPassword"
        const val SPFirstName = "SPFirstName"
        const val SPLastName = "SPLastName"
        const val SPMobileNo = "SPMobileNo"
        const val SPAddress = "SPAddress"
        const val SPUserType = "SPUserType"
        const val SPSyncData = "SPSyncData"
        const val SPToken = "SPToken"
    }
}