package com.sidharth.geemu.domain.usecase.detail

interface DetailsUseCase {
    suspend fun getCreatorDetails(id: Int)

    suspend fun getDeveloperDetails(id: Int)

    suspend fun getGenreDetails(id: Int)

    suspend fun getPlatformDetails(id: Int)

    suspend fun getPublisherDetails(id: Int)

    suspend fun getStoreDetails(id: Int)

    suspend fun getTagDetails(id: Int)
}