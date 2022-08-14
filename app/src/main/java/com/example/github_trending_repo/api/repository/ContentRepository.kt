package com.example.github_trending_repo.api.repository

interface ContentRepository {

    suspend fun getList() : ApiCallState
}
