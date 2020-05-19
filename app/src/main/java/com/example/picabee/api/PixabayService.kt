package com.example.picabee.api

import com.example.picabee.entity.ImageEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayService {
    companion object {
        private const val KEY = "16074557-86109882f5b5d32b17a2ee8e4"
    }

    @GET("api/")
    fun searchImageAsync(
        @Query("key") key: String = KEY,
        @Query("q") q: String,
        @Query("image_type") type: String = "photo",
        @Query("page") page: Int = 1
    ): Call<ImageEntity>
}

