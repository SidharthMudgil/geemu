package com.sidharth.geemu.domain.repository

import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.model.Tag

interface UserDataRepository {

    suspend fun addGameToCollection(game: Game, collection: Int)

    suspend fun moveGameToCollection(game: Game, collection: Int)

    suspend fun removeGameFromCollections(game: Game)

    suspend fun getTags()

    suspend fun followTag(tag: Tag)

    suspend fun unfollowTag(tag: Tag)
}