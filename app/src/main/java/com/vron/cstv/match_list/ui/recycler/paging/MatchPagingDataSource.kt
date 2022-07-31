package com.vron.cstv.match_list.ui.recycler.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vron.cstv.common.domain.model.Match
import com.vron.cstv.match_list.domain.usecase.GetMatchList

class MatchPagingDataSource(
    private val dateRange: Pair<String, String>,
    private val getMatchList: GetMatchList
) : PagingSource<Int, Match>() {

    // The refresh key is used for the initial load of the next PagingSource, after invalidation
    override fun getRefreshKey(state: PagingState<Int, Match>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Match> {
        val pageNumber = params.key ?: 1

        val getMatchListParams = GetMatchList.Params(page = pageNumber, pageSize = params.loadSize, dateRange = dateRange)
        val result = getMatchList.execute(getMatchListParams)

        return result.fold(
            onSuccess = { matches ->
                LoadResult.Page(data = matches, prevKey = null, nextKey = pageNumber + 1)
            },
            onFailure = { error ->
                LoadResult.Error(error)
            }
        )
    }
}