package com.sidharth.geemu.data.remote.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sidharth.geemu.data.mapper.ResponseMapper.toGames
import com.sidharth.geemu.domain.model.Game
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class GamesPagingSource @AssistedInject constructor(
    private val remoteDataSource: RemoteDataSource,
    @Assisted("pageSize") private val pageSize: Int?,
    @Assisted("search") private val search: String?,
    @Assisted("searchPrecise") private val searchPrecise: Boolean?,
    @Assisted("searchExact") private val searchExact: Boolean?,
    @Assisted("parentPlatforms") private val parentPlatforms: String?,
    @Assisted("platforms") private val platforms: String?,
    @Assisted("stores") private val stores: String?,
    @Assisted("developers") private val developers: String?,
    @Assisted("publishers") private val publishers: String?,
    @Assisted("genres") private val genres: String?,
    @Assisted("tags") private val tags: String?,
    @Assisted("creators") private val creators: String?,
    @Assisted("dates") private val dates: String?,
    @Assisted("updated") private val updated: String?,
    @Assisted("platformsCount") private val platformsCount: Int?,
    @Assisted("metacritic") private val metacritic: String?,
    @Assisted("excludeCollection") private val excludeCollection: Int?,
    @Assisted("excludeAdditions") private val excludeAdditions: Boolean?,
    @Assisted("excludeParents") private val excludeParents: Boolean?,
    @Assisted("excludeGameSeries") private val excludeGameSeries: Boolean?,
    @Assisted("excludeStores") private val excludeStores: String?,
    @Assisted("ordering") private val ordering: String?
) : PagingSource<Int, Game>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Game> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = remoteDataSource.getGames(
                page = nextPageNumber,
                pageSize = pageSize ?: DEFAULT_PAGE_SIZE,
                ordering = ordering,
                search = search,
                searchPrecise = searchPrecise,
                searchExact = searchExact,
                parentPlatforms = parentPlatforms,
                platforms = platforms,
                platformsCount = platformsCount,
                creators = creators,
                developers = developers,
                publishers = publishers,
                genres = genres,
                tags = tags,
                stores = stores,
                dates = dates,
                updated = updated,
                metacritic = metacritic,
                excludeStores = excludeStores,
                excludeCollection = excludeCollection,
                excludeAdditions = excludeAdditions,
                excludeParents = excludeParents,
                excludeGameSeries = excludeGameSeries
            )
            LoadResult.Page(
                data = response?.toGames() ?: emptyList(),
                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                nextKey = if (response?.next == null) null else nextPageNumber + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Game>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val DEFAULT_PAGE_SIZE = 20
    }
}
