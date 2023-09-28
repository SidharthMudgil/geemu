package com.sidharth.geemu.domain.usecase.game

import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.model.Genre
import kotlinx.coroutines.flow.Flow

interface GetGameUseCase {

    suspend fun getGenres(): Flow<List<Genre>>

    suspend fun getUpcomingGames(): Flow<List<Game>>

    suspend fun getBestOfTheYear(): Flow<List<Game>>

    suspend fun getBestOfAllTime(): Flow<List<Game>>

    suspend fun getGamesBySearch(query: String): Flow<List<Game>>

    suspend fun getGamesByCreators(creators: String): Flow<List<Game>>

    suspend fun getGamesByDevelopers(developers: String): Flow<List<Game>>

    suspend fun getGamesByGenres(genres: String): Flow<List<Game>>

    suspend fun getGamesByPublishers(publishers: String): Flow<List<Game>>

    suspend fun getGamesByTags(tags: String): Flow<List<Game>>
}