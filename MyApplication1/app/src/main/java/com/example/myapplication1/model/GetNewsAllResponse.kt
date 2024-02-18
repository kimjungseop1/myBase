package com.example.myapplication1.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GetNewsAllResponse(
    @SerializedName("newsList")
    @Expose
    val newsList: ArrayList<NewsDtoList>,

    @SerializedName("totalItemCount")
    @Expose
    val totalItemCount: Int? = null
)

data class NewsDtoList(
    @SerializedName("imageFullUrl")
    @Expose
    var imageFullUrl: String? = null,

    @SerializedName("uid")
    @Expose
    var uid: Long? = null,

    @SerializedName("htmlUrl")
    @Expose
    var htmlUrl: String? = null
)