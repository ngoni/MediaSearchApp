package com.cartrack.omdapi.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "SearchContent")
data class SearchContent(

    @SerializedName("Search")
    val search: List<Search>,

    val totalResults: String,

    @SerializedName("Response")
    val response: String
)

data class Search(

    @PrimaryKey
    val imdbID: String,

    @SerializedName("Title")
    val title: String,

    @SerializedName("Year")
    val year: String,

    @SerializedName("Type")
    val type: Type,

    @SerializedName("Poster")
    val poster: String
)

enum class Type(val value: String) {
    Game("game"),
    Movie("movie"),
    Series("series");

    companion object {
        fun fromValue(value: String): Type = when (value) {
            "game" -> Game
            "movie" -> Movie
            "series" -> Series
            else -> throw IllegalArgumentException()
        }
    }
}
