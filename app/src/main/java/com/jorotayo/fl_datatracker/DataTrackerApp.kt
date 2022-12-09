package com.jorotayo.fl_datatracker

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DataTrackerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)
    }

}