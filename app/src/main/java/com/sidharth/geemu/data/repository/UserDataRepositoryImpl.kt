package com.sidharth.geemu.data.repository

import com.sidharth.geemu.domain.repository.UserDataRepository

class UserDataRepositoryImpl : UserDataRepository {

    override suspend fun addGameToCollection(id: Int, collection: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun moveGameToCollection(id: Int, collection: Int) {
    }

    override suspend fun removeGameFromCollections(id: Int) {
        TODO("Not yet implemented")
    }


    override suspend fun getTags() {
        TODO("Not yet implemented")
    }

    override suspend fun followTag(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun unfollowTag(id: Int) {
        TODO("Not yet implemented")
    }
}