package com.example.myapplication1.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GetHomeScreenResponse(
    @SerializedName("newAdoptList")
    @Expose
    val newAdoptList: ArrayList<NewAdoptList>,

    @SerializedName("newsList")
    @Expose
    val newsList: ArrayList<NewsList>,

    @SerializedName("bannerList")
    @Expose
    val bannerList: ArrayList<BannerList>
)

data class NewAdoptList(
    @SerializedName("uid")
    @Expose
    var uid: Long? = null,

    @SerializedName("imageFullUrl")
    @Expose
    var imageFullUrl: String? = null,

    @SerializedName("adoptTypeLabel")
    @Expose
    var adoptTypeLabel: String? = null,

    @SerializedName("speciesLabel")
    @Expose
    var speciesLabel: String? = null,

    @SerializedName("breedLabel")
    @Expose
    var breedLabel: String? = null,

    @SerializedName("gender")
    @Expose
    var gender: String? = null,

    @SerializedName("age")
    @Expose
    var age: String? = null,

    @SerializedName("hasInsurance")
    @Expose
    var hasInsurance: Boolean = false
)

data class NewsList(
    @SerializedName("uid")
    @Expose
    var uid: Long? = null,

    @SerializedName("imageFullUrl")
    @Expose
    var imageFullUrl: String? = null,

    @SerializedName("htmlUrl")
    @Expose
    var htmlUrl: String? = null
)

data class BannerList(
    @SerializedName("uid") @Expose
    var uid: Long? = null,

    @SerializedName("imageFullUrl")
    @Expose
    var imageFullUrl: String? = null,

    @SerializedName("linkFullUrl")
    @Expose
    var linkFullUrl: String? = null
)
