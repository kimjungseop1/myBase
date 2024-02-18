package com.example.myapplication1.ui.component.splash

import com.example.myapplication1.AppData
import com.example.myapplication1.ui.base.BaseViewModel
import com.google.firebase.messaging.FirebaseMessaging


class SplashViewModel : BaseViewModel() {
    fun getFcmToken() {
        //TODO FCM Token 생성
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                AppData.FcmToken = task.result
            }
        }
    }
}