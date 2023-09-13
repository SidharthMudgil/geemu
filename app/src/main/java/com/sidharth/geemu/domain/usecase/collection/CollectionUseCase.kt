package com.sidharth.geemu.domain.usecase.collection

import com.sidharth.geemu.domain.model.Game

interface CollectionUseCase {

    suspend fun addGameToCollection(game: Game, collection: Int)

    suspend fun moveGameToCollection(game: Game, collection: Int)

    suspend fun removeGameFromCollections(game: Game)
}