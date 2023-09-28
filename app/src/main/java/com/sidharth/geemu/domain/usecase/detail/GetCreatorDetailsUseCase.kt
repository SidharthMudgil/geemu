package com.sidharth.geemu.domain.usecase.detail

import com.sidharth.geemu.domain.model.CreatorDetails
import kotlinx.coroutines.flow.Flow

interface GetCreatorDetailsUseCase {

    suspend fun getCreatorDetails(id: Int): Flow<CreatorDetails>
}