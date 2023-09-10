package com.sidharth.geemu.data.remote.response

import com.google.gson.annotations.SerializedName

data class GameScreenshotsResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("results") val results: List<Screenshot>,
)

data class Screenshot(
    @SerializedName("id") val id: Int,
    @SerializedName("image") val image: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("is_deleted") val isDeleted: Boolean,
)