package com.cartrack.omdapi.service.api

import com.cartrack.omdapi.service.api.models.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface OmdApiService {
    @GET("/")
    fun search(@QueryMap(encoded = true) options: Map<String, String>): Call<List<SearchResponse>>
}