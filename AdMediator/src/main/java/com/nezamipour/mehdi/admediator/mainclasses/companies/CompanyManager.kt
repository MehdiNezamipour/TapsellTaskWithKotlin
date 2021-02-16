package com.nezamipour.mehdi.admediator.mainclasses.companies

import android.app.Activity
import android.app.Application
import android.content.Context
import com.nezamipour.mehdi.admediator.mainclasses.listeners.AdRequestCallback
import com.nezamipour.mehdi.admediator.mainclasses.listeners.AdShowCallback

interface CompanyManager {


    fun initial(application: Application, appId: String)


    fun requestAd(context: Context, zoneId: String, adRequestCallback: AdRequestCallback)

    fun showAd(activity: Activity,
               zoneId: String?,
               adId: String,
               adShowCallback: AdShowCallback
    )


}