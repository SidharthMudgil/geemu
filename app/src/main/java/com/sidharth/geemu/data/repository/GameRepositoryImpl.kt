package com.sidharth.geemu.data.repository

import com.sidharth.geemu.data.mapper.ResponseMapper
import com.sidharth.geemu.data.mapper.ResponseMapper.toCreatorDetails
import com.sidharth.geemu.data.mapper.ResponseMapper.toGames
import com.sidharth.geemu.data.mapper.ResponseMapper.toGenres
import com.sidharth.geemu.data.remote.source.RemoteDataSource
import com.sidharth.geemu.domain.model.CreatorDetails
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.model.GameDetails
import com.sidharth.geemu.domain.model.Genre
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
    ): List<Game> {
        return remoteDataSource.getGames(
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
        )?.toGames() ?: listOf()
    }

    override suspend fun getGameDetails(id: Int): GameDetails {
        val gameDetails = remoteDataSource.getGameDetails(id)
        val gameDevelopmentTeam = remoteDataSource.getGameDevelopmentTeam(id)

        val gameAdditions = remoteDataSource.getGameAdditions(
            id = id,
            count = gameDetails?.additionsCount ?: 0,
        )

        val gameScreenshots = remoteDataSource.getGameScreenshots(
            id = id,
            count = gameDetails?.screenshotsCount ?: 0,
        )

        val gameMovies = remoteDataSource.getGameMovies(
            id = id,
            count = gameDetails?.moviesCount ?: 0,
        )

        return ResponseMapper.toGameDetails(
            details = gameDetails,
            additions = gameAdditions,
            screenshots = gameScreenshots,
            movies = gameMovies,
            creators = gameDevelopmentTeam
        )
    }

    override suspend fun getCreatorDetails(id: Int): CreatorDetails {
        return remoteDataSource.getCreatorDetails(id)!!.toCreatorDetails()
    }

    override suspend fun getGenres(): List<Genre> {
        return remoteDataSource.getGenres()?.toGenres() ?: listOf()
    }
}