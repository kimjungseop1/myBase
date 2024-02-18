package com.example.myapplication1.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PostLoginRequest(
    @SerializedName("snsCode")
    @Expose
    var snsCode: String = "",

    @SerializedName("snsType")
    @Expose
    var snsType: String = "",

    @SerializedName("fcmToken")
    @Expose
    var fcmToken: String = ""
)
