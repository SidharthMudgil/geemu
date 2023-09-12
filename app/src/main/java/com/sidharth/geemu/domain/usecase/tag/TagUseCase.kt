package com.sidharth.geemu.domain.usecase.tag

interface TagUseCase {

    suspend fun getTags()

    suspend fun followTag(id: Int)

    suspend fun unfollowTag(id: Int)
}