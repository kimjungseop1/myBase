package com.example.myapplication1.network

import com.example.myapplication1.model.GetHomeScreenResponse
import com.example.myapplication1.model.GetNewsAllResponse
import com.example.myapplication1.model.PostLoginRequest
import com.example.myapplication1.model.PostLoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FriendsInterface {
    //TODO 3.64 로그인
    @POST("api/mobile/auth/sns-login")
    fun postLogin(
        @Body postLoginRequest: PostLoginRequest
    ): Call<PostLoginResponse>

    @GET("api/mobile/screen/home")
    fun getScreenHome(
        @Query(value = "nowLatAndLng", encoded = true) nowLatAny: Any
    ): Call<GetHomeScreenResponse>

    //TODO 3.4 펫링크 소식 리스트
    @GET("api/mobile/news/all/{page}")
    fun getNewsList(
        @Path(value = "page", encoded = true) page: Int,
        @Query(value = "itemCountLimit", encoded = true) itemCountLimit: Int
    ): Call<GetNewsAllResponse>
}