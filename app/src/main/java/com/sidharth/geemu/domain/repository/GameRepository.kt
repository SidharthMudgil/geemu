package com.sidharth.geemu.domain.repository

interface GameRepository {
    suspend fun getGames(
        page: Int?, pageSize: Int?, ordering: String?,
        search: String?, searchPrecise: Boolean?, searchExact: Boolean?,
        parentPlatforms: String?, platforms: String?, platformsCount: Int?,
        creators: String?, developers: String?, publishers: String?,
        genres: String?, tags: String?, stores: String?,
        dates: String?, updated: String?, metacritic: String?,
        excludeStores: String?,
        excludeCollection: Int?,
        excludeAdditions: Boolean?,
        excludeParents: Boolean?,
        excludeGameSeries: Boolean?,
    )

    suspend fun getGameDetails(id: Int)

    suspend fun getGameAchievements(
        id: Int, page: Int?, pageSize: Int?, order: String?
    )

    suspend fun getGameDLCs(
        id: Int, page: Int?, pageSize: Int?, order: String?
    )

    suspend fun getGameScreenshots(
        id: Int, page: Int?, pageSize: Int?, order: String?
    )

    suspend fun getGameTrailers(
        id: Int, page: Int?, pageSize: Int?, order: String?
    )

    suspend fun getCreatorDetails(id: Int)

    suspend fun getDeveloperDetails(id: Int)

    suspend fun getGenreDetails(id: Int)

    suspend fun getPlatformDetails(id: Int)

    suspend fun getPublisherDetails(id: Int)

    suspend fun getStoreDetails(id: Int)

    suspend fun getTagDetails(id: Int)
}