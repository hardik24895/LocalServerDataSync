package com.kpl

import android.app.Application

class AppClass : Application() {

    override fun onCreate() {
        super.onCreate()
    //    Stetho.initializeWithDefaults(this);
    }

}