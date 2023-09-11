package com.sidharth.geemu.domain.usecase.game

interface GameUseCase {
    suspend fun getGames()
    suspend fun getGameDetails(id: Int, count: Int?)
    suspend fun getGameAchievements(id: Int, count: Int?)
    suspend fun getGameDLCs(id: Int, count: Int?)
    suspend fun getGameScreenshots(id: Int, count: Int?)
    suspend fun getGameTrailers(id: Int, count: Int?)
}