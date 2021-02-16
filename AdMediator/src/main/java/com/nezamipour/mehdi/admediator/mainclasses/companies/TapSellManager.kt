package com.nezamipour.mehdi.admediator.mainclasses.companies

import android.app.Activity
import android.app.Application
import android.content.Context
import com.nezamipour.mehdi.admediator.mainclasses.AdMediator
import com.nezamipour.mehdi.admediator.mainclasses.listeners.AdRequestCallback
import com.nezamipour.mehdi.admediator.mainclasses.listeners.AdShowCallback
import ir.tapsell.sdk.Tapsell
import ir.tapsell.sdk.TapsellAdRequestListener
import ir.tapsell.sdk.TapsellAdShowListener

object TapSellManager : CompanyManager {


    override fun initial(application: Application, appId: String) {
        Tapsell.initialize(application, appId)
    }


    override fun requestAd(context: Context, zoneId: String, adRequestCallback: AdRequestCallback) {
        Tapsell.requestAd(context, zoneId, null, object : TapsellAdRequestListener() {
            override fun onAdAvailable(adId: String) {
                super.onAdAvailable(adId)
                adRequestCallback.onSuccess(adId)
                AdMediator.sAdId = adId
                AdMediator.adFound = true
            }

            override fun onError(message: String) {
                super.onError(message)
                AdMediator.adFound = false
            }
        })
    }

    override fun showAd(
        activity: Activity,
        zoneId: String?,
        adId: String,
        adShowCallback: AdShowCallback
    ) {

        Tapsell.showAd(activity, zoneId, adId, null, object : TapsellAdShowListener() {
            override fun onOpened() {
                super.onOpened()
                adShowCallback.onOpened()
            }

            override fun onError(s: String) {
                super.onError(s)
                adShowCallback.onError(s)
            }
        })
    }
}