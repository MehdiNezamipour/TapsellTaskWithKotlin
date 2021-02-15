package com.nezamipour.mehdi.admediator.utils

class Constants {

    companion object {


        // Our Mediator
        const val AD_MEDIATOR_INTERSTITIAL_BANNER = "INTERSTITIAL_BANNER_ZONE_ID"
        const val AD_MEDIATOR_INTERSTITIAL_VIDEO = "INTERSTITIAL_VIDEO_ZONE_ID"
        const val AD_MEDIATOR_REWARDED_VIDEO = "REWARDED_VIDEO_ZONE_ID"

        //TapSell
        const val TAPSELL_KEY =
            "kttjtpmdehsmnhlkkrlfekisnfifqtdallotfeccaspodsnqspelhcinjjdbiqtmhaglsn"
        const val TAPSELL_INTERSTITIAL_BANNER = "5caae7c33a2e170001ef9392"
        const val TAPSELL_INTERSTITIAL_VIDEO = "5caaeffec1ed8b000149cedc"
        const val TAPSELL_REWARDED_VIDEO = "5caaf03dc1ed8b000149cedd"


        //UnityAd
        const val UNITY_AD_INTERSTITIAL_BANNER = "UNITY_AD_INTERSTITIAL_BANNER"
        const val UNITY_AD_INTERSTITIAL_VIDEO = "UNITY_AD_INTERSTITIAL_VIDEO"
        const val UNITY_AD_REWARDED_VIDEO = "UNITY_AD_REWARDED_VIDEO"


        //Chartboost
        const val CHART_BOOST_INTERSTITIAL_BANNER = "CHART_BOOST_INTERSTITIAL_BANNER"
        const val CHART_BOOST_INTERSTITIAL_VIDEO = "CHART_BOOST_INTERSTITIAL_VIDEO"
        const val CHART_BOOST_REWARDED_VIDEO = "CHART_BOOST_REWARDED_VIDEO"

        const val APP_CONFIG = "{\n" +
                "\"adNetworks\" : {\n" +
                "\"Tapsell\" : \"appIdInTapsell\",\n" +
                "\"UnityAds\" : \"appIdInUnityAds\",\n" +
                "\"Chartboost\" : \"appIdInChartboost\"\n" +
                "}\n" +
                "}"

        const val AD_CONFIG = "{\n" +
                "\"zoneType\": \"Interstitial\",\n" +
                "\"waterfall\": [\n" +
                "{\n" +
                "\"adNetwork\": \"UnityAds\",\n" +
                "\"zoneId\": \"zoneIdInUnityAds\",\n" +
                "\"timeout\": 2000\n" +
                "},\n" +
                "{\n" +
                "\"adNetwork\": \"Tapsell\",\n" +
                "\"zoneId\": \"zoneIdInTapsell\",\n" +
                "\"timeout\": 3000\n" +
                "},\n" +
                "{\n" +
                "\"adNetwork\": \"Chartboost\",\n" +
                "\"zoneId\": \"zoneIdInChartboost\",\n" +
                "\"timeout\": 1000\n" +
                "}\n" +
                "],\n" +
                "\"ttl\": 3600000\n" +
                "}"


    }

}