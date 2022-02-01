package com.scribblex.omdapi.data.repository

import com.scribblex.omdapi.data.local.MediaDao
import com.scribblex.omdapi.data.remote.MediaRemoteDataSource
import com.scribblex.omdapi.utils.performGetOperation
import javax.inject.Inject

class MediaRepository @Inject constructor(
    private val remoteDataSource: MediaRemoteDataSource,
    private val localDataSource: MediaDao
) {

    fun getPreviousSearchResults() = performGetOperation(
        databaseQuery = { localDataSource.getAllMedia() }
    )

    fun searchMedia(options: Map<String, String>) = performGetOperation(
        databaseQuery = { localDataSource.getAllMedia() },
        networkCall = { remoteDataSource.searchMedia(options) },
        saveCallResult = {
            localDataSource.deleteAllSearchContent()
            localDataSource.deleteAllMediaContent()
            localDataSource.insertAll(it.search)
        }
    )

    fun getMediaDetail(imdbId: String, options: Map<String, String>) = performGetOperation(
        databaseQuery = { localDataSource.getMedia(imdbId) },
        networkCall = { remoteDataSource.fetchMediaDetail(options) },
        saveCallResult = { localDataSource.insert(it) }
    )
}