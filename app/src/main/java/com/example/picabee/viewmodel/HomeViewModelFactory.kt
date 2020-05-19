package com.example.picabee.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.picabee.repository.HomeRepository

class HomeViewModelFactory(private val homeRepository: HomeRepository)
    : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(homeRepository) as T
    }
}