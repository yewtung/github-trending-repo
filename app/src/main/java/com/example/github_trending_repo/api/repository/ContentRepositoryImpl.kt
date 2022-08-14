package com.example.github_trending_repo.api.repository

import com.example.github_trending_repo.api.entity.TrendingRepository
import com.example.github_trending_repo.api.network.service.ContentService

class ContentRepositoryImpl(
    private val contentService: ContentService,
) : ContentRepository {

    override suspend fun getList(): ApiCallState {
        val response: List<TrendingRepository> = contentService.getListAsync("daily").await()
         return ApiCallState.COMPLETED(response)
    }
}
