package com.sidharth.geemu.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class GameDetails(
    val id: Int,
    val name: String,
    val image: String,
    val background: String,
    val description: String,
    val platforms: List<Platform>,
    val rating: Double,
    val release: String,
    val genres: List<Genre>,
    val esrbRating: String,
    val screenshots: List<String>,
    val trailers: List<Trailer>,
    val additions: List<Game>,
    val creators: List<Creator>,
    val developers: List<Developer>,
    val publishers: List<Publisher>,
    val stores: List<Store>,
    val ratings: List<Rating>,
    val tags: List<Tag>,
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
    val role: String,
)

data class Publisher(
    val id: Int,
    val name: String,
    val image: String,
)

@Parcelize
data class Tag(
    val id: Int,
    val name: String,
    val image: String,
    val count: Int,
) : Parcelable