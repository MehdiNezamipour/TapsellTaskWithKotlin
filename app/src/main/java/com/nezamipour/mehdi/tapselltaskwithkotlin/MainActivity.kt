package com.nezamipour.mehdi.tapselltaskwithkotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nezamipour.mehdi.admediator.mainclasses.AdMediator
import com.nezamipour.mehdi.tapselltaskwithkotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonRewardedVideo.setOnClickListener {
            startActivity(RewardedVideoActivity.newIntent(this))
        }

        binding.buttonInterstitialBanner.setOnClickListener {
            startActivity(InterstitialBannerActivity.newIntent(this))
        }

        binding.buttonInterstitialVideo.setOnClickListener {
            startActivity(InterstitialVideoActivity.newIntent(this))
        }


    }
}