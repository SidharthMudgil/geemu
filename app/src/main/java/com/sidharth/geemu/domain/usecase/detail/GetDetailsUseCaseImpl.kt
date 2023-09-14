package com.sidharth.geemu.domain.usecase.detail

import com.sidharth.geemu.domain.model.CreatorDetails
import com.sidharth.geemu.domain.model.GameDetails
import com.sidharth.geemu.domain.repository.GameRepository
import javax.inject.Inject

class GetDetailsUseCaseImpl @Inject constructor(
    private val gameRepository: GameRepository
): GetDetailsUseCase {
    override suspend fun getGameDetails(id: Int): GameDetails {
        return gameRepository.getGameDetails(id)
    }

    override suspend fun getCreatorDetails(id: Int): CreatorDetails {
        return gameRepository.getCreatorDetails(id)
    }
}