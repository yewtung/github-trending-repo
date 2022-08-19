package com.example.github_trending_repo.api.network.service

import com.example.github_trending_repo.api.entity.TrendingRepository
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ContentService {

    @GET("/repositories")
    fun getListAsync(
        @Query("since") since: String,
    ): Deferred<Response<List<TrendingRepository>>>

}
