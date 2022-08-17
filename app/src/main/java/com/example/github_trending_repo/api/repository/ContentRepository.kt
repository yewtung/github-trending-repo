package com.example.github_trending_repo.api.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.github_trending_repo.api.entity.TrendingRepository

interface ContentRepository {
    val trendingList: LiveData<List<TrendingRepository>>

    suspend fun getList(sortingIndex: Int): ApiCallState
}
