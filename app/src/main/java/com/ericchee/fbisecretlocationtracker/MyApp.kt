package com.ericchee.fbisecretlocationtracker

import android.app.Application

class MyApp: Application() {

    lateinit var fbiLocationManager: MyLocationManager
        private set

    override fun onCreate() {
        super.onCreate()

        fbiLocationManager = MyLocationManager(this)
    }




}