package com.cartrack.omdapi.data.entities

import com.google.gson.annotations.SerializedName

data class SearchResults(

    @SerializedName("Search")
    val search: List<SearchContent>,

    val totalResults: String,

    @SerializedName("Response")
    val response: String
)
