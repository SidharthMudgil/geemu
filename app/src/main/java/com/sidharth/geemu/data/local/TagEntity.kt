package com.sidharth.geemu.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tags")
data class TagEntity(
    @PrimaryKey
    @ColumnInfo("id") val id: Int,
    @ColumnInfo("name") val name: String,
)