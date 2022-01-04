package com.cartrack.omdapi.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.cartrack.omdapi.R
import com.cartrack.omdapi.service.api.RetrofitClient
import com.cartrack.omdapi.service.api.models.SearchResponse

class MainActivity : AppCompatActivity() {

    private val viewModel: SearchViewModel by lazy {
        ViewModelProvider(this)[SearchViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.results.observe(this, this::handleResults)

        val map: MutableMap<String, String> = mutableMapOf()
        map["s"] = "fire"
        map["type"] = "movie, series"
        map["apikey"] = RetrofitClient.API_KEY
        viewModel.search(map)
    }

    private fun handleResults(results: List<SearchResponse>) {
        Toast.makeText(this, "results.size", Toast.LENGTH_LONG).show()
    }
}