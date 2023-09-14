package com.sidharth.geemu.domain.usecase.tag

import com.sidharth.geemu.domain.model.Tag
import com.sidharth.geemu.domain.repository.UserDataRepository

class TagUseCaseImpl(
    private val userDataRepository: UserDataRepository
) : TagUseCase{

    override suspend fun getTags(): List<Tag> {
        return userDataRepository.getTags()
    }

    override suspend fun followTag(tag: Tag) {
        userDataRepository.followTag(tag)
    }

    override suspend fun unfollowTag(tag: Tag) {
        userDataRepository.unfollowTag(tag)
    }
}