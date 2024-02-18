package com.example.myapplication1.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PostLoginResponse(
    @SerializedName("accessToken")
    @Expose
    var accessToken: String = "",

    @SerializedName("userUid")
    @Expose
    var userUid: Long = 0,

    @SerializedName("userNickname")
    @Expose
    var userNickname: String = ""
)
