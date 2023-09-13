package com.sidharth.geemu.domain.usecase.game

import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.model.Genre
import com.sidharth.geemu.domain.model.Tag

interface GameUseCase {

    suspend fun getGenres(): List<Genre>

    suspend fun getUpcomingGames(): List<Game>

    suspend fun getBestOfTheYear(): List<Game>

    suspend fun getBestOfAllTime(): List<Game>

    suspend fun getGamesBySearch(query: String): List<Game>

    suspend fun getGamesByCreators(creators: String): List<Game>

    suspend fun getGamesByDevelopers(developers: String): List<Game>

    suspend fun getGamesByGenres(genres: String): List<Game>

    suspend fun getGamesByPublishers(publishers: String): List<Game>

    suspend fun getGamesByTags(tags: String): List<Game>
}