package com.sidharth.geemu.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    version = 1,
    entities = [GameEntity::class, TagEntity::class],
)
@TypeConverters(GenreTypeConverter::class)
abstract class AppDatabase(): RoomDatabase() {
    abstract fun getUserDataDao(): UserDataDao
}