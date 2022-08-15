package com.example.github_trending_repo.api.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class TrendingRepository(

    var author: String,

    var name: String,

    @SerializedName("avatar")
    var image: String,

    var stars: Int,

    var forks: Int,

    var language: String,

    var description: String,
) : Serializable {

    var isExpand: Boolean = false
}