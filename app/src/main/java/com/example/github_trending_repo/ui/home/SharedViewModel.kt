package com.example.github_trending_repo.ui.home

import android.app.Application
import android.app.PendingIntent.getActivity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.github_trending_repo.MainActivity
import com.example.github_trending_repo.api.entity.TrendingRepository
import com.example.github_trending_repo.api.repository.ApiCallState
import com.example.github_trending_repo.api.repository.ContentRepository
import com.example.github_trending_repo.database.RoomDB
import kotlinx.coroutines.launch
import java.io.IOException


class SharedViewModel(
    private val contentRepository: ContentRepository,
    app: Application
) : AndroidViewModel(app) {
    val getListState = MutableLiveData<ApiCallState>()
    var trendingList = MutableLiveData<List<TrendingRepository>>()
    val list = contentRepository.trendingList
    val mediator = MediatorLiveData<Unit>()
    private var sortingIndex = 0

    init {
        mediator.addSource(list) {
            trendingList.value = it
        }
    }

    fun getList(index: Int? = null,  context:Context) = viewModelScope.launch {
        getListState.value = ApiCallState.LOADING()
        if (index != null) sortingIndex = index
        try {
            if (hasInternetConnection(context)) {
                getListState.value = contentRepository.getList(sortingIndex)
            } else {
                getListState.value = ApiCallState.COMPLETED(getTrendingList())
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> ApiCallState.ERROR("Network  failure")
                else -> ApiCallState.ERROR("error")
            }
        }
        print(getListState.value)
    }

    fun getTrendingListObservers(): MutableLiveData<List<TrendingRepository>> {
        return trendingList
    }

    private fun getTrendingList(): List<TrendingRepository>? {
        val trendingRepositoryDao =
            RoomDB.getAppDatabase((getApplication()))?.trendingRepositoryDao()
        val list = trendingRepositoryDao?.getAllUserInfo()
        print(list)
        return list
    }

    fun insertTrendingRepository(entity: TrendingRepository , context:Context) {
        val trendingRepositoryDao = RoomDB.getAppDatabase(context)?.trendingRepositoryDao()
        trendingRepositoryDao?.insertUser(entity)
        getTrendingList()
        print(entity);
    }

    fun updateTrendingRepository(entity: TrendingRepository) {
        val trendingRepositoryDao = RoomDB.getAppDatabase(getApplication())?.trendingRepositoryDao()
        trendingRepositoryDao?.updateUser(entity)
    }

    fun deleteTrendingRepository(entity: TrendingRepository) {
        val trendingRepositoryDao = RoomDB.getAppDatabase(getApplication())?.trendingRepositoryDao()
        trendingRepositoryDao?.deleteUser(entity)
    }

    private fun hasInternetConnection(context:Context): Boolean {
//        val connectivityManager = getApplication<MyApplication>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        connectivityManager.activeNetworkInfo?.run {
//            return when (type){
//                TYPE_WIFI -> true
//                TYPE_MOBILE -> true
//                TYPE_ETHERNET -> true
//                else -> false
//            }
//        }
//        return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager?.getNetworkCapabilities(connectivityManager.activeNetwork)
                    ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        }
        // For below 29 api
        else {
            if (connectivityManager?.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnectedOrConnecting) {
                return true
            }
        }
        return false
    }

    fun safeCallGetList(index: Int?) = viewModelScope.launch {
        getListState.value = ApiCallState.LOADING()

    }
}