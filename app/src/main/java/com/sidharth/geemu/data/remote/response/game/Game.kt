package com.sidharth.geemu.data.remote.response.game

import com.google.gson.annotations.SerializedName
import com.sidharth.geemu.data.remote.response.game.details.AddedByStatus
import com.sidharth.geemu.data.remote.response.game.details.ESRBRating
import com.sidharth.geemu.data.remote.response.game.details.Genre
import com.sidharth.geemu.data.remote.response.game.details.ParentPlatform
import com.sidharth.geemu.data.remote.response.game.details.PlatformDetails
import com.sidharth.geemu.data.remote.response.game.details.Rating
import com.sidharth.geemu.data.remote.response.game.details.Requirements
import com.sidharth.geemu.data.remote.response.game.details.Store
import com.sidharth.geemu.data.remote.response.game.details.Tag

data class Game(
    @SerializedName("id") val id: Int,
    @SerializedName("slug") val slug: String,
    @SerializedName("name") val name: String,
    @SerializedName("released") val released: String,
    @SerializedName("tba") val tba: Boolean,
    @SerializedName("background_image") val backgroundImage: String?,
    @SerializedName("rating") val rating: Double,
    @SerializedName("rating_top") val ratingTop: Int,
    @SerializedName("ratings") val ratings: List<Rating>,
    @SerializedName("ratings_count") val ratingsCount: Int,
    @SerializedName("reviews_text_count") val reviewsTextCount: Int,
    @SerializedName("added") val added: Int,
    @SerializedName("added_by_status") val addedByStatus: AddedByStatus,
    @SerializedName("metacritic") val metacritic: Int?,
    @SerializedName("playtime") val playtime: Int,
    @SerializedName("suggestions_count") val suggestionsCount: Int,
    @SerializedName("updated") val updated: String,
    @SerializedName("user_game") val userGame: Any?,
    @SerializedName("reviews_count") val reviewsCount: Int,
    @SerializedName("community_rating") val communityRating: Int?,
    @SerializedName("saturated_color") val saturatedColor: String,
    @SerializedName("dominant_color") val dominantColor: String,
    @SerializedName("platforms") val platforms: List<Platform>,
    @SerializedName("parent_platforms") val parentPlatforms: List<ParentPlatform>,
    @SerializedName("genres") val genres: List<Genre>,
    @SerializedName("stores") val stores: List<Store>,
    @SerializedName("clip") val clip: Any?,
    @SerializedName("tags") val tags: List<Tag>,
    @SerializedName("esrb_rating") val esrbRating: ESRBRating,
    @SerializedName("short_screenshots") val shortScreenshots: List<ShortScreenshot>
)

data class Platform(
    @SerializedName("platform") val platform: PlatformDetails,
    @SerializedName("released_at") val releasedAt: String,
    @SerializedName("requirements_en") val requirementsEn: Requirements?,
    @SerializedName("requirements_ru") val requirementsRu: Requirements?,
)

data class ShortScreenshot(
    @SerializedName("id") val id: Int,
    @SerializedName("image") val image: String
)