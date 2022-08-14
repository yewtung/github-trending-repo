package com.example.github_trending_repo.api.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


 class TrendingRepository {
    @SerializedName("author")
    @Expose
    var author: String? = null


    @SerializedName("name")
    @Expose
    var name: String? = null
}
