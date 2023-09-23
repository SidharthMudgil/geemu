package com.sidharth.geemu.data.remote.response.game.team

import com.google.gson.annotations.SerializedName
import com.sidharth.geemu.data.remote.response.game.Game

data class GameDevelopmentTeamResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("results") val results: List<Creator>,
)

data class Creator(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("slug") val slug: String,
    @SerializedName("image") val image: String?,
    @SerializedName("image_background") val imageBackground: String?,
    @SerializedName("games_count") val gamesCount: Int,
    @SerializedName("games") val games: List<Game>,
    @SerializedName("positions") val positions: List<Position>,
)

data class Position(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("slug") val slug: String,
)
