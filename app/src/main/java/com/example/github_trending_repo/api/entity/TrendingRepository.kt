package com.example.github_trending_repo.api.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "trending_repo")
data class TrendingRepository(
    @PrimaryKey(autoGenerate = true,)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "author") var author: String,

    @ColumnInfo(name = "name") var name: String,

    @ColumnInfo(name = "image")
    @SerializedName("avatar")
    var image: String,

    @ColumnInfo(name = "stars") var stars: Int,

    @ColumnInfo(name = "forks") var forks: Int,

    @ColumnInfo(name = "language") var language: String,

    @ColumnInfo(name = "description") var description: String,
) : Serializable {

    var isExpand: Boolean = false
}