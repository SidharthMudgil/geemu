package com.sidharth.geemu.domain.repository

import com.sidharth.geemu.domain.model.CreatorDetails
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.model.GameDetails
import com.sidharth.geemu.domain.model.Genre

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
    ): List<Game>

    suspend fun getGameDetails(id: Int): GameDetails

    suspend fun getCreatorDetails(id: Int): CreatorDetails

    suspend fun getGenres(): List<Genre>

}