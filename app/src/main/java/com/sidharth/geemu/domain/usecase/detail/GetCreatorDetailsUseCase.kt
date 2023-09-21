package com.sidharth.geemu.domain.usecase.detail

import com.sidharth.geemu.domain.model.CreatorDetails

interface GetCreatorDetailsUseCase {

    suspend fun getCreatorDetails(id: Int): CreatorDetails
}