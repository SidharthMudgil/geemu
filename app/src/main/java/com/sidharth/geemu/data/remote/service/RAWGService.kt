package com.sidharth.geemu.data.remote.service

import com.sidharth.geemu.data.remote.response.creator.CreatorDetailsResponse
import com.sidharth.geemu.data.remote.response.game.details.GameDetailsResponse
import com.sidharth.geemu.data.remote.response.game.games.GamesAdditionsResponse
import com.sidharth.geemu.data.remote.response.game.movies.GameMoviesResponse
import com.sidharth.geemu.data.remote.response.game.screenshots.GameScreenshotsResponse
import com.sidharth.geemu.data.remote.response.game.team.GameDevelopmentTeamResponse
import com.sidharth.geemu.data.remote.response.GenresResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RAWGService {

    @GET("games")
    suspend fun getGames(
        @Query("page") page: Int? = null,
        @Query("page_size") pageSize: Int? = null,
        @Query("search") search: String? = null,
        @Query("search_precise") searchPrecise: Boolean? = null,
        @Query("search_exact") searchExact: Boolean? = null,
        @Query("parent_platforms") parentPlatforms: String? = null,
        @Query("platforms") platforms: String? = null,
        @Query("stores") stores: String? = null,
        @Query("developers") developers: String? = null,
        @Query("publishers") publishers: String? = null,
        @Query("genres") genres: String? = null,
        @Query("tags") tags: String? = null,
        @Query("creators") creators: String? = null,
        @Query("dates") dates: String? = null,
        @Query("updated") updated: String? = null,
        @Query("platforms_count") platformsCount: Int? = null,
        @Query("metacritic") metacritic: String? = null,
        @Query("exclude_collection") excludeCollection: Int? = null,
        @Query("exclude_additions") excludeAdditions: Boolean? = null,
        @Query("exclude_parents") excludeParents: Boolean? = null,
        @Query("exclude_game_series") excludeGameSeries: Boolean? = null,
        @Query("exclude_stores") excludeStores: String? = null,
        @Query("ordering") ordering: String? = null,
    ): Response<GamesAdditionsResponse>

    @GET("games/{id}")
    suspend fun getGameDetails(
        @Path("id") id: Int
    ): Response<GameDetailsResponse>

    @GET("games/{id}/development-team")
    suspend fun getGameDevelopmentTeam(
        @Path("id") id: Int,
        @Query("page") page: Int? = null,
        @Query("page_size") pageSize: Int? = null,
        @Query("ordering") ordering: String? = null,
    ): Response<GameDevelopmentTeamResponse>

    @GET("games/{id}/additions")
    suspend fun getGameAdditions(
        @Path("id") id: Int,
        @Query("page") page: Int? = null,
        @Query("page_size") pageSize: Int? = null,
        @Query("ordering") ordering: String? = null,
    ): Response<GamesAdditionsResponse>

    @GET("games/{id}/screenshots")
    suspend fun getGameScreenshots(
        @Path("id") id: Int,
        @Query("page") page: Int? = null,
        @Query("page_size") pageSize: Int? = null,
        @Query("ordering") ordering: String? = null,
    ): Response<GameScreenshotsResponse>

    @GET("games/{id}/movies")
    suspend fun getGameMovies(
        @Path("id") id: Int,
        @Query("page") page: Int? = null,
        @Query("page_size") pageSize: Int? = null,
        @Query("ordering") ordering: String? = null,
    ): Response<GameMoviesResponse>

    @GET("genres")
    suspend fun getGenres(
        @Query("page") page: Int? = null,
        @Query("page_size") pageSize: Int? = null,
        @Query("ordering") ordering: String? = null,
    ): Response<GenresResponse>

    @GET("creators/{id}")
    suspend fun getCreatorDetails(
        @Path("id") id: Int
    ): Response<CreatorDetailsResponse>
}