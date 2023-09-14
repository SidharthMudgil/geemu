package com.sidharth.geemu.data.remote.source

import com.sidharth.geemu.data.remote.response.GenresResponse
import com.sidharth.geemu.data.remote.response.creator.CreatorDetailsResponse
import com.sidharth.geemu.data.remote.response.game.details.GameDetailsResponse
import com.sidharth.geemu.data.remote.response.game.games.GamesAdditionsResponse
import com.sidharth.geemu.data.remote.response.game.movies.GameMoviesResponse
import com.sidharth.geemu.data.remote.response.game.screenshots.GameScreenshotsResponse
import com.sidharth.geemu.data.remote.response.game.team.GameDevelopmentTeamResponse
import com.sidharth.geemu.data.remote.service.RAWGService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
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
    ): GamesAdditionsResponse? {
        val response = rawgService.getGames(
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

        return when (response.isSuccessful) {
            true -> response.body()
            else -> null
        }
    }

    suspend fun getGenres(): GenresResponse? {
        val response = rawgService.getGenres(
            pageSize = 20,
        )

        return when (response.isSuccessful) {
            true -> response.body()
            else -> null
        }
    }

    suspend fun getGameDetails(
        id: Int
    ): GameDetailsResponse? {
        val response = rawgService.getGameDetails(id)

        return when (response.isSuccessful) {
            true -> response.body()
            else -> null
        }
    }

    suspend fun getGameAdditions(
        id: Int,
        count: Int,
    ): GamesAdditionsResponse? {
        val response = rawgService.getGameAdditions(
            id = id,
            page = 1,
            pageSize = count,
            ordering = "name"
        )

        return when (response.isSuccessful) {
            true -> response.body()
            else -> null
        }
    }

    suspend fun getGameScreenshots(
        id: Int,
        count: Int,
    ): GameScreenshotsResponse? {
        val response = rawgService.getGameScreenshots(
            id = id,
            page = 1,
            pageSize = count,
            ordering = "id"
        )

        return when (response.isSuccessful) {
            true -> response.body()
            else -> null
        }
    }

    suspend fun getGameDevelopmentTeam(
        id: Int
    ): GameDevelopmentTeamResponse? { // TODO paging for this too
        val response = rawgService.getGameDevelopmentTeam(
            id = id,
            page = 1,
            pageSize = 12,
            ordering = "name",
        )

        return when (response.isSuccessful) {
            true -> response.body()
            else -> null
        }
    }

    suspend fun getGameMovies(
        id: Int,
        count: Int,
    ): GameMoviesResponse? {
        val response = rawgService.getGameMovies(
            id = id,
            page = 1,
            pageSize = count,
            ordering = "name"
        )

        return when (response.isSuccessful) {
            true -> response.body()
            else -> null
        }
    }

    suspend fun getCreatorDetails(
        id: Int
    ): CreatorDetailsResponse? {
        val response = rawgService.getCreatorDetails(id)

        return when (response.isSuccessful) {
            true -> response.body()
            else -> null
        }
    }
}