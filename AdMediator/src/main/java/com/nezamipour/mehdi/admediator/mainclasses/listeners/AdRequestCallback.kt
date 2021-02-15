package com.nezamipour.mehdi.admediator.mainclasses.listeners

interface AdRequestCallback {

    fun onSuccess(adId: String?)

    fun error(message: String?)

}