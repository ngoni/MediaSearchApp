package com.scribblex.omdapi.data.local

import androidx.room.TypeConverter
import com.scribblex.omdapi.data.entities.Rating
import com.scribblex.omdapi.data.entities.Type
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun toRating(string: String): List<Rating> {
        val type = object : TypeToken<List<Rating>>() {}.type
        return Gson().fromJson(string, type)
    }

    @TypeConverter
    fun fromRating(ratings: List<Rating>): String = Gson().toJson(ratings)

    @TypeConverter
    fun toType(string: String?): Type? {
        val type = object : TypeToken<Type>() {}.type
        return string?.let { Gson().fromJson(string, type) }
    }

    @TypeConverter
    fun fromType(type: Type?): String? = type?.value

}