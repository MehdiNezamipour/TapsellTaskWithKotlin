package com.nezamipour.mehdi.admediator.mainclasses.listeners

import com.unity3d.ads.IUnityAdsListener
import com.unity3d.ads.UnityAds.FinishState
import com.unity3d.ads.UnityAds.UnityAdsError

open class UnityAdListeners : IUnityAdsListener {

    override fun onUnityAdsReady(placementId: String?) {}

    override fun onUnityAdsStart(placementId: String?) {}

    override fun onUnityAdsFinish(placementId: String?, result: FinishState?) {}

    override fun onUnityAdsError(error: UnityAdsError?, message: String?) {}
}