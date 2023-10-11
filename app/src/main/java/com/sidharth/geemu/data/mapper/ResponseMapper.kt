package com.sidharth.geemu.data.mapper

import com.sidharth.geemu.core.constant.Constants.BACKGROUND_IMAGE
import com.sidharth.geemu.core.constant.Constants.PROFILE_IMAGE
import com.sidharth.geemu.data.remote.response.GenresResponse
import com.sidharth.geemu.data.remote.response.creator.CreatorDetailsResponse
import com.sidharth.geemu.data.remote.response.game.details.GameDetailsResponse
import com.sidharth.geemu.data.remote.response.game.games.GamesAdditionsResponse
import com.sidharth.geemu.data.remote.response.game.movies.GameMoviesResponse
import com.sidharth.geemu.data.remote.response.game.screenshots.GameScreenshotsResponse
import com.sidharth.geemu.data.remote.response.game.team.GameDevelopmentTeamResponse
import com.sidharth.geemu.domain.model.Creator
import com.sidharth.geemu.domain.model.CreatorDetails
import com.sidharth.geemu.domain.model.Developer
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.model.GameDetails
import com.sidharth.geemu.domain.model.Genre
import com.sidharth.geemu.domain.model.Platform
import com.sidharth.geemu.domain.model.Publisher
import com.sidharth.geemu.domain.model.Rating
import com.sidharth.geemu.domain.model.Store
import com.sidharth.geemu.domain.model.Tag
import com.sidharth.geemu.domain.model.Timeline
import com.sidharth.geemu.domain.model.Trailer

object ResponseMapper {

    fun toGameDetails(
        details: GameDetailsResponse?,
        additions: GamesAdditionsResponse?,
        screenshots: GameScreenshotsResponse?,
        movies: GameMoviesResponse?,
        creators: GameDevelopmentTeamResponse?,
    ): GameDetails {
        return GameDetails(
            id = details?.id ?: 0,
            name = details?.name ?: "",
            image = details?.backgroundImage ?: details?.backgroundImageAdditional ?: "https://wallpaper-mania.com/wp-content/uploads/2018/09/High_resolution_wallpaper_background_ID_77700200186.jpg",
            background = details?.backgroundImageAdditional ?: details?.backgroundImage ?: "https://wallpaper-mania.com/wp-content/uploads/2018/09/High_resolution_wallpaper_background_ID_77700200186.jpg",
            description = details?.description ?: details?.descriptionRaw ?: "",
            release = details?.released ?: "",
            rating = details?.rating ?: 0.0,
            esrbRating = details?.esrbRating?.name ?: "",
            ratings = details?.ratings?.map {
                Rating(
                    title = it.title,
                    count = it.count,
                    percent = it.percent,
                )
            } ?: listOf(),
            screenshots = screenshots?.results?.map {
                it.image
            } ?: listOf(),
            trailers = movies?.results?.map {
                Trailer(
                    name = it.name,
                    preview = it.preview,
                    qualityLow = it.data.low,
                    qualityMax = it.data.max
                )
            } ?: listOf(),
            genres = details?.genres?.map {
                Genre(
                    id = it.id,
                    name = it.name,
                    image = it.imageBackground,
                )
            } ?: listOf(),
            tags = details?.tags?.map {
                Tag(
                    id = it.id,
                    name = it.name,
                    image = it.imageBackground,
                    count = it.gamesCount,
                )
            } ?: listOf(),
            additions = additions?.toGames() ?: listOf(),
            creators = creators?.results?.map {
                Creator(
                    id = it.id,
                    name = it.name,
                    image = it.image ?: PROFILE_IMAGE,
                    background = it.imageBackground ?: BACKGROUND_IMAGE,
                    role = it.positions[0].name
                )
            } ?: listOf(),
            developers = details?.developers?.map {
                Developer(
                    id = it.id,
                    name = it.name,
                    image = it.imageBackground,
                )
            } ?: listOf(),
            publishers = details?.publishers?.map {
                Publisher(
                    id = it.id, name = it.name, image = it.imageBackground
                )
            } ?: listOf(),
            platforms = details?.platforms?.map {
                Platform(
                    id = it.platform.id,
                    name = it.platform.name,
                )
            } ?: listOf(),
            stores = details?.stores?.map {
                Store(
                    id = it.store.id,
                    name = it.store.name,
                    domain = it.store.domain,
                    image = it.store.imageBackground,
                )
            } ?: listOf(),
        )
    }

    fun GenresResponse.toGenres(): List<Genre> {
        return this.results.map {
            Genre(
                id = it.id,
                name = it.name,
                image = it.imageBackground,
            )
        }
    }

    fun CreatorDetailsResponse.toCreatorDetails(): CreatorDetails {
        return CreatorDetails(
            id = this.id,
            name = this.name,
            image = this.image ?: "https://i.pinimg.com/736x/b2/ea/a0/b2eaa0d4918d54021f9c7aa3fc3d3cf3.jpg",
            background = this.imageBackground ?: "https://wallpaper-mania.com/wp-content/uploads/2018/09/High_resolution_wallpaper_background_ID_77700200186.jpg",
            description = this.description,
            rating = this.rating,
            reviewsCount = this.reviewsCount,
            gamesCount = this.gamesCount,
            ratings = this.ratings.map {
                Rating(
                    title = it.title,
                    count = it.count,
                    percent = it.percentage,
                )
            },
            timeline = this.timeline.map {
                Timeline(
                    year = it.year,
                    count = it.count,
                )
            },
        )
    }

    fun GamesAdditionsResponse.toGames(): List<Game> {
        return this.results.map { response ->
            Game(
                id = response.id,
                name = response.name,
                image = response.backgroundImage ?: "",
                genres = response.genres.take(2)
                    .joinToString(", ") { it.name },
                release = response.released ?: "",
                rating = response.rating,
            )
        }
    }
}