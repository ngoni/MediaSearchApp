package com.scribblex.omdapi.data.remote

import com.scribblex.omdapi.data.entities.MediaContent
import com.scribblex.omdapi.data.entities.SearchResults
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface MediaApiService {
    @GET("/")
    suspend fun searchMedia(@QueryMap(encoded = true) options: Map<String, String>): Response<SearchResults>

    @GET("/")
    suspend fun fetchMediaDetail(@QueryMap(encoded = true) options: Map<String, String>): Response<MediaContent>
}