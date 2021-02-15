package com.nezamipour.mehdi.tapselltaskwithkotlin

import android.app.Application
import com.nezamipour.mehdi.admediator.mainclasses.AdMediator

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AdMediator.initialize(this, "AppId")

    }
}