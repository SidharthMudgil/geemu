package com.sidharth.geemu.data.remote.source

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

@AssistedFactory
interface GamesPagingSourceFactory {
    fun create(
        @Assisted("pageSize") pageSize: Int? = null,
        @Assisted("search") search: String? = null,
        @Assisted("searchPrecise") searchPrecise: Boolean? = null,
        @Assisted("searchExact") searchExact: Boolean? = null,
        @Assisted("parentPlatforms") parentPlatforms: String? = null,
        @Assisted("platforms") platforms: String? = null,
        @Assisted("stores") stores: String? = null,
        @Assisted("developers") developers: String? = null,
        @Assisted("publishers") publishers: String? = null,
        @Assisted("genres") genres: String? = null,
        @Assisted("tags") tags: String? = null,
        @Assisted("creators") creators: String? = null,
        @Assisted("dates") dates: String? = null,
        @Assisted("updated") updated: String? = null,
        @Assisted("platformsCount") platformsCount: Int? = null,
        @Assisted("metacritic") metacritic: String? = null,
        @Assisted("excludeCollection") excludeCollection: Int? = null,
        @Assisted("excludeAdditions") excludeAdditions: Boolean? = null,
        @Assisted("excludeParents") excludeParents: Boolean? = null,
        @Assisted("excludeGameSeries") excludeGameSeries: Boolean? = null,
        @Assisted("excludeStores") excludeStores: String? = null,
        @Assisted("ordering") ordering: String? = null
    ): GamesPagingSource
}
