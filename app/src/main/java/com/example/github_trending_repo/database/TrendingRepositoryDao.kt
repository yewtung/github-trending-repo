package com.example.github_trending_repo.database

import androidx.room.*
import com.example.github_trending_repo.api.entity.TrendingRepository

@Dao
interface TrendingRepositoryDao {
    @Query("SELECT * FROM trending_repo ORDER BY id")
    fun getAllUserInfo(): List<TrendingRepository>?

    @Insert
    fun insertUser(user: TrendingRepository?)

    @Delete
    fun deleteUser(user: TrendingRepository?)

    @Update
    fun updateUser(user: TrendingRepository?)

    @Query("SELECT * FROM trending_repo ORDER BY stars")
    fun orderByStars(): List<TrendingRepository>?

    @Query("SELECT * FROM trending_repo ORDER BY forks")
    fun orderByForks(): List<TrendingRepository>?

}
