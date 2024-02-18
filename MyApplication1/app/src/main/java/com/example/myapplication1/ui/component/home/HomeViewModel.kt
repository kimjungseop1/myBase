package com.example.myapplication1.ui.component.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication1.model.GetHomeScreenResponse
import com.example.myapplication1.network.FriendRepository
import com.example.myapplication1.network.NetworkResult
import com.example.myapplication1.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {
    private var friendRepository: FriendRepository = FriendRepository()
    private var homeScreenLiveData: LiveData<NetworkResult<GetHomeScreenResponse>> =
        friendRepository.homeScreenLiveDataRepository

    fun getHomeData(nowLat: String) {
        viewModelScope.launch {
            friendRepository.requestHome(nowLat)
        }
    }

    fun getHomeScreenData(): LiveData<NetworkResult<GetHomeScreenResponse>> {
        return homeScreenLiveData
    }
}