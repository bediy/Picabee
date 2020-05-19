package com.example.picabee.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.example.picabee.api.ApiFactory
import com.example.picabee.db.ImageBoundaryCallback
import com.example.picabee.db.ImageDao
import com.example.picabee.entity.Hit
import com.example.picabee.entity.ImageEntity
import com.example.picabee.entity.NetworkState
import com.example.picabee.utils.SpUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRepository private constructor(private val imageDao: ImageDao) {

    fun loadImage(scope: CoroutineScope): LiveData<PagedList<Hit>> {
        val networkLiveData = MutableLiveData<NetworkState>()
        val callback = ImageBoundaryCallback(
            networkPageSize = NETWORK_PAGE_SIZE,
            imageDao = imageDao,
            scope = scope,
            networkLiveData = networkLiveData
        )
        return imageDao.postsAll().toLiveData(
            pageSize = NETWORK_PAGE_SIZE,
            boundaryCallback = callback
        )
    }

    fun refresh(key: String, scope: CoroutineScope): LiveData<NetworkState> {
        SpUtils.savedKey = key
        val refreshLiveData = MutableLiveData<NetworkState>()
        refreshLiveData.value = NetworkState.Loading
        ApiFactory.api.searchImageAsync(q = key)
            .enqueue(refreshImageCallback(scope, refreshLiveData))
        return refreshLiveData
    }

    private fun refreshImageCallback(
        scope: CoroutineScope,
        refreshLiveData: MutableLiveData<NetworkState>
    ) = object : Callback<ImageEntity> {

        override fun onFailure(call: Call<ImageEntity>, t: Throwable) {
            Log.e("paging", "Exception:${t}")
            refreshLiveData.value = NetworkState.Failed
        }

        override fun onResponse(call: Call<ImageEntity>, response: Response<ImageEntity>) {
            if (response.isSuccessful) {
                val hits = response.body()?.hits ?: emptyList()
                if (hits.isEmpty()) {
                    refreshLiveData.value = NetworkState.NoResult
                } else {
                    scope.launch(Dispatchers.IO) {
                        imageDao.deleteAll()
                        imageDao.insert(hits)
                        refreshLiveData.postValue(NetworkState.Success)
                    }
                }
            } else {
                refreshLiveData.value = NetworkState.Failed
            }
        }
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 20

        @Volatile
        private var instance: HomeRepository? = null
        fun getInstance(imageDao: ImageDao) =
            instance ?: synchronized(this) {
                instance ?: HomeRepository(imageDao).also { instance = it }
            }
    }
}