package com.example.github_trending_repo.api.network.core

import android.content.Context
import android.net.ConnectivityManager
import com.example.github_trending_repo.database.RoomDB
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptorImpl(
    context: Context
) : ConnectivityInterceptor {
    private val appContext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isOnline())
            throw NoConnectivityException()
        return chain.proceed(chain.request())
    }

    private fun isOnline(): Boolean {
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE)
        as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
