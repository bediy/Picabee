package com.example.picabee.ui

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.picabee.R
import com.example.picabee.adapter.HomeAdapter
import com.example.picabee.entity.NetworkState
import com.example.picabee.utils.InjectorUtils
import com.example.picabee.utils.SpUtils
import com.example.picabee.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    private val homeViewModel: HomeViewModel by viewModels {
        InjectorUtils.provideHomeViewModelFactory(this)
    }

    private lateinit var adapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = HomeAdapter()
        recyclerView.adapter = adapter

        val layoutManager = StaggeredGridLayoutManager(
            2,
            StaggeredGridLayoutManager.VERTICAL
        )
//        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        recyclerView.layoutManager = layoutManager

        homeViewModel.pagedLiveData.observe(this, Observer {
            adapter.submitList(it)
        })

        editText.setText(SpUtils.savedKey)
        editText.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                refresh(v.text.toString())
                return@setOnEditorActionListener true
            }
            false
        }

        swipeRefreshLayout.setOnRefreshListener {
            refresh(editText.text.toString())
        }
    }

    private fun refresh(key: String) {
        homeViewModel.refresh(key).observe(this, Observer {
            when (it) {
                NetworkState.Loading -> swipeRefreshLayout.isRefreshing = true
                NetworkState.Failed -> swipeRefreshLayout.isRefreshing = false
                NetworkState.NoResult -> swipeRefreshLayout.isRefreshing = false
                NetworkState.Success -> swipeRefreshLayout.isRefreshing = false
                else -> swipeRefreshLayout.isRefreshing = false
            }
        })
    }
}