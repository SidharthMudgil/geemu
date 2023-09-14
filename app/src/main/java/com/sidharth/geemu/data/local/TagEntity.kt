package com.sidharth.geemu.data.local

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class TagEntity(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
)