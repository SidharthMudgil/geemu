package com.sidharth.geemu.domain.usecase.collection

import com.sidharth.geemu.domain.model.Collection
import com.sidharth.geemu.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface CollectionUseCase {

    suspend fun getGameCollections(): Flow<List<Collection>>

    suspend fun addGameToCollection(game: Game, collection: Int)

    suspend fun moveGameToCollection(game: Game, collection: Int)

    suspend fun removeGameFromCollections(game: Game)
}