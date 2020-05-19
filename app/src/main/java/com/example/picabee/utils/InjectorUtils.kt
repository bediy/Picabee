package com.example.picabee.utils

import android.content.Context
import com.example.picabee.repository.HomeRepository
import com.example.picabee.db.AppDatabase
import com.example.picabee.viewmodel.HomeViewModelFactory

object InjectorUtils {
    private fun getHomeRepository(context: Context): HomeRepository {
        return HomeRepository.getInstance(AppDatabase.getInstance(context).imageDao())
    }

    fun provideHomeViewModelFactory(context: Context) =
        HomeViewModelFactory(getHomeRepository(context))
}