package com.example.picabee.db

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.example.picabee.api.ApiFactory
import com.example.picabee.entity.Hit
import com.example.picabee.entity.ImageEntity
import com.example.picabee.entity.NetworkState
import com.example.picabee.utils.SpUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImageBoundaryCallback(
    private val networkPageSize: Int,
    private val imageDao: ImageDao,
    private val scope: CoroutineScope,
    private val networkLiveData: MutableLiveData<NetworkState>
) : PagedList.BoundaryCallback<Hit>() {

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        Log.i("paging", "room list onZeroItemsLoaded.")
        networkLiveData.value = NetworkState.Loading
        ApiFactory.api.searchImageAsync(q = SpUtils.savedKey).enqueue(fetchImageCallback(true))
    }

    override fun onItemAtEndLoaded(itemAtEnd: Hit) {
        super.onItemAtEndLoaded(itemAtEnd)
        scope.launch {
            val count = withContext(Dispatchers.IO) { imageDao.count() }
            ApiFactory.api
                .searchImageAsync(q = SpUtils.savedKey, page = count / networkPageSize + 1)
                .enqueue(fetchImageCallback(false))
        }
    }

    private fun fetchImageCallback(isInitialLoaded: Boolean) = object : Callback<ImageEntity> {
        override fun onFailure(call: Call<ImageEntity>, t: Throwable) {
            Log.e("paging", "Exception:${t}")
            networkLiveData.value = NetworkState.Failed
        }

        override fun onResponse(call: Call<ImageEntity>, response: Response<ImageEntity>) {
            if (response.isSuccessful) {
                val hits = response.body()?.hits ?: emptyList()
                if (isInitialLoaded) {
                    if (hits.isEmpty()) {
                        networkLiveData.value = NetworkState.NoResult
                    } else {
                        insert(hits)
                        networkLiveData.value = NetworkState.Success
                    }
                } else {
                    if (hits.isEmpty() || hits.size < networkPageSize) {
                        networkLiveData.value = NetworkState.NoData
                    }
                    insert(hits)
                }
            } else {
                networkLiveData.value = NetworkState.Failed
            }
        }

        private fun insert(hits: List<Hit>) {
            scope.launch(Dispatchers.IO) {
                imageDao.insert(hits)
            }
        }
    }
}