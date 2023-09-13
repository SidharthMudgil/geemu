package com.sidharth.geemu.domain.usecase.detail

import com.sidharth.geemu.domain.model.CreatorDetails
import com.sidharth.geemu.domain.model.GameDetails

interface GetDetailsUseCase {
    suspend fun getGameDetails(id: Int): GameDetails

    suspend fun getCreatorDetails(id: Int): CreatorDetails
}