package com.cartrack.omdapi.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cartrack.omdapi.service.api.RetrofitClient
import com.cartrack.omdapi.service.api.models.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {

    private var _results = MutableLiveData<List<SearchResponse>>()
    val results: LiveData<List<SearchResponse>>
        get() = _results

    init {
        _results.value = listOf()
    }

    fun search(options: Map<String, String>) {
        val api = RetrofitClient.service.search(options)
        api.enqueue(object : Callback<List<SearchResponse>> {
            override fun onFailure(call: Call<List<SearchResponse>>, t: Throwable) {
                Log.d("TAG_TAG", "Failed :" + t.message)
            }

            override fun onResponse(
                call: Call<List<SearchResponse>>,
                response: Response<List<SearchResponse>>
            ) {
                _results.value = response.body()
            }
        })
    }
}