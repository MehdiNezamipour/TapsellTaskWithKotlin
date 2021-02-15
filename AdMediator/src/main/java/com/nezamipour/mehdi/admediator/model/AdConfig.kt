package com.nezamipour.mehdi.admediator.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AdConfig(

    @SerializedName("zoneType")
    @Expose
    var zoneType: String,

    @SerializedName("waterfall")
    @Expose
    var waterfall: List<Waterfall>,

    @SerializedName("ttl")
    @Expose
    var ttl: Long

)