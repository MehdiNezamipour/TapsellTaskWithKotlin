package com.nezamipour.mehdi.admediator.network

import com.nezamipour.mehdi.admediator.model.AdConfig
import com.nezamipour.mehdi.admediator.model.AppConfig

object AdMediatorService : BaseService() {

    private val apiService = RetrofitInstance.retrofitInstance.create(ApiService::class.java)

    suspend fun getAppConfig(appId: String): AppConfig {
        when (val result = createCall { apiService.getAppConfig(appId) }) {
            is Result.Success -> result.data
            is Result.Error -> result.throwable
        }
        throw Throwable("getAppConfig : not work")
    }

    suspend fun getAdConfig(zoneId: String): AdConfig {
        when (val result = createCall { apiService.getAdConfig(zoneId) }) {
            is Result.Success -> result.data
            is Result.Error -> result.throwable
        }
        throw Throwable("getAdConfig : not work")
    }
}