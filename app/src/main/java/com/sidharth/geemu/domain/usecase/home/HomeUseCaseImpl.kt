package com.sidharth.geemu.domain.usecase.home

import com.sidharth.geemu.domain.repository.GameRepository

class HomeUseCaseImpl(
    private val gameRepository: GameRepository
) : HomeUseCase {

    override suspend fun getGames() {
        gameRepository.getGames()
    }

    override suspend fun getGenres() {
        gameRepository.getGenres()
    }
}