package com.sidharth.geemu.domain.usecase.tag

import com.sidharth.geemu.domain.model.Tag

interface TagUseCase {

    suspend fun getTags(): List<Tag>

    suspend fun followTag(tag: Tag)

    suspend fun unfollowTag(tag: Tag)
}