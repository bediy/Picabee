package com.example.picabee

class HomeRepository private constructor() {

    suspend fun searchImage() = ApiFactory.api.searchImageAsync(q = "yellow+flowers").await()

    companion object {
        @Volatile
        private var instance: HomeRepository? = null
        fun getInstance() = instance ?: synchronized(this) {
            instance ?: HomeRepository().also { instance = it }
        }
    }
}