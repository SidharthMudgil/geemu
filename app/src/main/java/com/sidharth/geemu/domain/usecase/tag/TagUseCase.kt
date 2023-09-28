package com.sidharth.geemu.domain.usecase.tag

import com.sidharth.geemu.domain.model.Tag
import kotlinx.coroutines.flow.Flow

interface TagUseCase {

    suspend fun getTags(): Flow<List<Tag>>

    suspend fun followTag(tag: Tag)

    suspend fun unfollowTag(tag: Tag)
}