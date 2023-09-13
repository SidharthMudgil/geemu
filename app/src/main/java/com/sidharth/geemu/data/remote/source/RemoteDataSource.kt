package com.sidharth.geemu.data.remote.source

import com.sidharth.geemu.data.remote.service.RAWGService

class RemoteDataSource(
    private val rawgService: RAWGService,
) {
    suspend fun getGames(
        page: Int? = null,
        pageSize: Int? = null,
        search: String? = null,
        searchPrecise: Boolean? = null,
        searchExact: Boolean? = null,
        parentPlatforms: String? = null,
        platforms: String? = null,
        stores: String? = null,
        developers: String? = null,
        publishers: String? = null,
        genres: String? = null,
        tags: String? = null,
        creators: String? = null,
        dates: String? = null,
        updated: String? = null,
        platformsCount: Int? = null,
        metacritic: String? = null,
        excludeCollection: Int? = null,
        excludeAdditions: Boolean? = null,
        excludeParents: Boolean? = null,
        excludeGameSeries: Boolean? = null,
        excludeStores: String? = null,
        ordering: String? = null,
    ) {
        rawgService.getGames(
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

    suspend fun getGameDetails(id: Int) {
        rawgService.getGameDetails(id)
    }

    suspend fun getGameAdditions(
        id: Int,
        page: Int? = null,
        pageSize: Int? = null,
        ordering: String? = null,
    ) {
        rawgService.getGameAdditions(
            id = id,
            page = page,
            pageSize = pageSize,
            ordering = ordering
        )
    }

    suspend fun getGameScreenshots(
        id: Int,
        page: Int? = null,
        pageSize: Int? = null,
        ordering: String? = null,
    ) {
        rawgService.getGameScreenshots(
            id = id,
            page = page,
            pageSize = pageSize,
            ordering = ordering
        )
    }

    suspend fun getGameDevelopmentTeam(
        id: Int,
        page: Int? = null,
        pageSize: Int? = null,
        ordering: String? = null,
    ) {
        rawgService.getGameDevelopmentTeam(
            id = id,
            page = page,
            pageSize = pageSize,
            ordering = ordering
        )
    }

    suspend fun getGameMovies(
        id: Int,
        page: Int? = null,
        pageSize: Int? = null,
        ordering: String? = null,
    ) {
        rawgService.getGameMovies(
            id = id,
            page = page,
            pageSize = pageSize,
            ordering = ordering
        )
    }

    suspend fun getGenres() {
        rawgService.getGenres(
            pageSize = 20,
        )
    }

    suspend fun getGenreDetails(id: Int) {
        rawgService.getGenreDetails(id)
    }

    suspend fun getCreatorDetails(id: Int) {
        rawgService.getCreatorDetails(id)
    }

    suspend fun getDeveloperDetails(id: Int) {
        rawgService.getDeveloperDetails(id)
    }

    suspend fun getPlatformDetails(id: Int) {
        rawgService.getPlatformDetails(id)
    }

    suspend fun getPublisherDetails(id: Int) {
        rawgService.getPublisherDetails(id)
    }
}