package com.sidharth.geemu.domain.usecase.detail

import com.sidharth.geemu.domain.model.GameDetails

interface GetGameDetailsUseCase {

    suspend fun getGameDetails(id: Int): GameDetails
}