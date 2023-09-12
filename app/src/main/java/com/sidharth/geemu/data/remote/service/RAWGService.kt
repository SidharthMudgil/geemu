package com.sidharth.geemu.data.remote.service

import com.sidharth.geemu.data.remote.response.DeveloperDetailsResponse
import com.sidharth.geemu.data.remote.response.GenreDetailsResponse
import com.sidharth.geemu.data.remote.response.PlatformDetailsResponse
import com.sidharth.geemu.data.remote.response.PublisherDetailsResponse
import com.sidharth.geemu.data.remote.response.StoreDetailsResponse
import com.sidharth.geemu.data.remote.response.TagDetailsResponse
import com.sidharth.geemu.data.remote.response.creator.CreatorDetailsResponse
import com.sidharth.geemu.data.remote.response.game.achievements.GameAchievementsResponse
import com.sidharth.geemu.data.remote.response.game.details.GameDetailsResponse
import com.sidharth.geemu.data.remote.response.game.games.GamesAdditionsResponse
import com.sidharth.geemu.data.remote.response.game.movies.GameMoviesResponse
import com.sidharth.geemu.data.remote.response.game.screenshots.GameScreenshotsResponse
import com.sidharth.geemu.data.remote.response.genres.GenresResponse
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

    @GET("games/{id}/additions")
    suspend fun getGameAchievements(
        @Path("id") id: Int,
        @Query("page") page: Int? = null,
        @Query("page_size") pageSize: Int? = null,
        @Query("ordering") ordering: String? = null,
    ): Response<GameAchievementsResponse>

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

    @GET("genres/{id}")
    suspend fun getGenreDetails(
        @Path("id") id: Int
    ): Response<GenreDetailsResponse>

    @GET("creators/{id}")
    suspend fun getCreatorDetails(
        @Path("id") id: Int
    ): Response<CreatorDetailsResponse>

    @GET("developers/{id}")
    suspend fun getDeveloperDetails(
        @Path("id") id: Int
    ): Response<DeveloperDetailsResponse>

    @GET("platforms/{id}")
    suspend fun getPlatformDetails(
        @Path("id") id: Int
    ): Response<PlatformDetailsResponse>

    @GET("publishers/{id}")
    suspend fun getPublisherDetails(
        @Path("id") id: Int
    ): Response<PublisherDetailsResponse>

    @GET("stores/{id}")
    suspend fun getStoreDetails(
        @Path("id") id: Int
    ): Response<StoreDetailsResponse>

    @GET("tags/{id}")
    suspend fun getTagDetails(
        @Path("id") id: Int
    ): Response<TagDetailsResponse>
}