package com.cartrack.omdapi.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.cartrack.omdapi.data.entities.MediaContent
import com.cartrack.omdapi.data.entities.SearchContent
import com.cartrack.omdapi.data.repository.MediaRepository
import com.cartrack.omdapi.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(private val repository: MediaRepository) :
    ViewModel() {

    var mediaList: LiveData<Resource<List<SearchContent>>>? = null
    var media: LiveData<Resource<MediaContent>>? = null

    fun searchMedia(options: Map<String, String>) {
        mediaList = repository.searchMedia(options)
    }

    fun getMediaDetail(imdbId: String, options: Map<String, String>) {
        media = repository.getMediaDetail(imdbId, options)
    }
}