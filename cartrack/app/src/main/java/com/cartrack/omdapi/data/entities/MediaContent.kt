package com.cartrack.omdapi.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "MediaContent")
data class MediaContent(

    @PrimaryKey
    val imdbID: String,
    val imdbRating: String,
    val imdbVotes: String,

    @SerializedName("Title")
    val title: String,

    @SerializedName("Year")
    val year: String,

    @SerializedName("Rated")
    val rated: String,

    @SerializedName("Released")
    val released: String,

    @SerializedName("Runtime")
    val runtime: String,

    @SerializedName("Genre")
    val genre: String,

    @SerializedName("Director")
    val director: String,

    @SerializedName("Writer")
    val writer: String,

    @SerializedName("Actors")
    val actors: String,

    @SerializedName("Plot")
    val plot: String,

    @SerializedName("Language")
    val language: String,

    @SerializedName("Country")
    val country: String,

    @SerializedName("Awards")
    val awards: String,

    @SerializedName("Poster")
    val poster: String,

    @SerializedName("Ratings")
    val ratings: List<Rating>,

    @SerializedName("Metascore")
    val metascore: String,

    @SerializedName("Type")
    val type: String,

    @SerializedName("DVD")
    val dvd: String,

    @SerializedName("BoxOffice")
    val boxOffice: String,

    @SerializedName("Production")
    val production: String,

    @SerializedName("Website")
    val website: String,

    @SerializedName("Response")
    val response: String
)

data class Rating(
    @SerializedName("Source")
    val source: String,

    @SerializedName("Value")
    val value: String
)
