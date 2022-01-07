package com.cartrack.omdapi.data.repository

import com.cartrack.omdapi.data.local.MediaDao
import com.cartrack.omdapi.data.remote.MediaRemoteDataSource
import com.cartrack.omdapi.utils.performGetOperation
import javax.inject.Inject

class MediaRepository @Inject constructor(
    private val remoteDataSource: MediaRemoteDataSource,
    private val localDataSource: MediaDao
) {

    fun searchMedia(options: Map<String, String>) = performGetOperation(
        databaseQuery = { localDataSource.getAllMedia() },
        networkCall = { remoteDataSource.searchMedia(options) },
        saveCallResult = { localDataSource.insertAll(it.search) }
    )

    fun getMediaDetail(imdbId: String, options: Map<String, String>) = performGetOperation(
        databaseQuery = { localDataSource.getMedia(imdbId) },
        networkCall = { remoteDataSource.fetchMediaDetail(options) },
        saveCallResult = { localDataSource.insert(it) }
    )
}