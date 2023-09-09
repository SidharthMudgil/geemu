package com.sidharth.geemu.data.remote.response

import com.google.gson.annotations.SerializedName

class PlatformDetailsResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("slug") val slug: String,
    @SerializedName("games_count") val gamesCount: Int,
    @SerializedName("image_background") val background: String,
    @SerializedName("description") val description: String,
    @SerializedName("image") val image: String?,
    @SerializedName("year_start") val start: Int?,
    @SerializedName("year_end") val end: Int?,
)