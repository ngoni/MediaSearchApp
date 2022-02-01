package com.scribblex.omdapi.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.scribblex.omdapi.data.entities.MediaContent
import com.scribblex.omdapi.data.repository.MediaRepository
import com.scribblex.omdapi.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MediaDetailViewModel @Inject constructor(
    private val repository: MediaRepository
) :
    ViewModel() {

    private val media = MutableLiveData<Resource<MediaContent>>()
    private lateinit var internalMedia: LiveData<Resource<MediaContent>>

    private val mediaObserver = Observer<Resource<MediaContent>> {
        media.value = it
    }

    override fun onCleared() {
        super.onCleared()
        internalMedia.removeObserver(mediaObserver)
    }

    fun getMediaDetail(imdbId: String, options: Map<String, String>) {
        internalMedia =
            repository.getMediaDetail(imdbId, options).apply { observeForever(mediaObserver) }
    }

    fun getMediaDetails(): LiveData<Resource<MediaContent>> {
        return media
    }
}