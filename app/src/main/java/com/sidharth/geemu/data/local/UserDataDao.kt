package com.sidharth.geemu.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface UserDataDao {

    @Query("select * from games")
    suspend fun getGames(): List<GameEntity>

    @Upsert(GameEntity::class)
    suspend fun addGameToCollection(gameEntity: GameEntity)

    @Delete(GameEntity::class)
    suspend fun removeGameFromToCollection(id: Int)

    @Query("update games set collection = :collection where id == :id")
    suspend fun moveGameToCollection(id: Int, collection: Int)

    @Query("select * from tags")
    suspend fun getTags(): List<TagEntity>

    @Upsert(TagEntity::class)
    suspend fun followTag(tag: TagEntity)

    @Delete(TagEntity::class)
    suspend fun unfollowTag(id: Int)
}