package com.example.github_trending_repo

import android.app.Application
import com.example.github_trending_repo.api.di.apiModule
import com.example.github_trending_repo.api.di.networkModule
import com.example.github_trending_repo.api.di.repositoryModule
import com.example.github_trending_repo.api.di.viewModelModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule

class MyApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@MyApplication))
        import(apiModule)
        import(viewModelModule)
    }

    override fun onCreate() {
        super.onCreate()
    }
}
