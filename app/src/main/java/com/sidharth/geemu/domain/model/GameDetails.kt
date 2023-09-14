package com.sidharth.geemu.domain.model

data class GameDetails(
    val id: Int,
    val name: String,
    val image: String,
    val background: String,
    val description: String,
    val released: String,
    val playtime: Int,
    val website: String?,
    val metacritic: Int?,
    val rating: Double,
    val esrbRating: String,
    val ratings: List<Rating>,
    val screenshots: List<String>,
    val trailers: List<Trailer>,
    val alternativeNames: List<String>,
    val genres: List<Genre>,
    val tags: List<Tag>,
    val minimumRequirements: String?,
    val recommendedRequirements: String?,
    val creators: List<Creator>,
    val additions: List<Game>,
    val developers: List<Developer>,
    val publishers: List<Publisher>,
    val platforms: List<Platform>,
    val stores: List<Store>,
)

data class Trailer(
    val name: String,
    val preview: String,
    val qualityLow: String,
    val qualityMax: String,
)

data class Platform(
    val id: Int,
    val name: String,
)

data class Developer(
    val id: Int,
    val name: String,
    val image: String,
)

data class Store(
    val id: Int,
    val name: String,
    val domain: String,
    val image: String,
)

data class Creator(
    val id: Int,
    val name: String,
    val image: String,
    val background: String,
    val title: String,
)

data class Publisher(
    val id: Int,
    val name: String,
    val image: String,
)

data class Tag(
    val id: Int,
    val name: String,
)