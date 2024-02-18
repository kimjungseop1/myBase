package com.example.myapplication1.network

import androidx.lifecycle.MutableLiveData
import com.example.myapplication1.AppData
import com.example.myapplication1.model.GetHomeScreenResponse
import com.example.myapplication1.model.GetNewsAllResponse
import com.example.myapplication1.model.PostLoginRequest
import com.example.myapplication1.model.PostLoginResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FriendRepository {
    companion object {
        private const val BASE_URL = "https://petlinkfamily.co.kr/"
    }

    private var friendsInterface: FriendsInterface

    ////////////////////////////////////////////////////////////////////////////////////////////////
    val loginLiveDataRepository: MutableLiveData<NetworkResult<PostLoginResponse>> =
        MutableLiveData()
    val homeScreenLiveDataRepository: MutableLiveData<NetworkResult<GetHomeScreenResponse>> =
        MutableLiveData()
    val homeNewsLiveDataRepository: MutableLiveData<NetworkResult<GetNewsAllResponse>> =
        MutableLiveData()

    ////////////////////////////////////////////////////////////////////////////////////////////////

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val interceptorAuth = Interceptor { chain: Interceptor.Chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer ${AppData.AccessToken}").build()
            chain.proceed(request)
        }

        val client: okhttp3.OkHttpClient = okhttp3.OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(interceptorAuth)
            .retryOnConnectionFailure(true)
            .build()

        val gson: Gson = GsonBuilder().setLenient().create()

        friendsInterface = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(FriendsInterface::class.java)
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    fun requestLogin(postLoginRequest: PostLoginRequest) {
        friendsInterface.postLogin(postLoginRequest).enqueue(object :
            Callback<PostLoginResponse> {

            override fun onResponse(
                call: Call<PostLoginResponse>,
                response: Response<PostLoginResponse>
            ) {
                val headers = response.headers()
                val authorization = headers["Authorization"]
                if (authorization != null) {
                    AppData.AccessToken = authorization
                }

                val apiErrorCode = headers["api-error-code"]
                if (apiErrorCode != null) {
                    if (apiErrorCode == "1") {
                        loginLiveDataRepository.postValue(NetworkResult.Error("1"))
                        return
                    }

                    if (apiErrorCode == "2") {
                        loginLiveDataRepository.postValue(NetworkResult.Error("2"))
                        return
                    }

                    if (apiErrorCode == "b") {
                        loginLiveDataRepository.postValue(NetworkResult.Error("b"))
                        return
                    }
                }

                if (response.code() == 200) {
                    loginLiveDataRepository.postValue(NetworkResult.Success(response.body()!!))
                } else {
                    loginLiveDataRepository.postValue(
                        NetworkResult.Error(
                            response.message().toString()
                        )
                    )
                }
            }

            override fun onFailure(call: Call<PostLoginResponse>, t: Throwable) {
                loginLiveDataRepository.postValue(NetworkResult.Error(t.message))
            }
        })
    }

    fun requestHome(nowLat: String) {
        friendsInterface.getScreenHome(nowLat).enqueue(object :
            Callback<GetHomeScreenResponse> {
            override fun onResponse(
                call: Call<GetHomeScreenResponse>,
                response: Response<GetHomeScreenResponse>
            ) {
                val headers = response.headers()
                val authorization = headers["Authorization"]
                if (authorization != null) {
                    AppData.AccessToken = authorization
                }

                val apiErrorCode = headers["api-error-code"]
                if (apiErrorCode != null) {
                    if (apiErrorCode == "b") {
                        homeScreenLiveDataRepository.postValue(NetworkResult.Error("b"))
                        return
                    }
                }

                if (response.code() == 200) {
                    homeScreenLiveDataRepository.postValue(NetworkResult.Success(response.body()))
                } else {
                    homeScreenLiveDataRepository.postValue(
                        NetworkResult.Error(
                            response.code().toString()
                        )
                    )
                }
            }

            override fun onFailure(call: Call<GetHomeScreenResponse>, t: Throwable) {
                t.printStackTrace()
                homeScreenLiveDataRepository.postValue(NetworkResult.Error(t.message))
            }
        })
    }

    fun requestHomeNews(page: Int) {
        friendsInterface.getNewsList(page, 10).enqueue(object :
            Callback<GetNewsAllResponse> {
            override fun onResponse(
                call: Call<GetNewsAllResponse>,
                response: Response<GetNewsAllResponse>
            ) {
                val headers = response.headers()
                val authorization = headers["Authorization"]
                if (authorization != null) {
                    AppData.AccessToken = authorization
                }

                val apiErrorCode = headers["api-error-code"]
                if (apiErrorCode != null) {
                    homeNewsLiveDataRepository.postValue(NetworkResult.Error("b"))
                    return
                }

                if (response.code() == 200) {
                    homeNewsLiveDataRepository.postValue(NetworkResult.Success(response.body()))
                } else {
                    homeNewsLiveDataRepository.postValue(
                        NetworkResult.Error(
                            response.code().toString()
                        )
                    )
                }
            }

            override fun onFailure(call: Call<GetNewsAllResponse>, t: Throwable) {
                t.printStackTrace()
                homeNewsLiveDataRepository.postValue(NetworkResult.Error(t.message))
            }
        })
    }
}