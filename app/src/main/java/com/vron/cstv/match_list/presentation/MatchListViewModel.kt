package com.vron.cstv.match_list.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.vron.cstv.common.domain.model.Match
import com.vron.cstv.match_list.domain.usecase.GetMatchList
import com.vron.cstv.match_list.ui.recycler.paging.MatchPagingDataSource
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.YEAR

private const val API_PAGE_SIZE = 20
private const val PREFETCH_DISTANCE = 5

class MatchListViewModel(
    private val getMatchList: GetMatchList
) : ViewModel() {

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewState

    val matchPagingData = getPagingData()

    private fun getPagingData(): LiveData<PagingData<Match>> {
        val apiDateRange = getDateRange()
        val pagingConfig = PagingConfig(pageSize = API_PAGE_SIZE, prefetchDistance = PREFETCH_DISTANCE)
        val pager = Pager(
            config = pagingConfig,
            pagingSourceFactory = { MatchPagingDataSource(apiDateRange, getMatchList) }
        )
        return pager.liveData
    }

    private fun getDateRange(): Pair<String, String> {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")

        val today = Calendar.getInstance()
        val todayDateString = dateFormat.format(today.time)

        val finalDate = Calendar.getInstance().apply { add(YEAR, 1) }
        val finalDateString = dateFormat.format(finalDate.time)

        return Pair(todayDateString, finalDateString)
    }
}