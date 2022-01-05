package com.cartrack.omdapi.data.remote

import com.cartrack.omdapi.data.entities.MediaContent
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface MediaApiService {
    @GET("/")
    suspend fun searchMedia(@QueryMap(encoded = true) options: Map<String, String>): Response<List<MediaContent>>

    @GET("/")
    suspend fun fetchMediaDetail(@QueryMap(encoded = true) options: Map<String, String>): Response<MediaContent>
}