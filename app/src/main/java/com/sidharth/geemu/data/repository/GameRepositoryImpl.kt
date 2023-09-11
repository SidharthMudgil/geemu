package com.sidharth.geemu.data.repository

import com.sidharth.geemu.domain.repository.GameRepository

class GameRepositoryImpl: GameRepository {
    override suspend fun getGames(
        page: Int?,
        pageSize: Int?,
        ordering: String?,
        search: String?,
        searchPrecise: Boolean?,
        searchExact: Boolean?,
        parentPlatforms: String?,
        platforms: String?,
        platformsCount: Int?,
        creators: String?,
        developers: String?,
        publishers: String?,
        genres: String?,
        tags: String?,
        stores: String?,
        dates: String?,
        updated: String?,
        metacritic: String?,
        excludeStores: String?,
        excludeCollection: Int?,
        excludeAdditions: Boolean?,
        excludeParents: Boolean?,
        excludeGameSeries: Boolean?
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun getGameDetails(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getGameAchievements(id: Int, page: Int?, pageSize: Int?, order: String?) {
        TODO("Not yet implemented")
    }

    override suspend fun getGameDLCs(id: Int, page: Int?, pageSize: Int?, order: String?) {
        TODO("Not yet implemented")
    }

    override suspend fun getGameScreenshots(id: Int, page: Int?, pageSize: Int?, order: String?) {
        TODO("Not yet implemented")
    }

    override suspend fun getGameTrailers(id: Int, page: Int?, pageSize: Int?, order: String?) {
        TODO("Not yet implemented")
    }

    override suspend fun getCreatorDetails(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getDeveloperDetails(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getGenreDetails(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getPlatformDetails(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getPublisherDetails(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getStoreDetails(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getTagDetails(id: Int) {
        TODO("Not yet implemented")
    }
}