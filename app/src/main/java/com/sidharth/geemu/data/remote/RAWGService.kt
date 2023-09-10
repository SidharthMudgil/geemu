package com.sidharth.geemu.data.remote

import com.sidharth.geemu.data.remote.response.CreatorDetailsResponse
import com.sidharth.geemu.data.remote.response.DeveloperDetailsResponse
import com.sidharth.geemu.data.remote.response.GenreDetailsResponse
import com.sidharth.geemu.data.remote.response.GenresResponse
import com.sidharth.geemu.data.remote.response.PlatformDetailsResponse
import com.sidharth.geemu.data.remote.response.PublisherDetailsResponse
import com.sidharth.geemu.data.remote.response.StoreDetailsResponse
import com.sidharth.geemu.data.remote.response.TagDetailsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RAWGService {
    @GET("games")
    suspend fun getGames(
        @Path("id") id: Int
    )

    @GET("games/{id}")
    suspend fun getGameDetails(
        @Path("id") id: Int
    )

    @GET("games/{id}/additions")
    suspend fun getGameAchievements(
        @Path("id") id: Int,
        @Query("page") page:Int = 1,
        @Query("page_size") pageSize: Int = 20,
        @Query("ordering") ordering: String = "name",
    )

    @GET("games/{id}/additions")
    suspend fun getGameAdditions(
        @Path("id") id: Int
    )

    @GET("games/{id}/screenshots")
    suspend fun getGameScreenshots(
        @Path("id") id: Int,
        @Query("page") page:Int = 1,
        @Query("page_size") pageSize: Int = 20,
        @Query("ordering") ordering: String = "id",
    )

    @GET("games/{id}/movies")
    suspend fun getGameMovies(
        @Path("id") id: Int,
        @Query("page") page:Int = 1,
        @Query("page_size") pageSize: Int = 20,
        @Query("ordering") ordering: String = "name",
    )

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