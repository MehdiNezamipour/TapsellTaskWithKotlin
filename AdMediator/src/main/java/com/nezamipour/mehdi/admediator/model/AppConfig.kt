package com.nezamipour.mehdi.admediator.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AppConfig(

    @SerializedName("adNetworks")
    @Expose
    var adNetworks: AdNetworks
)