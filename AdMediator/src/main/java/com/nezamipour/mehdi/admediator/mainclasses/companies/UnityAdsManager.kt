package com.nezamipour.mehdi.admediator.mainclasses.companies

import android.app.Activity
import android.app.Application
import android.content.Context
import com.nezamipour.mehdi.admediator.mainclasses.AdMediator
import com.nezamipour.mehdi.admediator.mainclasses.listeners.AdRequestCallback
import com.nezamipour.mehdi.admediator.mainclasses.listeners.AdShowCallback
import com.nezamipour.mehdi.admediator.mainclasses.listeners.UnityAdListeners
import com.unity3d.ads.IUnityAdsLoadListener
import com.unity3d.ads.UnityAds

object UnityAdsManager : CompanyManager {


    override fun initial(application: Application, appId: String) {
        UnityAds.initialize(application, appId)
    }

    override fun requestAd(context: Context, zoneId: String, adRequestCallback: AdRequestCallback) {

        UnityAds.load(zoneId, object : IUnityAdsLoadListener {
            override fun onUnityAdsAdLoaded(adId: String) {
                adRequestCallback.onSuccess(adId)
                AdMediator.adFound = true
                AdMediator.sAdId = adId
            }

            override fun onUnityAdsFailedToLoad(placementId: String) {
                adRequestCallback.error("placementId :  $placementId")
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
        UnityAds.addListener(object : UnityAdListeners() {
            override fun onUnityAdsStart(placementId: String?) {
                super.onUnityAdsStart(placementId)
                adShowCallback.onOpened()
            }

            override fun onUnityAdsError(error: UnityAds.UnityAdsError?, message: String?) {
                super.onUnityAdsError(error, message)
                adShowCallback.onError(message)
            }
        })

        if (UnityAds.isReady(zoneId))
            UnityAds.show(activity, zoneId)
    }
}