package com.example.picabee

import com.example.picabee.db.ImageDao

class HomeRepository private constructor(private val imageDao: ImageDao) {

    suspend fun searchImage() = ApiFactory.api.searchImageAsync(q = "yellow+flowers").await()

    fun loadImage() = imageDao.getAll()

    fun insertImage(list: List<Hit>) = imageDao.insert(list)

    companion object {
        @Volatile
        private var instance: HomeRepository? = null
        fun getInstance(imageDao: ImageDao) = instance ?: synchronized(this) {
            instance ?: HomeRepository(imageDao).also { instance = it }
        }
    }
}