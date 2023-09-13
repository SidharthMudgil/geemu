package com.sidharth.geemu.domain.usecase.game

interface GameUseCase {

    suspend fun getGenres()

    suspend fun getUpcomingGames()

    suspend fun getBestOfTheYear()

    suspend fun getBestOfAllTime()

    suspend fun getGamesBySearch(query: String)

    suspend fun getGamesByCreators(creators: String)

    suspend fun getGamesByDevelopers(developers: String)

    suspend fun getGamesByGenres(genres: String)

    suspend fun getGamesByPlatforms(platforms: String)

    suspend fun getGamesByPublishers(publishers: String)

    suspend fun getGamesByStores(stores: String)

    suspend fun getGamesByTags(tags: String)

    suspend fun getGameDevelopmentTeam(id: Int)

    suspend fun getGameDLCs(id: Int)

    suspend fun getGameScreenshots(id: Int)

    suspend fun getGameTrailers(id: Int)
}