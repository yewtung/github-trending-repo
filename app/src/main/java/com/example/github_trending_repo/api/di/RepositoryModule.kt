package com.example.github_trending_repo.api.di


import com.example.github_trending_repo.api.repository.ContentRepository
import com.example.github_trending_repo.api.repository.ContentRepositoryImpl
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class RepositoryModule
private const val MODULE_NAME = "Repository Module"

val repositoryModule = Kodein.Module(MODULE_NAME, false) {

    bind<ContentRepository>() with singleton {
        ContentRepositoryImpl(instance())
    }
}
