package com.sidharth.geemu.data.remote.response.game.games

import com.google.gson.annotations.SerializedName
import com.sidharth.geemu.data.remote.response.game.Game

data class GamesAdditionsResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("results") val results: List<Game>,
)

