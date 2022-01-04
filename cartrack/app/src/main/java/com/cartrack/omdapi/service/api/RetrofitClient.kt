package com.cartrack.omdapi.service.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL: String = "https://www.omdbapi.com"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

object RetrofitClient {
    const val API_KEY: String = "56b4edcb"
    val service: OmdApiService by lazy {
        retrofit.create(OmdApiService::class.java)
    }
}