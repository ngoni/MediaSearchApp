package com.cartrack.omdapi.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cartrack.omdapi.service.api.models.MediaContent

class MediaViewModel : ViewModel() {



    private var _results = MutableLiveData<List<MediaContent>>()
    val results: LiveData<List<MediaContent>>
        get() = _results

    init {
        _results.value = listOf()
    }

    fun search(options: Map<String, String>) {
    }
}