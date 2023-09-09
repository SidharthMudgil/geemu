package com.sidharth.geemu.data.remote.response

import com.google.gson.annotations.SerializedName

data class PublisherDetailsResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("slug") val slug: String,
    @SerializedName("games_count") val gamesCount: Int,
    @SerializedName("image_background") val background: String,
    @SerializedName("description") val description: String,
)