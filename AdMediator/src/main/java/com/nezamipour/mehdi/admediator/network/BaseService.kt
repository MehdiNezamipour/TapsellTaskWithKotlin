package com.nezamipour.mehdi.admediator.network

import retrofit2.Response


abstract class BaseService {

    protected suspend fun <T> createCall(call: suspend () -> Response<T>): Result<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return Result.Success(body)
                }
            }
            return Result.Error(Throwable(response.errorBody().toString()))
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }


}
