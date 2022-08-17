package com.example.github_trending_repo.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.github_trending_repo.api.entity.TrendingRepository
import com.example.github_trending_repo.api.repository.ApiCallState
import com.example.github_trending_repo.api.repository.ContentRepository
import kotlinx.coroutines.launch


class SharedViewModel(
    private val contentRepository: ContentRepository

) : ViewModel() {
    val getListState = MutableLiveData<ApiCallState>()
    val trendingList = contentRepository.trendingList
    private var sortingIndex = 0

    fun getList(index: Int? = null) = viewModelScope.launch {
        if(index!=null) sortingIndex = index
        getListState.value = ApiCallState.LOADING()
        getListState.value = contentRepository.getList(sortingIndex)
    }
}