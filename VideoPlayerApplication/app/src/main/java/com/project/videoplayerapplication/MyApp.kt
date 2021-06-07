package com.project.videoplayerapplication

import android.app.Application

class MyApp:Application() {
    override fun onCreate() {
        super.onCreate()
        NetworkLiveData.init(this)

    }
}