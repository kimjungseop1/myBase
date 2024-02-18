package com.example.myapplication1.ui.component.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.myapplication1.AppData
import com.example.myapplication1.databinding.ActivityLoginBinding
import com.example.myapplication1.network.NetworkResult
import com.example.myapplication1.ui.base.BaseActivity
import com.example.myapplication1.ui.component.home.HomeActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun observeViewModel() {
        CoroutineScope(Dispatchers.Main).launch {
            loginViewModel.kakaoLoginResponseLiveData().observe(this@LoginActivity, Observer {
                when(it) {
                    is NetworkResult.Success -> {
                        AppData.myUid = it.data!!.userUid
                        AppData.myNickName = it.data.userNickname
                        AppData.AccessToken = it.data.accessToken

                        goMain()
                    }

                    is NetworkResult.Error -> {
                        Log.e("jung","NetworkResult error : ${it.message}")
                        if (it.message.equals("1")) {
                            Toast.makeText(this@LoginActivity, "로그인 정보 불일치", Toast.LENGTH_SHORT).show()
                        } else if (it.message.equals("2")) {
                            Toast.makeText(this@LoginActivity, "가입 필요", Toast.LENGTH_SHORT).show()
                            goJoin()
                        } else if (it.message.equals("b")) {
                            AppData.AccessToken = ""
                            goMain()
                        }
                    }
                }
            })
        }
    }

    override fun initViewBinding() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initKakaoLoginListeners()
        initCloseBtnListeners()
    }

    private fun initKakaoLoginListeners() {
        binding.kakaoLoginBtn.setOnClickListener {
            loginViewModel.kakaoLogin(this@LoginActivity)
        }
    }

    private fun initCloseBtnListeners() {

        binding.closeBtn.setOnClickListener {
            finish()
        }
    }

    private fun goMain() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
        finish()
    }

    private fun goJoin() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
        finish()
    }
}