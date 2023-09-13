package com.sidharth.geemu.data.repository

import com.sidharth.geemu.data.remote.source.RemoteDataSource
import com.sidharth.geemu.domain.repository.GameRepository

class GameRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
) : GameRepository {
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
        remoteDataSource.getGames(
            page = page,
            pageSize = pageSize,
            ordering = ordering,
            search = search,
            searchPrecise = searchPrecise,
            searchExact = searchExact,
            parentPlatforms = parentPlatforms,
            platforms = platforms,
            platformsCount = platformsCount,
            creators = creators,
            developers = developers,
            publishers = publishers,
            genres = genres,
            tags = tags,
            stores = stores,
            dates = dates,
            updated = updated,
            metacritic = metacritic,
            excludeStores = excludeStores,
            excludeCollection = excludeCollection,
            excludeAdditions = excludeAdditions,
            excludeParents = excludeParents,
            excludeGameSeries = excludeGameSeries
        )
    }

    override suspend fun getGameDetails(id: Int) {
        remoteDataSource.getGameDetails(id)
    }

    override suspend fun getGameDevelopmentTeam(id: Int, page: Int?, pageSize: Int?, ordering: String?) {
        remoteDataSource.getGameDevelopmentTeam(
            id = id,
            page = page,
            pageSize = pageSize,
            ordering = ordering
        )
    }

    override suspend fun getGameDLCs(id: Int, page: Int?, pageSize: Int?, ordering: String?) {
        remoteDataSource.getGameAdditions(
            id = id,
            page = page,
            pageSize = pageSize,
            ordering = ordering
        )
    }

    override suspend fun getGameScreenshots(id: Int, page: Int?, pageSize: Int?, ordering: String?) {
        remoteDataSource.getGameScreenshots(
            id = id,
            page = page,
            pageSize = pageSize,
            ordering = ordering
        )
    }

    override suspend fun getGameTrailers(id: Int, page: Int?, pageSize: Int?, ordering: String?) {
        remoteDataSource.getGameMovies(
            id = id,
            page = page,
            pageSize = pageSize,
            ordering = ordering
        )
    }

    override suspend fun getCreatorDetails(id: Int) {
        remoteDataSource.getCreatorDetails(id)
    }

    override suspend fun getDeveloperDetails(id: Int) {
        remoteDataSource.getDeveloperDetails(id)
    }

    override suspend fun getGenres() {
        remoteDataSource.getGenres()
    }

    override suspend fun getPublisherDetails(id: Int) {
        remoteDataSource.getPublisherDetails(id)
    }
}