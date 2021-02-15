package com.nezamipour.mehdi.admediator.mainclasses.listeners

interface AdShowCallback {

    fun onOpened()

    fun onClosed()

    fun onRewarded()

    fun onError(message: String?)
}