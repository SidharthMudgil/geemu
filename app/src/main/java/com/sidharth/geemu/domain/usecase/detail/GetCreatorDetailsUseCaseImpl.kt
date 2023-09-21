package com.sidharth.geemu.domain.usecase.detail

import com.sidharth.geemu.domain.model.CreatorDetails
import com.sidharth.geemu.domain.repository.GameRepository
import javax.inject.Inject

class GetCreatorDetailsUseCaseImpl @Inject constructor(
    private val gameRepository: GameRepository
) : GetCreatorDetailsUseCase {

    override suspend fun getCreatorDetails(id: Int): CreatorDetails {
        return gameRepository.getCreatorDetails(id)
    }
}