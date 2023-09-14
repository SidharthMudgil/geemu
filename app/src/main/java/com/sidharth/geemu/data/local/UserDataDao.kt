package com.sidharth.geemu.data.local

import androidx.room.Dao
import com.sidharth.geemu.domain.model.Tag

@Dao
interface UserDataDao {

    suspend fun getGames(): List<GameEntity>

    suspend fun addGameToCollection(gameEntity: GameEntity)

    suspend fun removeGameFromToCollection(id: Int)

    suspend fun moveGameToCollection(id: Int, collection: Int)

    suspend fun getTags(): List<TagEntity>

    suspend fun followTag(tag: TagEntity)

    suspend fun unfollowTag(id: Int)
}