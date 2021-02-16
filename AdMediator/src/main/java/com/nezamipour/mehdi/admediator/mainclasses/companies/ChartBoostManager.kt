package com.nezamipour.mehdi.admediator.mainclasses.companies

import android.app.Activity
import android.app.Application
import android.content.Context
import com.chartboost.sdk.Chartboost
import com.chartboost.sdk.ChartboostDelegate
import com.chartboost.sdk.Model.CBError
import com.nezamipour.mehdi.admediator.mainclasses.AdMediator
import com.nezamipour.mehdi.admediator.mainclasses.listeners.AdRequestCallback
import com.nezamipour.mehdi.admediator.mainclasses.listeners.AdShowCallback
import com.nezamipour.mehdi.admediator.utils.Constants

object ChartBoostManager : CompanyManager {


    override fun initial(application: Application, appId: String) {
        Chartboost.startWithAppId(application, appId, null)
    }


    override fun requestAd(context: Context, zoneId: String, adRequestCallback: AdRequestCallback) {
        when (AdMediator.zoneType) {
            Constants.AD_MEDIATOR_INTERSTITIAL_BANNER, Constants.AD_MEDIATOR_INTERSTITIAL_VIDEO -> requestInterstitialFromChartBoost(
                zoneId, adRequestCallback
            )
            Constants.AD_MEDIATOR_REWARDED_VIDEO -> requestRewardedFromChartBoost(
                zoneId,
                adRequestCallback
            )
        }
    }

    private fun requestInterstitialFromChartBoost(
        chartBoostZoneId: String,
        adRequestCallback: AdRequestCallback
    ) {
        Chartboost.setDelegate(object : ChartboostDelegate() {
            override fun didCacheInterstitial(location: String?) {
                super.didCacheInterstitial(location)
                adRequestCallback.onSuccess(location)

            }

            override fun didFailToLoadInterstitial(
                location: String?,
                error: CBError.CBImpressionError?
            ) {
                super.didFailToLoadInterstitial(location, error)
                adRequestCallback.error(error.toString())
            }
        })
        Chartboost.cacheInterstitial(chartBoostZoneId)
    }

    private fun requestRewardedFromChartBoost(
        chartBoostZoneId: String,
        adRequestCallback: AdRequestCallback
    ) {
        Chartboost.setDelegate(object : ChartboostDelegate() {
            override fun didCacheRewardedVideo(location: String?) {
                super.didCacheRewardedVideo(location)
                adRequestCallback.onSuccess(location)
            }

            override fun didFailToLoadRewardedVideo(
                location: String?,
                error: CBError.CBImpressionError?
            ) {
                super.didFailToLoadRewardedVideo(location, error)
                adRequestCallback.error(error.toString())
            }
        })
        Chartboost.cacheRewardedVideo(chartBoostZoneId)
    }

    override fun showAd(
        activity: Activity,
        zoneId: String?,
        adId: String,
        adShowCallback: AdShowCallback
    ) {
        when (AdMediator.zoneType) {
            Constants.AD_MEDIATOR_INTERSTITIAL_BANNER, Constants.AD_MEDIATOR_INTERSTITIAL_VIDEO -> showInterstitialFromChartBoost(
                zoneId, adShowCallback
            )
            Constants.AD_MEDIATOR_REWARDED_VIDEO -> showRewardedFromChartBoost(
                zoneId,
                adShowCallback
            )
        }
    }


    private fun showInterstitialFromChartBoost(
        zoneId: String?, adShowCallback: AdShowCallback
    ) {
        Chartboost.setDelegate(object : ChartboostDelegate() {
            override fun didCloseInterstitial(location: String?) {
                super.didCloseInterstitial(location)
                adShowCallback.onClosed()
            }

            override fun didDisplayInterstitial(location: String?) {
                super.didDisplayInterstitial(location)
                adShowCallback.onOpened()
            }

        })
        Chartboost.showInterstitial(zoneId)
    }

    private fun showRewardedFromChartBoost(
        zoneId: String?, adShowCallback: AdShowCallback
    ) {
        Chartboost.setDelegate(object : ChartboostDelegate() {
            override fun didCloseRewardedVideo(location: String?) {
                super.didCloseRewardedVideo(location)
                adShowCallback.onClosed()
            }

            override fun didClickRewardedVideo(location: String?) {
                super.didClickRewardedVideo(location)

            }

            override fun didCompleteRewardedVideo(location: String?, reward: Int) {
                super.didCompleteRewardedVideo(location, reward)
                adShowCallback.onRewarded()
            }

            override fun didDisplayRewardedVideo(location: String?) {
                super.didDisplayRewardedVideo(location)
                adShowCallback.onOpened()

            }
        })
        Chartboost.showRewardedVideo(zoneId)
    }
}