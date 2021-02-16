package com.nezamipour.mehdi.tapselltaskwithkotlin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.nezamipour.mehdi.admediator.mainclasses.AdMediator
import com.nezamipour.mehdi.admediator.mainclasses.listeners.AdRequestCallback
import com.nezamipour.mehdi.admediator.mainclasses.listeners.AdShowCallback
import com.nezamipour.mehdi.admediator.utils.Constants
import com.nezamipour.mehdi.tapselltaskwithkotlin.databinding.ActivityInterstitialBannerBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class InterstitialBannerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInterstitialBannerBinding
    private lateinit var adId: String

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, InterstitialBannerActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInterstitialBannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonRequestAd.setOnClickListener {
            CoroutineScope(IO).launch {
                AdMediator.requestAd(
                    context = this@InterstitialBannerActivity,
                    Constants.AD_MEDIATOR_INTERSTITIAL_BANNER,
                    object : AdRequestCallback {
                        override fun onSuccess(adId: String?) {
                            binding.buttonShowAd.isEnabled = true
                            if (adId != null) {
                                this@InterstitialBannerActivity.adId = adId
                            }
                        }

                        override fun error(message: String?) {
                            Toast.makeText(
                                this@InterstitialBannerActivity,
                                message,
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }

                    })
            }
        }

        binding.buttonShowAd.setOnClickListener {
            AdMediator.showAd(
                this,
                Constants.AD_MEDIATOR_INTERSTITIAL_BANNER,
                adId,
                object : AdShowCallback {
                    override fun onOpened() {
                        TODO("Not yet implemented")
                    }

                    override fun onClosed() {
                        TODO("Not yet implemented")
                    }

                    override fun onRewarded() {
                        TODO("Not yet implemented")
                    }

                    override fun onError(message: String?) {
                        TODO("Not yet implemented")
                    }

                })
        }

    }
}