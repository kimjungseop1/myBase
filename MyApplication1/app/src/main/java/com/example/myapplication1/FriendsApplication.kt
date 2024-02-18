package com.example.myapplication1

import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.naver.maps.map.NaverMapSdk

class FriendsApplication : Application() {
    private var instance: FriendsApplication? = null

    private val naver_map_client_id = "a0fvkf16bm"

    private val naver_map_client_secret = "9BwrhKJlYBuJzsmGbrnyv4MY6eruNZlzpIliWnEf"

    private val kakao_app_key = "20198f95c7d32d26a6d039be7b696508"

    //TODO 공공주소 검색
    val juso_key = "U01TX0FVVEgyMDIyMDYwNzE2MzEyMjExMjY1NzA="

    fun getGlobalApplicationContext(): FriendsApplication? {
        checkNotNull(instance) { "FriendsApplication instance is null" }
        return instance
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        Log.e("jung", "KeyHash : " + Utility.getKeyHash(this))

        NaverMapSdk.getInstance(this).client = NaverMapSdk.NaverCloudPlatformClient(naver_map_client_id)

        KakaoSdk.init(this, kakao_app_key)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

    }
}