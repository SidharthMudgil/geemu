package com.sidharth.geemu.domain.usecase.collection

interface CollectionUseCase {

    suspend fun addGameToCollection(id: Int, collection: Int)

    suspend fun moveGameToCollection(id: Int, collection: Int)

    suspend fun removeGameFromCollections(id: Int)
}