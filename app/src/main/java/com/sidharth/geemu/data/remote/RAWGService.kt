package com.sidharth.geemu.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

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
        @Path("id") id: Int
    )

    @GET("games/{id}/additions")
    suspend fun getGameAdditions(
        @Path("id") id: Int
    )

    @GET("games/{id}/screenshots")
    suspend fun getGameScreenshots(
        @Path("id") id: Int
    )

    @GET("games/{id}/movies")
    suspend fun getGameMovies(
        @Path("id") id: Int
    )

    @GET("genres/{id}")
    suspend fun getGenres(
        @Path("id") id: Int
    )

    @GET("genres/{id}")
    suspend fun getGenreDetails(
        @Path("id") id: Int
    )

    @GET("creators/{id}")
    suspend fun getCreatorDetails(
        @Path("id") id: Int
    )

    @GET("developers/{id}")
    suspend fun getDeveloperDetails(
        @Path("id") id: Int
    )

    @GET("platforms/{id}")
    suspend fun getPlatformDetails(
        @Path("id") id: Int
    )

    @GET("publishers/{id}")
    suspend fun getPublisherDetails(
        @Path("id") id: Int
    )

    @GET("stores/{id}")
    suspend fun getStoreDetails(
        @Path("id") id: Int
    )

    @GET("tags/{id}")
    suspend fun getTagDetails(
        @Path("id") id: Int
    )
}