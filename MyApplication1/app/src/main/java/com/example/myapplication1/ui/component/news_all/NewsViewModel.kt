package com.example.myapplication1.ui.component.news_all

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication1.model.GetNewsAllResponse
import com.example.myapplication1.network.FriendRepository
import com.example.myapplication1.network.NetworkResult
import com.example.myapplication1.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class NewsViewModel: BaseViewModel() {
    private lateinit var friendRepository: FriendRepository
    private lateinit var newsLiveData: LiveData<NetworkResult<GetNewsAllResponse>>

    init {
        viewModelScope.launch {
            friendRepository = FriendRepository()
            newsLiveData = friendRepository.homeNewsLiveDataRepository
        }
    }

    fun getHomeNewsData(page: Int) {
        viewModelScope.launch {
            friendRepository.requestHomeNews(page)
        }
    }

    fun getHomeNewsScreenData() : LiveData<NetworkResult<GetNewsAllResponse>> {
        return newsLiveData
    }
}