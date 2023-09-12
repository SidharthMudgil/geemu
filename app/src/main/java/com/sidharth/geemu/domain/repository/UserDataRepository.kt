package com.sidharth.geemu.domain.repository

interface UserDataRepository {

    suspend fun addGameToCollection(id: Int, collection: Int)

    suspend fun moveGameToCollection(id: Int, collection: Int)

    suspend fun removeGameFromCollections(id: Int)

    suspend fun getTags()

    suspend fun followTag(id: Int)

    suspend fun unfollowTag(id: Int)
}