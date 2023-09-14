package com.sidharth.geemu.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sidharth.geemu.domain.model.Genre

class GenreTypeConverter {
    val gson = Gson()

    @TypeConverter
    fun genresToJsonString(genres: List<Genre>): String {
        return gson.toJson(genres)
    }

    @TypeConverter
    fun jsonStringToGenres(json: String): List<Genre> {
        return gson.fromJson(json, object : TypeToken<List<Genre>>() {}.type)
    }
}