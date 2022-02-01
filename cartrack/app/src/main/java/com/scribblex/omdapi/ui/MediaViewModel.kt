package com.scribblex.omdapi.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.scribblex.omdapi.data.entities.SearchContent
import com.scribblex.omdapi.data.repository.MediaRepository
import com.scribblex.omdapi.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(
    private val repository: MediaRepository
) :
    ViewModel() {

    private val mediaList = MutableLiveData<Resource<List<SearchContent>>>()
    private lateinit var internalMediaList: LiveData<Resource<List<SearchContent>>>
    private lateinit var internalSearchResults: LiveData<Resource<List<SearchContent>>>

    private val internalResultsObserver = Observer<Resource<List<SearchContent>>> {
        mediaList.value = it
    }
    private val mediaListObserver = Observer<Resource<List<SearchContent>>> {
        mediaList.value = it
    }

    override fun onCleared() {
        super.onCleared()
        internalMediaList.removeObserver(mediaListObserver)
        internalSearchResults.removeObserver(internalResultsObserver)
    }

    fun getPreviousSearchResults() {
        internalSearchResults =
            repository.getPreviousSearchResults().apply { observeForever(internalResultsObserver) }
    }

    fun searchMedia(options: Map<String, String>) {
        internalMediaList =
            repository.searchMedia(options).apply { observeForever(mediaListObserver) }
    }

    fun getMediaList(): LiveData<Resource<List<SearchContent>>> {
        return mediaList
    }

}