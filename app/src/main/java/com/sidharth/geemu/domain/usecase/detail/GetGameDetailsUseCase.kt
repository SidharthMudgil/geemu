package com.sidharth.geemu.domain.usecase.detail

import com.sidharth.geemu.domain.model.GameDetails
import kotlinx.coroutines.flow.Flow

interface GetGameDetailsUseCase {

    suspend fun getGameDetails(id: Int): Flow<GameDetails>
}