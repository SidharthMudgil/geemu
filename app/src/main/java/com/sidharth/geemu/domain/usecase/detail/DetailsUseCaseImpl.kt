package com.sidharth.geemu.domain.usecase.detail

import com.sidharth.geemu.domain.repository.GameRepository

class DetailsUseCaseImpl(
    private val gameRepository: GameRepository
): DetailsUseCase {
    override suspend fun getCreatorDetails(id: Int) {
        gameRepository.getCreatorDetails(id)
        //todo return games from that also... for all of these
    }

    override suspend fun getDeveloperDetails(id: Int) {
        gameRepository.getDeveloperDetails(id)
    }

    override suspend fun getGenreDetails(id: Int) {
        gameRepository.getGenreDetails(id)
    }

    override suspend fun getPlatformDetails(id: Int) {
        gameRepository.getPlatformDetails(id)
    }

    override suspend fun getPublisherDetails(id: Int) {
        gameRepository.getPublisherDetails(id)
    }

    override suspend fun getStoreDetails(id: Int) {
        gameRepository.getStoreDetails(id)
    }

    override suspend fun getTagDetails(id: Int) {
        gameRepository.getTagDetails(id)
    }
}