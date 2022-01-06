package com.cartrack.omdapi.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cartrack.omdapi.Constants
import com.cartrack.omdapi.R
import com.cartrack.omdapi.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MediaViewModel by lazy {
        ViewModelProvider(this)[MediaViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        searchMedia()
//        fetchMedia()
        observeViewModels()
    }

    private fun searchMedia() {
        val map: MutableMap<String, String> = mutableMapOf()
        map["s"] = "fire"
        map["type"] = "movie, series"
        map["apikey"] = Constants.API_KEY
        viewModel.searchMedia(map)
    }

    private fun fetchMedia() {
        val map: MutableMap<String, String> = mutableMapOf()
        map["type"] = "movie, series"
        map["apikey"] = Constants.API_KEY
        val imdbId = "tt0358670"
        map["i"] = imdbId
        viewModel.fetchMediaDetail(imdbId, map)
    }

    private fun observeViewModels() {
        viewModel.mediaList?.observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    Toast.makeText(this, "${it.data}", Toast.LENGTH_SHORT).show()
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
                }
                Resource.Status.LOADING -> {
                    Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                }
            }
        })

        viewModel.media?.observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    Toast.makeText(this, "${it.data}", Toast.LENGTH_SHORT).show()
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
                }
                Resource.Status.LOADING -> {
                    Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}