package com.example.github_trending_repo.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.github_trending_repo.api.repository.ApiCallState
import com.example.github_trending_repo.api.repository.ContentRepository
import kotlinx.coroutines.launch


class HomeViewModel(
    private val contentRepository: ContentRepository

): ViewModel(){
    val getListState = MutableLiveData<ApiCallState>()

     fun getList()= viewModelScope.launch {
         print("getlist outside")
         getListState.value = ApiCallState.LOADING("")
        val state = contentRepository.getList()
         if(state is ApiCallState.COMPLETED){
             getListState.value = state
             print("getList(")
             print(getListState)
         }
    }
}