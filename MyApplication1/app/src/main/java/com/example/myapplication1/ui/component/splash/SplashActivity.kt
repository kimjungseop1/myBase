package com.example.myapplication1.ui.component.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.viewModels
import com.example.myapplication1.databinding.ActivitySplashBinding
import com.example.myapplication1.ui.base.BaseActivity
import com.example.myapplication1.ui.component.home.HomeActivity
import com.example.myapplication1.ui.component.login.LoginActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val splashViewModel : SplashViewModel by viewModels()

    override fun observeViewModel() {
        splashViewModel.getFcmToken()
    }

    override fun initViewBinding() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        }

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateToMainScreen()
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }

    private fun navigateToMainScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()
        }, 1500)
    }
}