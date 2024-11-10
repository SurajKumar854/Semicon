package com.suraj854.myapplication.core

import android.app.Application
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MissionSemiConductor : Application(){
    override fun onCreate() {
        super.onCreate()

    }
}