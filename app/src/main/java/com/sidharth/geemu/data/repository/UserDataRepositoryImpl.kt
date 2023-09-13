package com.sidharth.geemu.data.repository

import com.sidharth.geemu.data.local.LocalDataSource
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.model.Tag
import com.sidharth.geemu.domain.repository.UserDataRepository

class UserDataRepositoryImpl(
    private val localDataSource: LocalDataSource
) : UserDataRepository {

    override suspend fun addGameToCollection(game: Game, collection: Int) {
        localDataSource.addGameToCollection(
            game = game,
            collection = collection,
        )
    }

    override suspend fun moveGameToCollection(game: Game, collection: Int) {
        localDataSource.moveGameToCollection(
            game = game,
            collection = collection,
        )
    }

    override suspend fun removeGameFromCollections(game: Game) {
        localDataSource.removeGameFromCollection(game)
    }


    override suspend fun getTags() {
        localDataSource.getTags()
    }

    override suspend fun followTag(tag: Tag) {
        localDataSource.followTag(tag)
    }

    override suspend fun unfollowTag(tag: Tag) {
        localDataSource.unfollowTag(tag)
    }
}