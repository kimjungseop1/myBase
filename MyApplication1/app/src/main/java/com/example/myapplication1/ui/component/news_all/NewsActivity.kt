package com.example.myapplication1.ui.component.news_all

import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.AppData
import com.example.myapplication1.databinding.ActivityNewsBinding
import com.example.myapplication1.model.NewsDtoList
import com.example.myapplication1.network.NetworkResult
import com.example.myapplication1.ui.base.BaseActivity
import com.example.myapplication1.ui.component.news_all.adapter.NewsListVerticalAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsActivity : BaseActivity() {
    private lateinit var binding: ActivityNewsBinding
    private val newsViewModel: NewsViewModel by viewModels()
    private var mCurPage: Int = 1
    private var newsAllAdapter: NewsListVerticalAdapter? = null

    override fun observeViewModel() {
        CoroutineScope(Dispatchers.Main).launch {
            newsViewModel.apply {
                getHomeNewsData(mCurPage)

                getHomeNewsScreenData().observe(this@NewsActivity, Observer {
                    when (it) {
                        is NetworkResult.Success -> {
                            if (mCurPage == 1) {
                                setList(it.data!!.newsList)
                            } else {
                                if (it.data!!.newsList.size > 0) {
                                    newsAllAdapter?.addData(it.data.newsList)
                                }
                            }
                            Log.e("jung", "$mCurPage")
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
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }

    private fun setList(response: ArrayList<NewsDtoList>) {
        val linearLayoutManager = LinearLayoutManager(this)
        newsAllAdapter = NewsListVerticalAdapter(this, response)

        binding.recyclerNews.apply {
            layoutManager = linearLayoutManager
            adapter = newsAllAdapter
        }
        binding.recyclerNews.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val visibleItemCount = linearLayoutManager.childCount
                val totalItemCount = linearLayoutManager.itemCount
                val pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition()
                if (pastVisibleItems + visibleItemCount >= totalItemCount && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mCurPage += 1
                    CoroutineScope(Dispatchers.IO).launch {
                        newsViewModel.getHomeNewsData(mCurPage)
                    }
                }
            }
        })

        newsAllAdapter!!.setOnItemClickListener(object :
            NewsListVerticalAdapter.OnItemClickListener {
            override fun onClick(position: Int) {

            }
        })

    }
}