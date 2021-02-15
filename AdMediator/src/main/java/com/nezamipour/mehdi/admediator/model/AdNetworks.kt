package com.nezamipour.mehdi.admediator.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AdNetworks(
    @SerializedName("Tapsell")
    @Expose
    var tapsell: String,

    @SerializedName("UnityAds")
    @Expose
    var unityAds: String,

    @SerializedName("Chartboost")
    @Expose
    var chartboost: String
)