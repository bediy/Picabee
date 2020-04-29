package com.example.picabee

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.picabee.adapter.HomeAdapter
import com.example.picabee.utils.InjectorUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val homeViewModel: HomeViewModel by viewModels {
        InjectorUtils.provideHomeViewModelFactory(this)
    }
    private lateinit var adapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = HomeAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

//        homeViewModel.searchImage()

        homeViewModel.liveData.observe(this, Observer {
//            adapter.submitList(it.hits)
        })

        homeViewModel.imageLiveData.observe(this, Observer {
            Log.i("MainActivity", "room list size: ${it.size}")
            adapter.submitList(it)
        })
    }
}