package com.sidharth.geemu.data.remote.response.game.details

import com.google.gson.annotations.SerializedName

data class GameDetailsResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("slug") val slug: String,
    @SerializedName("name") val name: String,
    @SerializedName("name_original") val nameOriginal: String,
    @SerializedName("description") val description: String?,
    @SerializedName("metacritic") val metacritic: Int,
    @SerializedName("metacritic_platforms") val metacriticPlatforms: List<MetacriticPlatform>,
    @SerializedName("released") val released: String,
    @SerializedName("tba") val tba: Boolean,
    @SerializedName("updated") val updated: String,
    @SerializedName("background_image") val backgroundImage: String?,
    @SerializedName("background_image_additional") val backgroundImageAdditional: String?,
    @SerializedName("website") val website: String,
    @SerializedName("rating") val rating: Double,
    @SerializedName("rating_top") val ratingTop: Int,
    @SerializedName("ratings") val ratings: List<Rating>,
    @SerializedName("reactions") val reactions: Reactions,
    @SerializedName("added") val added: Int,
    @SerializedName("added_by_status") val addedByStatus: AddedByStatus,
    @SerializedName("playtime") val playtime: Int,
    @SerializedName("screenshots_count") val screenshotsCount: Int,
    @SerializedName("movies_count") val moviesCount: Int,
    @SerializedName("creators_count") val creatorsCount: Int,
    @SerializedName("achievements_count") val achievementsCount: Int,
    @SerializedName("parent_achievements_count") val parentAchievementsCount: Int,
    @SerializedName("reddit_url") val redditUrl: String,
    @SerializedName("reddit_name") val redditName: String,
    @SerializedName("reddit_description") val redditDescription: String,
    @SerializedName("reddit_logo") val redditLogo: String,
    @SerializedName("reddit_count") val redditCount: Int,
    @SerializedName("twitch_count") val twitchCount: Int,
    @SerializedName("youtube_count") val youtubeCount: Int,
    @SerializedName("reviews_text_count") val reviewsTextCount: Int,
    @SerializedName("ratings_count") val ratingsCount: Int,
    @SerializedName("suggestions_count") val suggestionsCount: Int,
    @SerializedName("alternative_names") val alternativeNames: List<String>,
    @SerializedName("metacritic_url") val metacriticUrl: String,
    @SerializedName("parents_count") val parentsCount: Int,
    @SerializedName("additions_count") val additionsCount: Int,
    @SerializedName("game_series_count") val gameSeriesCount: Int,
    @SerializedName("user_game") val userGame: Any?,
    @SerializedName("reviews_count") val reviewsCount: Int,
    @SerializedName("saturated_color") val saturatedColor: String,
    @SerializedName("dominant_color") val dominantColor: String,
    @SerializedName("parent_platforms") val parentPlatforms: List<ParentPlatform>,
    @SerializedName("platforms") val platforms: List<Platform>,
    @SerializedName("stores") val stores: List<Store>,
    @SerializedName("developers") val developers: List<Developer>,
    @SerializedName("genres") val genres: List<Genre>,
    @SerializedName("tags") val tags: List<Tag>,
    @SerializedName("publishers") val publishers: List<Publisher>,
    @SerializedName("esrb_rating") val esrbRating: ESRBRating,
    @SerializedName("clip") val clip: Any?,
    @SerializedName("description_raw") val descriptionRaw: String?,
)

data class MetacriticPlatform(
    @SerializedName("metascore") val metascore: Int,
    @SerializedName("url") val url: String,
    @SerializedName("platform") val platform: MetacriticPlatformDetails
)

data class MetacriticPlatformDetails(
    @SerializedName("platform") val platform: Int,
    @SerializedName("name") val name: String,
    @SerializedName("slug") val slug: String
)

data class Rating(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("count") val count: Int,
    @SerializedName("percent") val percent: Double
)

data class Reactions(
    @SerializedName("1") val reaction1: Int,
    @SerializedName("2") val reaction2: Int,
    @SerializedName("3") val reaction3: Int,
    @SerializedName("4") val reaction4: Int,
    @SerializedName("5") val reaction5: Int,
    @SerializedName("6") val reaction6: Int,
    @SerializedName("7") val reaction7: Int,
    @SerializedName("8") val reaction8: Int,
    @SerializedName("9") val reaction9: Int,
    @SerializedName("10") val reaction10: Int,
    @SerializedName("11") val reaction11: Int,
    @SerializedName("12") val reaction12: Int,
    @SerializedName("13") val reaction13: Int,
    @SerializedName("14") val reaction14: Int,
    @SerializedName("15") val reaction15: Int,
    @SerializedName("16") val reaction16: Int,
    @SerializedName("18") val reaction18: Int,
    @SerializedName("20") val reaction20: Int,
    @SerializedName("21") val reaction21: Int
)

data class AddedByStatus(
    @SerializedName("yet") val yet: Int?,
    @SerializedName("owned") val owned: Int?,
    @SerializedName("beaten") val beaten: Int?,
    @SerializedName("toplay") val toplay: Int?,
    @SerializedName("dropped") val dropped: Int?,
    @SerializedName("playing") val playing: Int?,
)

data class ParentPlatform(
    @SerializedName("platform") val platform: ParentPlatformDetails,
)

data class ParentPlatformDetails(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("slug") val slug: String,
)

data class Platform(
    @SerializedName("platform") val platform: PlatformDetails,
    @SerializedName("released_at") val releasedAt: String,
    @SerializedName("requirements") val requirements: Requirements,
)

data class PlatformDetails(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("slug") val slug: String,
    @SerializedName("image") val image: String?,
    @SerializedName("year_end") val yearEnd: Int?,
    @SerializedName("year_start") val yearStart: Int?,
    @SerializedName("games_count") val gamesCount: Int,
    @SerializedName("image_background") val imageBackground: String,
)

data class Requirements(
    @SerializedName("minimum") val minimum: String?,
    @SerializedName("recommended") val recommended: String?,
)

data class Store(
    @SerializedName("id") val id: Int,
    @SerializedName("url") val url: String?,
    @SerializedName("store") val store: StoreDetails,
)

data class StoreDetails(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("slug") val slug: String,
    @SerializedName("domain") val domain: String,
    @SerializedName("games_count") val gamesCount: Int,
    @SerializedName("image_background") val imageBackground: String,
)

data class Developer(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("slug") val slug: String,
    @SerializedName("games_count") val gamesCount: Int,
    @SerializedName("image_background") val imageBackground: String,
)

data class Genre(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("slug") val slug: String,
    @SerializedName("games_count") val gamesCount: Int,
    @SerializedName("image_background") val imageBackground: String,
)

data class Tag(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("slug") val slug: String,
    @SerializedName("language") val language: String,
    @SerializedName("games_count") val gamesCount: Int,
    @SerializedName("image_background") val imageBackground: String,
)

data class Publisher(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("slug") val slug: String,
    @SerializedName("games_count") val gamesCount: Int,
    @SerializedName("image_background") val imageBackground: String
)

data class ESRBRating(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("slug") val slug: String,
)