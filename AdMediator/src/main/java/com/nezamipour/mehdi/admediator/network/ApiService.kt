package com.nezamipour.mehdi.admediator.network

import com.nezamipour.mehdi.admediator.model.AdConfig
import com.nezamipour.mehdi.admediator.model.AppConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    // fake api
    // base url : https://api.admediator.com/
    // path : mobile_ad


    @GET(NetworkConstants.MOBILE_AD)
    suspend fun getAppConfig(@Query("appId") appId: String): Response<AppConfig>


    @GET(NetworkConstants.MOBILE_AD)
    suspend fun getAdConfig(@Query("zoneId") zoneId: String): Response<AdConfig>

}