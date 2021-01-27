package com.kpl

import android.app.Application
import com.facebook.stetho.Stetho

class AppClass : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this);
    }

}