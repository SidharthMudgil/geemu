package com.sidharth.geemu.domain.usecase.tag

import com.sidharth.geemu.domain.repository.UserDataRepository

class TagUseCaseImpl(
    private val userDataRepository: UserDataRepository
) : TagUseCase{

    override suspend fun getTags() {
        userDataRepository.getTags()
    }

    override suspend fun followTag(id: Int) {
        userDataRepository.followTag(id)
    }

    override suspend fun unfollowTag(id: Int) {
        userDataRepository.unfollowTag(id)
    }
}