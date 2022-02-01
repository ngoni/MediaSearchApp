package com.scribblex.omdapi.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "SearchContent")
data class SearchContent(
    @PrimaryKey
    val imdbID: String,

    @SerializedName("Title")
    val title: String,

    @SerializedName("Year")
    val year: String? = null,

    @SerializedName("Type")
    val type: Type? = null,

    @SerializedName("Poster")
    val poster: String? = null
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

