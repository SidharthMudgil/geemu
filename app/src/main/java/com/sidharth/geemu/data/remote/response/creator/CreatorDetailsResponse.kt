package com.sidharth.geemu.data.remote.response.creator

import com.google.gson.annotations.SerializedName

data class CreatorDetailsResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("slug") val slug: String,
    @SerializedName("image") val image: String?,
    @SerializedName("image_background") val imageBackground: String?,
    @SerializedName("description") val description: String,
    @SerializedName("games_count") val gamesCount: Int,
    @SerializedName("reviews_count") val reviewsCount: Int,
    @SerializedName("rating") val rating: String,
    @SerializedName("rating_top") val ratingTop: Int,
    @SerializedName("updated") val lastUpdated: String,
    @SerializedName("positions") val positions: List<Position>,
    @SerializedName("platforms") val platforms: Platforms,
    @SerializedName("ratings") val ratings: List<Rating>,
    @SerializedName("timeline") val timeline: List<Timeline>,
)

data class Position(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("slug") val slug: String,
)

data class Platforms(
    @SerializedName("total") val total: Int,
    @SerializedName("results") val results: List<Result>,
    @SerializedName("count") val count: Int,
)

data class Result(
    @SerializedName("count") val count: Int,
    @SerializedName("percent") val percent: Double,
    @SerializedName("platform") val platform: Platform,
)

data class Platform(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("slug") val slug: String,
)

data class Rating(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("count") val count: Int,
    @SerializedName("percent") val percentage: Double,
)

data class Timeline(
    @SerializedName("year") val year: Int,
    @SerializedName("count") val count: Int,
)