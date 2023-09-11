package com.sidharth.geemu.domain.usecase.game

import com.sidharth.geemu.domain.repository.GameRepository

class GameUseCaseImpl(
    private val gameRepository: GameRepository
): GameUseCase {
    override suspend fun getGames() {
        gameRepository.getGames()
    }

    override suspend fun getGameDetails(id: Int, count: Int?) {
        gameRepository.getGameDetails(id)
    }

    override suspend fun getGameAchievements(id: Int, count: Int?) {
        gameRepository.getGameAchievements(id)
    }

    override suspend fun getGameDLCs(id: Int, count: Int?) {
        gameRepository.getGameDLCs(id)
    }

    override suspend fun getGameScreenshots(id: Int, count: Int?) {
        gameRepository.getGameScreenshots(id)
    }

    override suspend fun getGameTrailers(id: Int, count: Int?) {
        gameRepository.getGameTrailers(id)
    }
}