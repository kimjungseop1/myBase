package com.example.myapplication1.ui.component.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.myapplication1.AppData
import com.example.myapplication1.databinding.ActivityHomeBinding
import com.example.myapplication1.model.BannerList
import com.example.myapplication1.model.NewAdoptList
import com.example.myapplication1.model.NewsList
import com.example.myapplication1.network.NetworkResult
import com.example.myapplication1.ui.base.BaseActivity
import com.example.myapplication1.ui.component.home.adapter.NewContractListAdapter
import com.example.myapplication1.ui.component.home.adapter.NewsListHorizontalAdapter
import com.example.myapplication1.ui.component.home.adapter.SlideBannerAdapter
import com.example.myapplication1.ui.component.news_all.NewsActivity
import com.example.myapplication1.ui.component.pet_contract.PetContractListActivity
import com.example.myapplication1.ui.utils.FriendsCommon
import com.example.myapplication1.ui.utils.HorizontalMarginItemDecoration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeActivity : BaseActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()
    private val handler = Handler(Looper.getMainLooper())

    private var backKeyPressedTime: Long = 0
    private var toast: Toast? = null
    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
                backKeyPressedTime = System.currentTimeMillis()
                showGuide()
                return
            }
            if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
                this@HomeActivity.setResult(Activity.RESULT_OK)
                this@HomeActivity.finish()
                if (toast != null) {
                    toast!!.cancel()
                }
            }
        }

        private fun showGuide() {
            toast =
                Toast.makeText(this@HomeActivity, "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT)
            toast.run { this!!.show() }
        }
    }

    override fun observeViewModel() {
        CoroutineScope(Dispatchers.Main).launch {
            homeViewModel.apply {
                getHomeData("")

                getHomeScreenData().observe(this@HomeActivity, Observer {
                    when (it) {
                        is NetworkResult.Success -> {
                            //TODO 컨텐츠 배너
                            setAdBanner(it.data!!.bannerList)

                            //TODO 새로운 반려동물
                            setNewPet(it.data.newAdoptList)

                            //TODO 댕댕 정보통
                            setPetLinkNews(it.data.newsList)
                        }

                        is NetworkResult.Error -> {
                            Log.e("jung","NetworkResult error : ${it.message}")
                            if (it.message.equals("b")) {
                                AppData.AccessToken = ""
                            }
                        }
                    }
                })
            }
        }
    }

    override fun initViewBinding() {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoroutineScope(Dispatchers.Main).launch {
            //TODO 추천
            binding.recommendView.setOnClickListener {

            }

            //TODO 보험
            binding.insuranceView.setOnClickListener {

            }

        }

        //TODO 앱 종료 핸들러
        onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(slideRunnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(slideRunnable, 3000)
    }

    private fun setAdBanner(response: ArrayList<BannerList>) {
        val bannerAdapter = SlideBannerAdapter(this, binding.bannerPager, response)
        binding.bannerPager.apply {
            adapter = bannerAdapter
            offscreenPageLimit = response.size
        }
        binding.bannerPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (response.size > 1) {
                    handler.removeCallbacks(slideRunnable)
                    handler.postDelayed(slideRunnable, 3000)
                }
            }
        })

        handler.postDelayed(slideRunnable, 3000)
    }

    private val slideRunnable =
        Runnable { binding.bannerPager.currentItem = binding.bannerPager.currentItem + 1 }

    private fun setNewPet(response: ArrayList<NewAdoptList>) {
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        val newPetAdapter = NewContractListAdapter(this, response)
        binding.recyclerNewPet.apply {
            layoutManager = linearLayoutManager
            adapter = newPetAdapter
            addItemDecoration(
                HorizontalMarginItemDecoration(
                    this@HomeActivity,
                    FriendsCommon.ConvertDPtoPX(this@HomeActivity, 20),
                    true
                )
            )
        }

        newPetAdapter.setOnItemClickListener(object : NewContractListAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                val intent = Intent(this@HomeActivity, PetContractListActivity::class.java)
                intent.putExtra("aaaa", position)
                startActivity(intent)
                overridePendingTransition(0, 0)
            }
        })
    }

    private fun setPetLinkNews(response: ArrayList<NewsList>) {
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        val newsAdapter = NewsListHorizontalAdapter(this, response)
        binding.recyclerNews.apply {
            layoutManager = linearLayoutManager
            adapter = newsAdapter
            addItemDecoration(
                HorizontalMarginItemDecoration(
                    this@HomeActivity,
                    FriendsCommon.ConvertDPtoPX(this@HomeActivity, 20),
                    true
                )
            )
        }

        newsAdapter.setOnItemClickListener(object : NewsListHorizontalAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                Log.e("jung", "$position")
            }
        })

        binding.moreBtn.setOnClickListener {
            val intent = Intent(this, NewsActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
    }

}