package com.sidharth.geemu.data.repository

import com.sidharth.geemu.data.local.LocalDataSource
import com.sidharth.geemu.data.mapper.EntityMapper
import com.sidharth.geemu.data.mapper.EntityMapper.toGameEntity
import com.sidharth.geemu.data.mapper.EntityMapper.toTag
import com.sidharth.geemu.data.mapper.EntityMapper.toTagEntity
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.model.Tag
import com.sidharth.geemu.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : UserDataRepository {
    override suspend fun getGamesCollections() = flow {
        emit(EntityMapper.toGameCollections(localDataSource.getGames()))
    }

    override suspend fun addGameToCollection(game: Game, collection: Int) {
        localDataSource.addGameToCollection(
            game = game.toGameEntity(collection),
        )
    }

    override suspend fun moveGameToCollection(game: Game, collection: Int) {
        localDataSource.moveGameToCollection(
            id = game.id,
            collection = collection,
        )
    }

    override suspend fun removeGameFromCollections(game: Game) {
        localDataSource.removeGameFromCollection(game.id)
    }

    override suspend fun getTags() = flow {
        emit(localDataSource.getTags().map { it.toTag() })
    }

    override suspend fun followTag(tag: Tag) {
        localDataSource.followTag(tag.toTagEntity())
    }

    override suspend fun unfollowTag(tag: Tag) {
        localDataSource.unfollowTag(tag.toTagEntity())
    }
}