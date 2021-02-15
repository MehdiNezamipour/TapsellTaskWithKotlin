package com.nezamipour.mehdi.admediator.mainclasses

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import com.chartboost.sdk.Chartboost
import com.google.gson.Gson
import com.nezamipour.mehdi.admediator.mainclasses.listeners.AdRequestCallback
import com.nezamipour.mehdi.admediator.mainclasses.listeners.AdShowCallback
import com.nezamipour.mehdi.admediator.mainclasses.listeners.UnityAdListeners
import com.nezamipour.mehdi.admediator.model.AdConfig
import com.nezamipour.mehdi.admediator.model.AppConfig
import com.nezamipour.mehdi.admediator.utils.Constants
import com.unity3d.ads.IUnityAdsLoadListener
import com.unity3d.ads.UnityAds
import com.unity3d.ads.UnityAds.UnityAdsError
import ir.tapsell.sdk.Tapsell
import ir.tapsell.sdk.TapsellAdRequestListener
import ir.tapsell.sdk.TapsellAdShowListener
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

class AdMediator {

    companion object {

        private val TAG = "AdMediator"
        private lateinit var appConfig: AppConfig
        private lateinit var adConfig: AdConfig
        private var adFound: Boolean = false


        private var sAdId: String? = null
        private var sZoneId: String? = null


        fun initialize(application: Application?, adMediatorAppId: String?) {
            getConfig()
            // initialization adNetworks base on appConfig
            Tapsell.initialize(application, appConfig.adNetworks.tapsell)
            Chartboost.startWithAppId(application, appConfig.adNetworks.chartboost, null)
            UnityAds.initialize(application, appConfig.adNetworks.unityAds)
        }

        private fun getConfig() {
            try {
                val gson = Gson()
                appConfig = gson.fromJson(Constants.APP_CONFIG, AppConfig::class.java)
                adConfig = gson.fromJson(Constants.AD_CONFIG, AdConfig::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d(TAG, "getConfig: " + e.message)
            }

            Log.d(TAG, "getConfig: appConfig " + appConfig.adNetworks.tapsell)
            Log.d(TAG, "getConfig: adConfig" + adConfig.waterfall.toString())
        }

        suspend fun requestAd(
            context: Context,
            adMediatorZoneId: String?,
            adRequestCallback: AdRequestCallback
        ) {
            when (adMediatorZoneId) {

                // request for get waterfall base on adMediatorZoneId
                /*Constants.AD_MEDIATOR_INTERSTITIAL_BANNER -> requestForZoneId
                Constants.AD_MEDIATOR_INTERSTITIAL_VIDEO -> requestForZoneId
                Constants.UNITY_AD_REWARDED_VIDEO -> requestForZoneId */

                Constants.AD_MEDIATOR_INTERSTITIAL_BANNER -> loadInterstitialBanner(
                    context,
                    adRequestCallback
                )
                Constants.AD_MEDIATOR_INTERSTITIAL_VIDEO -> loadInterstitialVideo(
                    context,
                    adRequestCallback
                )
                Constants.UNITY_AD_REWARDED_VIDEO -> loadRewardedVideo(context, adRequestCallback)
                else -> throw Exception("unKnow ZoneID")
            }
        }

        private suspend fun loadInterstitialBanner(
            context: Context,
            adRequestCallback: AdRequestCallback
        ) {

            val parentJob = CoroutineScope(IO).launch {
                val job1 = withTimeoutOrNull(adConfig.waterfall[0].timeout) {
                    if (!adFound)
                        try {
                            delay(2100)
                            Log.d(TAG, "loadAd: ${adConfig.waterfall[0].zoneId}")
                            requestInterstitialBannerBaseOnNetworkName(
                                context,
                                adConfig.waterfall[0].adNetwork,
                                adRequestCallback
                            )


                        } catch (e: Exception) {
                            Log.d(TAG, "loadAd: exception message =  ${e.message}")
                        }
                    else
                        cancel()
                }
                val job2 = withTimeoutOrNull(adConfig.waterfall[1].timeout) {
                    if (!adFound)
                        try {
                            Log.d(TAG, "loadAd: ${adConfig.waterfall[1].zoneId}")
                            requestInterstitialBannerBaseOnNetworkName(
                                context,
                                adConfig.waterfall[1].adNetwork,
                                adRequestCallback
                            )

                        } catch (e: Exception) {
                            Log.d(TAG, "loadAd: exception message = ${e.message}")
                        }
                    else
                        cancel()
                }
                val job3 = withTimeoutOrNull(adConfig.waterfall[2].timeout) {
                    if (!adFound)
                        try {
                            Log.d(TAG, "loadAd: ${adConfig.waterfall[2].zoneId}")
                            requestInterstitialBannerBaseOnNetworkName(
                                context,
                                adConfig.waterfall[2].adNetwork,
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

        private suspend fun loadInterstitialVideo(
            context: Context,
            adRequestCallback: AdRequestCallback
        ) {

            val parentJob = CoroutineScope(IO).launch {
                val job1 = withTimeoutOrNull(adConfig.waterfall[0].timeout) {
                    if (!adFound)
                        try {
                            //delay(2100) mimic of request
                            Log.d(TAG, "loadAd: ${adConfig.waterfall[0].zoneId}")
                            requestInterstitialVideoBaseOnNetworkName(
                                context,
                                adConfig.waterfall[0].adNetwork,
                                adRequestCallback
                            )


                        } catch (e: Exception) {
                            Log.d(TAG, "loadAd: exception message =  ${e.message}")
                        }
                    if (adFound)
                        cancel()
                }
                val job2 = withTimeoutOrNull(adConfig.waterfall[1].timeout) {
                    try {
                        Log.d(TAG, "loadAd: ${adConfig.waterfall[1].zoneId}")
                        requestInterstitialVideoBaseOnNetworkName(
                            context,
                            adConfig.waterfall[1].adNetwork,
                            adRequestCallback
                        )

                    } catch (e: Exception) {
                        Log.d(TAG, "loadAd: exception message = ${e.message}")
                    }
                    if (adFound)
                        cancel()
                }
                val job3 = withTimeoutOrNull(adConfig.waterfall[2].timeout) {
                    try {
                        Log.d(TAG, "loadAd: ${adConfig.waterfall[2].zoneId}")
                        requestInterstitialVideoBaseOnNetworkName(
                            context,
                            adConfig.waterfall[2].adNetwork,
                            adRequestCallback
                        )

                    } catch (e: Exception) {
                        Log.d(TAG, "loadAd: exception message = ${e.message}")
                    }
                    if (adFound)
                        cancel()
                }

            }
        }

        private suspend fun loadRewardedVideo(
            context: Context,
            adRequestCallback: AdRequestCallback
        ) {

            val parentJob = CoroutineScope(IO).launch {
                val job1 = withTimeoutOrNull(adConfig.waterfall[0].timeout) {
                    if (!adFound)
                        try {
                            //delay(2100) mimic of request
                            Log.d(TAG, "loadAd: ${adConfig.waterfall[0].zoneId}")
                            requestRewardedVideoBaseOnNetworkName(
                                context,
                                adConfig.waterfall[0].adNetwork,
                                adRequestCallback
                            )


                        } catch (e: Exception) {
                            Log.d(TAG, "loadAd: exception message =  ${e.message}")
                        }
                    if (adFound)
                        cancel()
                }
                val job2 = withTimeoutOrNull(adConfig.waterfall[1].timeout) {
                    try {
                        Log.d(TAG, "loadAd: ${adConfig.waterfall[1].zoneId}")
                        requestRewardedVideoBaseOnNetworkName(
                            context,
                            adConfig.waterfall[1].adNetwork,
                            adRequestCallback
                        )

                    } catch (e: Exception) {
                        Log.d(TAG, "loadAd: exception message = ${e.message}")
                    }
                    if (adFound)
                        cancel()
                }
                val job3 = withTimeoutOrNull(adConfig.waterfall[2].timeout) {
                    try {
                        Log.d(TAG, "loadAd: ${adConfig.waterfall[2].zoneId}")
                        requestRewardedVideoBaseOnNetworkName(
                            context,
                            adConfig.waterfall[2].adNetwork,
                            adRequestCallback
                        )

                    } catch (e: Exception) {
                        Log.d(TAG, "loadAd: exception message = ${e.message}")
                    }
                    if (adFound)
                        cancel()
                }

            }
        }

        private fun requestInterstitialBannerBaseOnNetworkName(
            context: Context,
            network: String,
            adRequestCallback: AdRequestCallback
        ) {
            when (network) {
                "Tapsell" -> requestFromTapSell(
                    context,
                    Constants.TAPSELL_INTERSTITIAL_BANNER,
                    adRequestCallback
                )
                "UnityAds" -> requestFromUnityAd(
                    context,
                    Constants.UNITY_AD_INTERSTITIAL_BANNER,
                    adRequestCallback
                )
                "Chartboost" -> requestInterstitialFromChartBoost(Constants.CHART_BOOST_INTERSTITIAL_BANNER)
                else -> throw Exception("Unknown network or ZoneID")
            }
        }

        private fun requestInterstitialVideoBaseOnNetworkName(
            context: Context,
            network: String,
            adRequestCallback: AdRequestCallback
        ) {
            when (network) {
                "Tapsell" -> requestFromTapSell(
                    context,
                    Constants.TAPSELL_INTERSTITIAL_VIDEO,
                    adRequestCallback
                )
                "UnityAds" -> requestFromUnityAd(
                    context,
                    Constants.UNITY_AD_INTERSTITIAL_VIDEO,
                    adRequestCallback
                )
                "Chartboost" -> requestInterstitialFromChartBoost(Constants.CHART_BOOST_INTERSTITIAL_VIDEO)
                else -> throw Exception("Unknown network or ZoneID")
            }
        }

        private fun requestRewardedVideoBaseOnNetworkName(
            context: Context,
            network: String,
            adRequestCallback: AdRequestCallback
        ) {
            when (network) {
                "Tapsell" -> requestFromTapSell(
                    context,
                    Constants.TAPSELL_REWARDED_VIDEO,
                    adRequestCallback
                )
                "UnityAds" -> requestFromUnityAd(
                    context,
                    Constants.UNITY_AD_REWARDED_VIDEO,
                    adRequestCallback
                )
                "Chartboost" -> requestRewardedFromChartBoost(Constants.CHART_BOOST_REWARDED_VIDEO)
                else -> throw Exception("Unknown network or ZoneID")
            }
        }


        private fun requestFromTapSell(
            context: Context,
            tapSellZoneId: String,
            adRequestCallback: AdRequestCallback
        ) {
            Tapsell.requestAd(context, tapSellZoneId, null, object : TapsellAdRequestListener() {
                override fun onAdAvailable(adId: String) {
                    super.onAdAvailable(adId)
                    adRequestCallback.onSuccess(adId)
                    sAdId = adId
                    adFound = true
                }

                override fun onError(adId: String) {
                    super.onError(adId)
                    adFound = false
                }
            })
        }


        private fun requestFromUnityAd(
            context: Context,
            unityAdZoneId: String,
            adRequestCallback: AdRequestCallback
        ) {

            UnityAds.load(unityAdZoneId, object : IUnityAdsLoadListener {
                override fun onUnityAdsAdLoaded(adId: String) {
                    adRequestCallback.onSuccess(adId)
                    adFound = true
                }

                override fun onUnityAdsFailedToLoad(adId: String) {
                    adFound = false
                }
            })
        }

        private fun requestInterstitialFromChartBoost(chartBoostZoneId: String) {
            Chartboost.cacheInterstitial(chartBoostZoneId)
        }

        private fun requestRewardedFromChartBoost(chartBoostZoneId: String) {
            Chartboost.cacheRewardedVideo(chartBoostZoneId)
        }


        fun showAd(
            context: Context,
            activity: Activity,
            adId: String?,
            adShowCallback: AdShowCallback
        ) {
            when (sZoneId) {
                Constants.TAPSELL_INTERSTITIAL_BANNER, Constants.TAPSELL_INTERSTITIAL_VIDEO, Constants.TAPSELL_REWARDED_VIDEO -> showFromTapSell(
                    context,
                    sZoneId,
                    sAdId,
                    adShowCallback
                )
                Constants.CHART_BOOST_INTERSTITIAL_BANNER, Constants.CHART_BOOST_INTERSTITIAL_VIDEO -> showInterstitialFromChartBoost(
                    sZoneId
                )
                Constants.CHART_BOOST_REWARDED_VIDEO -> showRewardedFromChartBoost(sZoneId)
                Constants.UNITY_AD_INTERSTITIAL_BANNER, Constants.UNITY_AD_INTERSTITIAL_VIDEO, Constants.UNITY_AD_REWARDED_VIDEO -> showFromUnityAd(
                    activity,
                    sZoneId,
                    adShowCallback
                )
                else -> adShowCallback.onError("showAd : network not exist")
            }
        }

        private fun showFromTapSell(
            context: Context,
            zoneId: String?,
            adId: String?,
            adShowCallback: AdShowCallback
        ) {
            Tapsell.showAd(context, zoneId, adId, null, object : TapsellAdShowListener() {
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

        private fun showFromUnityAd(
            activity: Activity,
            zoneId: String?,
            adShowCallback: AdShowCallback
        ) {
            UnityAds.addListener(object : UnityAdListeners() {
                override fun onUnityAdsStart(placementId: String?) {
                    super.onUnityAdsStart(placementId)
                    adShowCallback.onOpened()
                }

                override fun onUnityAdsError(error: UnityAdsError?, message: String?) {
                    super.onUnityAdsError(error, message)
                    adShowCallback.onError(message)
                }
            })
            if (UnityAds.isReady(zoneId)) UnityAds.show(activity, zoneId)
        }

        private fun showInterstitialFromChartBoost(zoneId: String?) {
            Chartboost.showInterstitial(zoneId)
        }

        private fun showRewardedFromChartBoost(zoneId: String?) {
            Chartboost.showRewardedVideo(zoneId)
        }
    }

}