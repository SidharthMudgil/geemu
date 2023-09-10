package com.sidharth.geemu.data.remote.response

import com.google.gson.annotations.SerializedName

data class GameAchievementsResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("results") val results: List<Achievement>,
)

data class Achievement(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("image") val image: String,
    @SerializedName("percent") val percent: String,
)