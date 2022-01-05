package com.cartrack.omdapi.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.cartrack.omdapi.Constants
import com.cartrack.omdapi.R
import com.cartrack.omdapi.service.api.models.MediaContent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MediaViewModel by lazy {
        ViewModelProvider(this)[MediaViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.results.observe(this, this::handleResults)

        val map: MutableMap<String, String> = mutableMapOf()
        map["s"] = "fire"
        map["type"] = "movie, series"
        map["apikey"] = Constants.API_KEY
        viewModel.search(map)
    }

    private fun handleResults(results: List<MediaContent>) {
        Toast.makeText(this, "results.size", Toast.LENGTH_LONG).show()
    }
}