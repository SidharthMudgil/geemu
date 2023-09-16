package com.sidharth.geemu.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [GameEntity::class, TagEntity::class],
)
abstract class AppDatabase(): RoomDatabase() {
    abstract fun getUserDataDao(): UserDataDao
}