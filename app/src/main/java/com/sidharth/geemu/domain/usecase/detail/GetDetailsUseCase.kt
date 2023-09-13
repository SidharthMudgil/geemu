package com.sidharth.geemu.domain.usecase.detail

interface GetDetailsUseCase {
    suspend fun getGameDetails(id: Int)

    suspend fun getCreatorDetails(id: Int)

    suspend fun getDeveloperDetails(id: Int)

    suspend fun getPublisherDetails(id: Int)
}