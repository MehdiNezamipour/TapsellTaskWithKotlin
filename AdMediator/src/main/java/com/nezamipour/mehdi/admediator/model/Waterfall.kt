package com.nezamipour.mehdi.admediator.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Waterfall(
    @SerializedName("adNetwork")
    @Expose
    var adNetwork: String,

    @SerializedName("zoneId")
    @Expose
    var zoneId: String,

    @SerializedName("timeout")
    @Expose
    var timeout: Long
)