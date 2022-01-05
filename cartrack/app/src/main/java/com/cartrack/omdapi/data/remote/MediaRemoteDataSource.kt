package com.cartrack.omdapi.data.remote

import javax.inject.Inject

class MediaRemoteDataSource @Inject constructor(
    private val mediaApiService: MediaApiService
) : BaseDataSource() {
    suspend fun searchMedia(options: Map<String, String>) =
        getResult { mediaApiService.searchMedia(options) }

    suspend fun fetchMediaDetail(options: Map<String, String>) =
        getResult { mediaApiService.fetchMediaDetail(options) }
}