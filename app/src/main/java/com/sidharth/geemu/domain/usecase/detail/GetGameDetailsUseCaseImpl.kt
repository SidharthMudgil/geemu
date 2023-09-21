package com.sidharth.geemu.domain.usecase.detail

import com.sidharth.geemu.domain.model.GameDetails
import com.sidharth.geemu.domain.repository.GameRepository
import javax.inject.Inject

class GetGameDetailsUseCaseImpl @Inject constructor(
    private val gameRepository: GameRepository
) : GetGameDetailsUseCase {

    override suspend fun getGameDetails(id: Int): GameDetails {
        return gameRepository.getGameDetails(id)
    }
}