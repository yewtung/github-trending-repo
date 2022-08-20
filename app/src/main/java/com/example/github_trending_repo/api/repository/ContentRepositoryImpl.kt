package com.example.github_trending_repo.api.repository

import androidx.lifecycle.MutableLiveData
import com.example.github_trending_repo.api.entity.TrendingRepository
import com.example.github_trending_repo.api.network.service.ContentService
import retrofit2.Response

class ContentRepositoryImpl(
    private val contentService: ContentService,
    ) : BaseRepository(), ContentRepository {
    override var trendingList = MutableLiveData<List<TrendingRepository>>()

    override suspend fun getList(sortingIndex: Int): ApiCallState {
        val response: Response<List<TrendingRepository>> =
            contentService.getListAsync("daily").await()
        return resolveResponse(response) {
            response.body()?.let { list ->
                when (sortingIndex) {
                    1 -> {
                        trendingList.value = list.sortedWith(compareBy { it.stars })
                    }
                    2 -> {
                        trendingList.value = list.sortedWith(compareBy { it.name })
                    }
                    else -> {
                        trendingList.value = list
                    }
                }
            }
        }
    }
}
