package com.sidharth.geemu.domain.usecase.game

import com.sidharth.geemu.core.utils.DateTimeUtils
import com.sidharth.geemu.domain.repository.GameRepository

class GameUseCaseImpl(
    private val gameRepository: GameRepository
) : GameUseCase {

    override suspend fun getGenres() {
        gameRepository.getGenres()
    }

    override suspend fun getUpcomingGames() {
        gameRepository.getGames(
            dates = DateTimeUtils.getOneYearRange(),
            ordering = "-released"
        )
    }

    override suspend fun getBestOfTheYear() {
        gameRepository.getGames(
            dates = DateTimeUtils.getOneYearRange(past = true),
            ordering = "-rating",
            metacritic = "1,100",
        )
    }

    override suspend fun getBestOfAllTime() {
        gameRepository.getGames(
            ordering = "-rating",
            metacritic = "1,100"
        )
    }

    override suspend fun getGamesBySearch(query: String) {
        gameRepository.getGames(search = query)
    }

    override suspend fun getGamesByCreators(creators: String) {
        gameRepository.getGames(creators = creators)
    }

    override suspend fun getGamesByDevelopers(developers: String) {
        gameRepository.getGames(developers = developers)
    }

    override suspend fun getGamesByGenres(genres: String) {
        gameRepository.getGames(genres = genres)
    }

    override suspend fun getGamesByPlatforms(platforms: String) {
        gameRepository.getGames(platforms = platforms)
    }

    override suspend fun getGamesByPublishers(publishers: String) {
        gameRepository.getGames(publishers = publishers)
    }

    override suspend fun getGamesByStores(stores: String) {
        gameRepository.getGames(stores = stores)
    }

    override suspend fun getGamesByTags(tags: String) {
        gameRepository.getGames(tags = tags)
    }

    override suspend fun getGameDevelopmentTeam(id: Int) {
        gameRepository.getGameDevelopmentTeam(id)
    }

    override suspend fun getGameDLCs(id: Int) {
        gameRepository.getGameDLCs(id)
    }

    override suspend fun getGameScreenshots(id: Int) {
        gameRepository.getGameScreenshots(id)
    }

    override suspend fun getGameTrailers(id: Int) {
        gameRepository.getGameTrailers(id)
    }

}