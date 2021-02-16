package com.nezamipour.mehdi.admediator.mainclasses

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import com.chartboost.sdk.Chartboost
import com.nezamipour.mehdi.admediator.mainclasses.companies.ChartBoostManager
import com.nezamipour.mehdi.admediator.mainclasses.companies.CompanyManager
import com.nezamipour.mehdi.admediator.mainclasses.companies.TapSellManager
import com.nezamipour.mehdi.admediator.mainclasses.companies.UnityAdsManager
import com.nezamipour.mehdi.admediator.mainclasses.listeners.AdRequestCallback
import com.nezamipour.mehdi.admediator.mainclasses.listeners.AdShowCallback
import com.nezamipour.mehdi.admediator.model.AdConfig
import com.nezamipour.mehdi.admediator.model.AppConfig
import com.nezamipour.mehdi.admediator.network.AdMediatorService
import com.nezamipour.mehdi.admediator.utils.Constants
import com.unity3d.ads.UnityAds
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

class AdMediator {

    companion object {

        private val allCompanies: HashMap<String, CompanyManager> = hashMapOf()
        private val TAG = "AdMediator"

        private lateinit var appConfig: AppConfig
        private lateinit var adConfig: AdConfig
        internal lateinit var zoneType: String


        internal var adFound: Boolean = false
        internal var sAdId: String? = null


        fun initialize(application: Application, adMediatorAppId: String) {
            getAppConfig(adMediatorAppId)
            // initialization adNetworks base on appConfig
            if (appConfig.adNetworks.tapsell != null) {
                TapSellManager.initial(application, appConfig.adNetworks.tapsell!!)
                allCompanies["Tapsell"] = TapSellManager
            }
            if (appConfig.adNetworks.unityAds != null) {
                UnityAds.initialize(application, appConfig.adNetworks.unityAds)
                allCompanies["UnityAds"] = UnityAdsManager
            }
            if (appConfig.adNetworks.chartboost != null) {
                Chartboost.startWithAppId(application, appConfig.adNetworks.chartboost, null)
                allCompanies["Chartboost"] = ChartBoostManager
            }
        }

        private fun getAppConfig(appId: String) {
            try {
                CoroutineScope(IO).launch {
                    appConfig = AdMediatorService.getAppConfig(appId)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d(TAG, "getAppConfig: " + e.message)
            }

            Log.d(TAG, "getAppConfig : " + appConfig.adNetworks.tapsell)
            Log.d(TAG, "getAppConfig : " + adConfig.waterfall.toString())
        }

        private fun getAdConfigBaseOnZoneId(zoneId: String) {
            try {
                CoroutineScope(IO).launch {
                    adConfig = AdMediatorService.getAdConfig(zoneId)
                    zoneType = adConfig.zoneType
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d(TAG, "getAdConfigBaseOnZoneId : " + e.message)
            }
        }

        fun requestAd(
            context: Context,
            adMediatorZoneId: String,
            adRequestCallback: AdRequestCallback
        ) {
            // TODO : get ad config with new waterfall if not exist waterfall with this zoneId in catch
            getAdConfigBaseOnZoneId(zoneId = adMediatorZoneId)
            loadAd(context, adRequestCallback)

        }

        private fun loadAd(
            context: Context,
            adRequestCallback: AdRequestCallback
        ) {

            val parentJob = CoroutineScope(IO).launch {
                withTimeoutOrNull(adConfig.waterfall[0].timeout) {
                    if (!adFound)
                        try {
                            //delay(2100)
                            Log.d(TAG, "loadAd: ${adConfig.waterfall[0].zoneId}")
                            allCompanies[adConfig.waterfall[0].adNetwork]?.requestAd(
                                context,
                                adConfig.waterfall[0].zoneId,
                                adRequestCallback
                            )

                        } catch (e: Exception) {
                            Log.d(TAG, "loadAd: exception message =  ${e.message}")
                        }
                    else
                        cancel()
                }
                withTimeoutOrNull(adConfig.waterfall[1].timeout) {
                    if (!adFound)
                        try {
                            Log.d(TAG, "loadAd: ${adConfig.waterfall[1].zoneId}")
                            allCompanies[adConfig.waterfall[1].adNetwork]?.requestAd(
                                context,
                                adConfig.waterfall[1].zoneId,
                                adRequestCallback
                            )

                        } catch (e: Exception) {
                            Log.d(TAG, "loadAd: exception message = ${e.message}")
                        }
                    else
                        cancel()
                }
                withTimeoutOrNull(adConfig.waterfall[2].timeout) {
                    if (!adFound)
                        try {
                            Log.d(TAG, "loadAd: ${adConfig.waterfall[2].zoneId}")
                            allCompanies[adConfig.waterfall[2].adNetwork]?.requestAd(
                                context,
                                adConfig.waterfall[2].zoneId,
                                adRequestCallback
                            )

                        } catch (e: Exception) {
                            Log.d(TAG, "loadAd: exception message = ${e.message}")
                        }
                    else
                        cancel()
                }

            }
        }


        fun showAd(
            activity: Activity,
            zoneId: String,
            adId: String,
            adShowCallback: AdShowCallback
        ) {

            when (zoneId) {
                Constants.TAPSELL_INTERSTITIAL_BANNER, Constants.TAPSELL_INTERSTITIAL_VIDEO, Constants.TAPSELL_REWARDED_VIDEO ->
                    sAdId?.let {
                        allCompanies["Tapsell"]?.showAd(
                            activity,
                            zoneId,
                            it,
                            adShowCallback
                        )
                    }
                Constants.CHART_BOOST_INTERSTITIAL_BANNER, Constants.CHART_BOOST_INTERSTITIAL_VIDEO ->
                    sAdId?.let {
                        allCompanies["Chartboost"]?.showAd(
                            activity,
                            zoneId,
                            it,
                            adShowCallback
                        )
                    }
                Constants.CHART_BOOST_REWARDED_VIDEO -> sAdId?.let {
                    allCompanies["Chartboost"]?.showAd(
                        activity,
                        zoneId,
                        it,
                        adShowCallback
                    )
                }
                Constants.UNITY_AD_INTERSTITIAL_BANNER, Constants.UNITY_AD_INTERSTITIAL_VIDEO, Constants.UNITY_AD_REWARDED_VIDEO ->
                    sAdId?.let {
                        allCompanies["UnityAds"]?.showAd(
                            activity,
                            zoneId,
                            it,
                            adShowCallback
                        )
                    }
                else -> adShowCallback.onError("showAd : network not exist")
            }
        }


    }

}