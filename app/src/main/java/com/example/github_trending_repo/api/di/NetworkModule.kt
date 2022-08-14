package com.example.github_trending_repo.api.di

import android.content.Context
import com.example.github_trending_repo.api.network.core.*
import com.example.github_trending_repo.api.network.service.ContentService
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import io.github.wax911.library.converter.GraphConverter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


private const val MODULE_NAME = "Network Module"

class NetworkModule

private const val TAG_SERVER_URL = "server_url_const"
const val NETWORK_AVAILABLE_AGE = 60
private val SERVER_ERROR_CODES = listOf(404, 504, 400, 401, 500, 403)
private val url = "https://private-anon-fc551db9c8-githubtrendingapi.apiary-mock.com"

val networkModule = Kodein.Module(MODULE_NAME, false) {
    bind<ConnectivityInterceptor>() with singleton {
        ConnectivityInterceptorImpl(
            instance()
        )
    }
    bind<OkHttpClient>() with singleton { okHttpClient(instance()) }
    bind<Retrofit>() with singleton { getMockRetrofit(instance(), instance()) }
    bind<ContentService>() with singleton { instanceService(instance(), ContentService::class.java) }
}

private fun okHttpClient(connectivityInterceptor: ConnectivityInterceptor) = OkHttpClient.Builder()
    .connectTimeout(30, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .addInterceptor(HttpLoggingInterceptor().also {
        it.level = HttpLoggingInterceptor.Level.BODY
    })
    .addInterceptor(connectivityInterceptor)
    .build()

private fun getMockRetrofit(okHttpClient: OkHttpClient, context: Context): Retrofit = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl(url)
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()


private fun <T> instanceService(retrofit: Retrofit, apiService: Class<T>, baseUrl: String = url): T {
    return retrofit
        .newBuilder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(apiService)
}