package com.sidharth.geemu.domain.usecase.game

import androidx.paging.PagingData
import com.sidharth.geemu.core.util.DateTimeUtils
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.model.Genre
import com.sidharth.geemu.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGameUseCaseImpl @Inject constructor(
    private val gameRepository: GameRepository,
) : GetGameUseCase {

    override suspend fun getGenres(): Flow<List<Genre>> {
        return gameRepository.getGenres()
    }

    override suspend fun getUpcomingGames(): Flow<List<Game>> {
        return gameRepository.getGames(
            dates = DateTimeUtils.getOneYearRange(),
            ordering = "released",
            page = 1,
            pageSize = 20,
        )
    }

    override suspend fun getBestOfTheYear(): Flow<List<Game>> {
        return gameRepository.getGames(
            dates = DateTimeUtils.getOneYearRange(past = true),
            ordering = "-rating",
            metacritic = "1,100",
            page = 1,
            pageSize = 20,
        )
    }

    override suspend fun getBestOfAllTime(): Flow<PagingData<Game>> {
        return gameRepository.getGamesPagingSource()
    }

    override suspend fun getGamesBySearch(query: String): Flow<List<Game>> {
        return gameRepository.getGames(search = query)
    }

    override suspend fun getGamesByCreators(creators: String): Flow<List<Game>> {
        return gameRepository.getGames(
            creators = creators,
            page = 1,
            pageSize = 20,
        )
    }

    override suspend fun getGamesByDevelopers(developers: String): Flow<List<Game>> {
        return gameRepository.getGames(developers = developers)
    }

    override suspend fun getGamesByGenres(genres: String): Flow<List<Game>> {
        return gameRepository.getGames(genres = genres)
    }

    override suspend fun getGamesByPublishers(publishers: String): Flow<List<Game>> {
        return gameRepository.getGames(publishers = publishers)
    }

    override suspend fun getGamesByTags(tags: String): Flow<List<Game>> {
        return gameRepository.getGames(tags = tags)
    }
}