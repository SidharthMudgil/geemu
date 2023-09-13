package com.sidharth.geemu.domain.usecase.detail

import com.sidharth.geemu.domain.repository.GameRepository

class GetDetailsUseCaseImpl(
    private val gameRepository: GameRepository
): GetDetailsUseCase {
    override suspend fun getGameDetails(id: Int) {
        gameRepository.getGameDetails(id)
    }

    override suspend fun getCreatorDetails(id: Int) {
        gameRepository.getCreatorDetails(id)
    }

    override suspend fun getDeveloperDetails(id: Int) {
        gameRepository.getDeveloperDetails(id)
    }

    override suspend fun getPublisherDetails(id: Int) {
        gameRepository.getPublisherDetails(id)
    }
}