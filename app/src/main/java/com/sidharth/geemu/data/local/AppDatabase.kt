package com.sidharth.geemu.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 3,
    entities = [GameEntity::class, TagEntity::class],
    exportSchema = false,
)
abstract class AppDatabase(): RoomDatabase() {
    abstract fun getUserDataDao(): UserDataDao
}