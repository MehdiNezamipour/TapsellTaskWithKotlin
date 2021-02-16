package com.nezamipour.mehdi.admediator.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AppConfig(

    @SerializedName("adNetworks")
    @Expose
    var adNetworks: AdNetworks
): Parcelable