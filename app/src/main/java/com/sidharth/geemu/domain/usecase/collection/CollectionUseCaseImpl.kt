package com.sidharth.geemu.domain.usecase.collection

import com.sidharth.geemu.domain.repository.UserDataRepository

class CollectionUseCaseImpl(
    private val userDataRepository: UserDataRepository
) : CollectionUseCase{

    override suspend fun addGameToCollection(id: Int, collection: Int) {
        userDataRepository.addGameToCollection(
            id = id,
            collection = collection
        )
    }

    override suspend fun moveGameToCollection(id: Int, collection: Int) {
        userDataRepository.moveGameToCollection(
            id = id,
            collection = collection
        )
    }

    override suspend fun removeGameFromCollections(id: Int) {
        userDataRepository.removeGameFromCollections(id)
    }
}