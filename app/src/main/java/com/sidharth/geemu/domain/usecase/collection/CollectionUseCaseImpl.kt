package com.sidharth.geemu.domain.usecase.collection

import com.sidharth.geemu.domain.model.Collection
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.repository.UserDataRepository

class CollectionUseCaseImpl(
    private val userDataRepository: UserDataRepository
) : CollectionUseCase{
    override suspend fun getGameCollections(): List<Collection> {
        return userDataRepository.getGamesCollections()
    }

    override suspend fun addGameToCollection(game: Game, collection: Int) {
        userDataRepository.addGameToCollection(
            game = game,
            collection = collection
        )
    }

    override suspend fun moveGameToCollection(game: Game, collection: Int) {
        userDataRepository.moveGameToCollection(
            game = game,
            collection = collection
        )
    }

    override suspend fun removeGameFromCollections(game: Game) {
        userDataRepository.removeGameFromCollections(game)
    }
}