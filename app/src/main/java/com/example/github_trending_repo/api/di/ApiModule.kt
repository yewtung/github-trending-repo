package com.example.github_trending_repo.api.di

import org.kodein.di.Kodein

class ApiModule
private const val MODULE_NAME = "Api Module"

val apiModule = Kodein.Module(MODULE_NAME, false) {
    import(networkModule)
    import(repositoryModule)
}
