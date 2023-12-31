package com.sidharth.geemu.core.constant

import com.sidharth.geemu.domain.model.CreatorDetails
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.model.GameDetails
import com.sidharth.geemu.domain.model.Genre
import com.sidharth.geemu.presentation.explore.ExploreData

object Constants {
    const val BASE_URL = "https://api.rawg.io/api/"
    const val APP_DATABASE_NAME = "geemu_database"
    const val PROFILE_IMAGE =
        "https://i.pinimg.com/736x/b2/ea/a0/b2eaa0d4918d54021f9c7aa3fc3d3cf3.jpg"
    const val BACKGROUND_IMAGE =
        "https://wallpaper-mania.com/wp-content/uploads/2018/09/High_resolution_wallpaper_background_ID_77700200186.jpg"

    val EMPTY_GAME_DETAILS = GameDetails(
        id = -1,
        name = "",
        image = "",
        background = "",
        description = "",
        platforms = emptyList(),
        rating = 0.0,
        release = "",
        genres = emptyList(),
        esrbRating = "",
        screenshots = emptyList(),
        trailers = emptyList(),
        additions = emptyList(),
        creators = emptyList(),
        developers = emptyList(),
        publishers = emptyList(),
        stores = emptyList(),
        ratings = emptyList(),
        tags = emptyList()
    )

    val EMPTY_CREATOR_DETAILS = CreatorDetails(
        id = -1,
        name = "",
        image = "",
        background = "",
        description = "",
        rating = "",
        reviewsCount = 0,
        gamesCount = 0,
        ratings = emptyList(),
        timeline = emptyList()
    )

    private val EMPTY_GENRE = Genre(
        id = -1,
        name = "",
        image = ""
    )

    val EMPTY_GAME = Game(
        id = -1,
        name = "",
        image = "NIL",
        genres = "",
        release = "",
        rating = 0.0
    )

    val EMPTY_EXPLORE_DATA = ExploreData(
        List(5) { EMPTY_GENRE },
        List(5) { EMPTY_GAME },
        List(5) { EMPTY_GAME },
        List(5) { EMPTY_GAME }
    )

    val colors = arrayOf<Any>(
        "#C05780", "#FF828B", "#FFD872", "#FF5678", "#FC6238",
        "#00A5E3", "#8DD7BF", "#FF96C5", "#FF5768", "#FFBF65",
        "#F2D4CC", "#E77577", "#6C88C4", "#E7C582", "#00BOBA",
        "#0065A2", "#FF6F68", "#FFDACC", "#FF60A8", "#CFF800",
        "#FF5C77", "#4DD091", "#FFEC59", "#FFA23A", "#74737A"
    )
}