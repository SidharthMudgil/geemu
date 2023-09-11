package com.sidharth.geemu.data.remote.service

import com.sidharth.geemu.data.remote.response.creator.CreatorDetailsResponse
import com.sidharth.geemu.data.remote.response.DeveloperDetailsResponse
import com.sidharth.geemu.data.remote.response.GenreDetailsResponse
import com.sidharth.geemu.data.remote.response.genres.GenresResponse
import com.sidharth.geemu.data.remote.response.PlatformDetailsResponse
import com.sidharth.geemu.data.remote.response.PublisherDetailsResponse
import com.sidharth.geemu.data.remote.response.StoreDetailsResponse
import com.sidharth.geemu.data.remote.response.TagDetailsResponse
import com.sidharth.geemu.data.remote.response.game.achievements.GameAchievementsResponse
import com.sidharth.geemu.data.remote.response.game.details.GameDetailsResponse
import com.sidharth.geemu.data.remote.response.game.games.GamesAdditionsResponse
import com.sidharth.geemu.data.remote.response.game.movies.GameMoviesResponse
import com.sidharth.geemu.data.remote.response.game.screenshots.GameScreenshotsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RAWGService {

    @GET("games")
    suspend fun getGames(
        @Query("page") page: Int?,
        @Query("page_size") pageSize: Int?,
        @Query("search") search: String?,
        @Query("search_precise") searchPrecise: Boolean?,
        @Query("search_exact") searchExact: Boolean?,
        @Query("parent_platforms") parentPlatforms: String?,
        @Query("platforms") platforms: String?,
        @Query("stores") stores: String?,
        @Query("developers") developers: String?,
        @Query("publishers") publishers: String?,
        @Query("genres") genres: String?,
        @Query("tags") tags: String?,
        @Query("creators") creators: String?,
        @Query("dates") dates: String?,
        @Query("updated") updated: String?,
        @Query("platforms_count") platformsCount: Int?,
        @Query("metacritic") metacritic: String?,
        @Query("exclude_collection") excludeCollection: Int?,
        @Query("exclude_additions") excludeAdditions: Boolean?,
        @Query("exclude_parents") excludeParents: Boolean?,
        @Query("exclude_game_series") excludeGameSeries: Boolean?,
        @Query("exclude_stores") excludeStores: String?,
        @Query("ordering") ordering: String?,
    ): Response<GamesAdditionsResponse>

    @GET("games/{id}")
    suspend fun getGameDetails(
        @Path("id") id: Int
    ): Response<GameDetailsResponse>

    @GET("games/{id}/additions")
    suspend fun getGameAchievements(
        @Path("id") id: Int,
        @Query("page") page:Int = 1,
        @Query("page_size") pageSize: Int = 20,
        @Query("ordering") ordering: String = "name",
    ): Response<GameAchievementsResponse>

    @GET("games/{id}/additions")
    suspend fun getGameAdditions(
        @Path("id") id: Int,
        @Query("page") page:Int = 1,
        @Query("page_size") pageSize: Int = 20,
        @Query("ordering") ordering: String = "name",
    ): Response<GamesAdditionsResponse>

    @GET("games/{id}/screenshots")
    suspend fun getGameScreenshots(
        @Path("id") id: Int,
        @Query("page") page:Int = 1,
        @Query("page_size") pageSize: Int = 20,
        @Query("ordering") ordering: String = "id",
    ): Response<GameScreenshotsResponse>

    @GET("games/{id}/movies")
    suspend fun getGameMovies(
        @Path("id") id: Int,
        @Query("page") page:Int = 1,
        @Query("page_size") pageSize: Int = 20,
        @Query("ordering") ordering: String = "name",
    ): Response<GameMoviesResponse>

    @GET("genres")
    suspend fun getGenres(
        @Query("page") page:Int = 1,
        @Query("page_size") pageSize: Int = 20,
        @Query("ordering") ordering: String = "name",
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