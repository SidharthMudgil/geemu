package com.sidharth.geemu.data.local

import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val userDataDao: UserDataDao
) {
    suspend fun getGames(): List<GameEntity> {
        return userDataDao.getGames()
    }

    suspend fun addGameToCollection(game: GameEntity) {
        userDataDao.addGameToCollection(game)
    }

    suspend fun removeGameFromCollection(id: Int) {
        userDataDao.removeGameFromToCollection(id)
    }

    suspend fun moveGameToCollection(id: Int, collection: Int) {
        userDataDao.moveGameToCollection(id, collection)
    }

    suspend fun getTags(): List<TagEntity> {
        return userDataDao.getTags()
    }

    suspend fun unfollowTag(tag: TagEntity) {
        userDataDao.unfollowTag(tag.id)
    }

    suspend fun followTag(tag: TagEntity) {
        userDataDao.followTag(tag)
    }
}