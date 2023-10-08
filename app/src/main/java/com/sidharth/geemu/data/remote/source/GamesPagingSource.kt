package com.sidharth.geemu.data.remote.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sidharth.geemu.data.mapper.ResponseMapper.toGames
import com.sidharth.geemu.domain.model.Game
import javax.inject.Inject

class GamesPagingSource @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : PagingSource<Int, Game>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Game> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = remoteDataSource.getGames(
                page = nextPageNumber,
                pageSize = 20,
                ordering = "-rating",
                metacritic = "1,100",
                excludeAdditions = true,
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
}
