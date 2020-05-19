package com.example.picabee.api

import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {
    private const val URL = "https://pixabay.com/"
    val api: PixabayService by lazy {
        val logger = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.d("ApiFactory", message)
            }
        })
        logger.level = HttpLoggingInterceptor.Level.BASIC

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
        val retrofit = Retrofit
            .Builder()
            .baseUrl(URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
        retrofit.create(PixabayService::class.java)
    }
}