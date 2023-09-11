package com.sidharth.geemu.domain.usecase.home

interface HomeUseCase {
    suspend fun getGames()
    suspend fun getGenres()
}