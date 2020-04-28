package com.example.picabee

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    val liveData by lazy { MutableLiveData<ImageEntity>() }
    private val repository = HomeRepository.getInstance()

    fun searchImage() {
        viewModelScope.launch {
            repository.searchImage().also { liveData.postValue(it) }
        }
    }
}