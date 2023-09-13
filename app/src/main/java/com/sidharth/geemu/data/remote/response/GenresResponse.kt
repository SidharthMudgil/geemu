package com.sidharth.geemu.data.remote.response

import com.google.gson.annotations.SerializedName

data class GenresResponse (
    @SerializedName("count") val count: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("results") val results: List<Genre>,
)

data class Genre(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("slug") val slug: String,
    @SerializedName("games_count") val gamesCount: Int,
    @SerializedName("image_background") val imageBackground: String,
    @SerializedName("games") val games: List<Game>,
)

data class Game(
    @SerializedName("id") val id: Int,
    @SerializedName("slug") val slug: String,
    @SerializedName("name") val name: String,
    @SerializedName("added") val added: Int,
)