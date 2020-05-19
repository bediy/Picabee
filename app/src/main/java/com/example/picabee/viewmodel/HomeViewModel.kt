package com.example.picabee.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.picabee.repository.HomeRepository

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {
    var pagedLiveData = repository.loadImage(viewModelScope)

    fun refresh(key: String) =
        repository.refresh(key, viewModelScope)
}