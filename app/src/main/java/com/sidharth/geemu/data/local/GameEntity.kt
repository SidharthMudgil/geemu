package com.sidharth.geemu.data.local

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.sidharth.geemu.domain.model.Genre

@Entity
data class GameEntity(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String,
    @SerializedName("genres") val genres: List<Genre>,
    @SerializedName("release") val release: String,
    @SerializedName("rating") val rating: String,
    @SerializedName("collection") val collection: Int,
)