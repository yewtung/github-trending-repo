package com.example.github_trending_repo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.github_trending_repo.api.entity.TrendingRepository


@Database(entities = [TrendingRepository::class], version = 1)
abstract class RoomDB : RoomDatabase() {

    abstract fun trendingRepositoryDao():  TrendingRepositoryDao?

    companion object {
        private var INSTANCE: RoomDB? = null

        val migration_1_2: Migration = object : Migration(1,2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                //database.execSQL("ALTER TABLE trending_repo ADD COLUMN phone TEXT DEFAULT ''")
            }
        }

        fun getAppDatabase(context: Context): RoomDB? {

            if (INSTANCE == null) {

                INSTANCE = Room.databaseBuilder<RoomDB>(
                    context.applicationContext, RoomDB::class.java, "AppDBB"
                )
                    .addMigrations(migration_1_2)
                    .allowMainThreadQueries()
                    .build()

            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

}