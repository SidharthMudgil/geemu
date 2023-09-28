package com.sidharth.geemu.domain.repository

import com.sidharth.geemu.domain.model.Collection
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.model.Tag
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {

    suspend fun getGamesCollections(): Flow<List<Collection>>

    suspend fun addGameToCollection(game: Game, collection: Int)

    suspend fun moveGameToCollection(game: Game, collection: Int)

    suspend fun removeGameFromCollections(game: Game)

    suspend fun getTags(): Flow<List<Tag>>

    suspend fun followTag(tag: Tag)

    suspend fun unfollowTag(tag: Tag)
}