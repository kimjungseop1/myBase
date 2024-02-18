package com.example.myapplication1.ui.component.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication1.AppData.FcmToken
import com.example.myapplication1.model.PostLoginRequest
import com.example.myapplication1.model.PostLoginResponse
import com.example.myapplication1.network.FriendRepository
import com.example.myapplication1.network.NetworkResult
import com.example.myapplication1.ui.base.BaseViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.User
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel() {
    private val friendRepository: FriendRepository = FriendRepository()
    private var kakaoResponseLiveData: LiveData<NetworkResult<PostLoginResponse>> = friendRepository.loginLiveDataRepository

    private fun getKaKaoProfile(context: Context, snsToken: String) {
        UserApiClient.instance.me { user: User?, throwable: Throwable? ->
            if (user != null) {
                //TODO 로그인 api
                val nick = user.kakaoAccount!!.profile!!.nickname
                requestLogin(snsToken, nick!!)
            } else {
                Toast.makeText(context, "계정 정보가 없습니다.", Toast.LENGTH_SHORT).show()
            }
            throwable?.printStackTrace()
        }
    }

    fun kakaoLogin(context: Context) {
        viewModelScope.launch {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                UserApiClient.instance.loginWithKakaoTalk(context) { token: OAuthToken?, error: Throwable? ->
                    if (token != null) {
                        val snsToken = token.accessToken
                        Log.e("jung", snsToken)
                        getKaKaoProfile(context, snsToken)
                    }

                    error?.printStackTrace()
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(context) { token: OAuthToken?, error: Throwable? ->
                    if (token != null) {
                        val snsToken = token.accessToken
                        getKaKaoProfile(context, snsToken)
                    }

                    error?.printStackTrace()
                }
            }
        }
    }

    fun kakaoLogout() {
        viewModelScope.launch {
            UserApiClient.instance.logout { throwable: Throwable? ->
                if (throwable != null) {
                    Log.e("jung", "로그아웃 실패 : " + throwable.message)
                } else {
                    Log.e("jung", "로그아웃 성공 : ")
                }
            }
        }
    }

    fun kakaoUnlink() {
        viewModelScope.launch {
            UserApiClient.instance.unlink { error ->
                if (error != null) {
                    Log.e("jung","연결 끊기 실패")
                } else {
                    Log.e("jung","연결 끊기 성공")
                }
            }
        }
    }

    private fun requestLogin(snsToken: String, nick: String) {
        viewModelScope.launch {
            val postLoginRequest = PostLoginRequest()
            postLoginRequest.apply {
                snsCode = snsToken
                snsType = "kakao"
                fcmToken = FcmToken
            }

            friendRepository.requestLogin(postLoginRequest)
        }
    }

    fun kakaoLoginResponseLiveData(): LiveData<NetworkResult<PostLoginResponse>> {
        return kakaoResponseLiveData
    }

}