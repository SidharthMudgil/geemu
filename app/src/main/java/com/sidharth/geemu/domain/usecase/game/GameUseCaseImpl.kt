package com.sidharth.geemu.domain.usecase.game

import com.sidharth.geemu.core.utils.DateTimeUtils
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.model.Genre
import com.sidharth.geemu.domain.repository.GameRepository

class GameUseCaseImpl(
    private val gameRepository: GameRepository
) : GameUseCase {

    override suspend fun getGenres(): List<Genre> {
        return gameRepository.getGenres()
    }

    override suspend fun getUpcomingGames(): List<Game> {
        return gameRepository.getGames(
            dates = DateTimeUtils.getOneYearRange(),
            ordering = "-released"
        )
    }

    override suspend fun getBestOfTheYear(): List<Game> {
        return gameRepository.getGames(
            dates = DateTimeUtils.getOneYearRange(past = true),
            ordering = "-rating",
            metacritic = "1,100",
        )
    }

    override suspend fun getBestOfAllTime(): List<Game> {
        return gameRepository.getGames(
            ordering = "-rating",
            metacritic = "1,100"
        )
    }

    override suspend fun getGamesBySearch(query: String): List<Game> {
        return gameRepository.getGames(search = query)
    }

    override suspend fun getGamesByCreators(creators: String): List<Game> {
        return gameRepository.getGames(creators = creators)
    }

    override suspend fun getGamesByDevelopers(developers: String): List<Game> {
        return gameRepository.getGames(developers = developers)
    }

    override suspend fun getGamesByGenres(genres: String): List<Game> {
        return gameRepository.getGames(genres = genres)
    }

    override suspend fun getGamesByPublishers(publishers: String): List<Game> {
        return gameRepository.getGames(publishers = publishers)
    }

    override suspend fun getGamesByTags(tags: String): List<Game> {
        return gameRepository.getGames(tags = tags)
    }
}