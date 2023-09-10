package com.sidharth.geemu.data.remote.response.game.movies

import com.google.gson.annotations.SerializedName

data class GameMoviesResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("results") val results: List<Movie>,
)

data class Movie(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("preview") val preview: String,
    @SerializedName("data") val data: MovieData,
)

data class MovieData(
    @SerializedName("480") val low: String,
    @SerializedName("max") val max: String,
)