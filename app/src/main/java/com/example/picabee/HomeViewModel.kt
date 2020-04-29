package com.example.picabee

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {
    val liveData by lazy { MutableLiveData<ImageEntity>() }
    val imageLiveData = repository.loadImage()

    fun searchImage() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.searchImage().also {
//                liveData.postValue(it)
                repository.insertImage(it.hits)
            }
        }
    }
}