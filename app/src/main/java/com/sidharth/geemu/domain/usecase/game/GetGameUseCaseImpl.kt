package com.sidharth.geemu.domain.usecase.game

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sidharth.geemu.core.util.DateTimeUtils
import com.sidharth.geemu.data.remote.source.GamesPagingSource
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

    override suspend fun getBestOfAllTime(): Flow<List<Game>> {
        return gameRepository.getGames(
            page = 1,
            pageSize = 20,
            ordering = "-rating",
            metacritic = "1,100",
            excludeAdditions = true,
        )
    }

    override suspend fun getGamesByCreators(creators: String): Flow<List<Game>> {
        return gameRepository.getGames(
            creators = creators,
            page = 1,
            pageSize = 20,
        )
    }

    override suspend fun getGamesBySearch(query: String): Flow<PagingData<Game>> {
        val gamesPagingSource = gameRepository.getGamesPagingSource(search = query)
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { gamesPagingSource }
        ).flow
    }

    override suspend fun getGamesByDevelopers(developers: String): Flow<PagingData<Game>> {
        val gamesPagingSource = gameRepository.getGamesPagingSource(developers = developers)
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { gamesPagingSource }
        ).flow
    }

    override suspend fun getGamesByGenres(genres: String): Flow<PagingData<Game>> {
        val gamesPagingSource = gameRepository.getGamesPagingSource(genres = genres)
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { gamesPagingSource }
        ).flow
    }

    override suspend fun getGamesByPublishers(publishers: String): Flow<PagingData<Game>> {
        val gamesPagingSource = gameRepository.getGamesPagingSource(publishers = publishers)
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { gamesPagingSource }
        ).flow
    }

    override suspend fun getGamesByTags(tags: String): Flow<PagingData<Game>> {
        val gamesPagingSource = gameRepository.getGamesPagingSource(tags = tags)
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { gamesPagingSource }
        ).flow
    }

    override suspend fun getGamesPagingSource(tags: String): GamesPagingSource {
        return gameRepository.getGamesPagingSource(tags = tags)
    }
}