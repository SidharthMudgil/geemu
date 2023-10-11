package com.sidharth.geemu.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey
    @ColumnInfo("id") val id: Int,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("image") val image: String,
    @ColumnInfo("genres") val genres: String,
    @ColumnInfo("release") val release: String,
    @ColumnInfo("rating") val rating: Double,
    @ColumnInfo("collection") val collection: Int,
)