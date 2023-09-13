package com.sidharth.geemu.domain.repository

interface GameRepository {
    suspend fun getGames(
        page: Int? = null,
        pageSize: Int? = null,
        ordering: String? = null,
        search: String? = null,
        searchPrecise: Boolean? = null,
        searchExact: Boolean? = null,
        parentPlatforms: String? = null,
        platforms: String? = null,
        platformsCount: Int? = null,
        creators: String? = null,
        developers: String? = null,
        publishers: String? = null,
        genres: String? = null,
        tags: String? = null,
        stores: String? = null,
        dates: String? = null,
        updated: String? = null,
        metacritic: String? = null,
        excludeStores: String? = null,
        excludeCollection: Int? = null,
        excludeAdditions: Boolean? = null,
        excludeParents: Boolean? = null,
        excludeGameSeries: Boolean? = null,
    )

    suspend fun getGameDetails(id: Int)

    suspend fun getGameDevelopmentTeam(
        id: Int,
        page: Int? = null,
        pageSize: Int? = null,
        ordering: String? = null
    )

    suspend fun getGameDLCs(
        id: Int,
        page: Int? = null,
        pageSize: Int? = null,
        ordering: String? = null
    )

    suspend fun getGameScreenshots(
        id: Int,
        page: Int? = null,
        pageSize: Int? = null,
        ordering: String? = null
    )

    suspend fun getGameTrailers(
        id: Int,
        page: Int? = null,
        pageSize: Int? = null,
        ordering: String? = null
    )

    suspend fun getCreatorDetails(id: Int)

    suspend fun getDeveloperDetails(id: Int)

    suspend fun getGenres()

    suspend fun getPublisherDetails(id: Int)
}