package com.vron.cstv.match_list.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vron.cstv.common.domain.model.Match
import com.vron.cstv.common.domain.model.MatchStatus
import com.vron.cstv.match_list.domain.usecase.GetMatchList
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.YEAR

private const val PAGE_SIZE = 25

class MatchListViewModel(
    private val getMatchList: GetMatchList
) : ViewModel() {

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewState

    private var nextPage = 1
    private var isLoadingNextPage = false
    private var didReachEndOfResults = false

    private val matchList = mutableListOf<Match>()

    init {
        refresh()
    }

    fun refresh() {
        nextPage = 1
        didReachEndOfResults = false
        matchList.clear()

        viewModelScope.launch {
            loadPage()
        }
    }

    fun loadMoreItems() {
        if (isLoadingNextPage) {
            return
        }

        isLoadingNextPage = true

        viewModelScope.launch {
            try {
                loadPage()
            } finally {
                isLoadingNextPage = false
            }
        }
    }

    private suspend fun loadPage() {
        if (didReachEndOfResults) {
            return
        }

        _viewState.value = ViewState(matchList = matchList, showLoading = true)

        val params = GetMatchList.Params(page = nextPage, pageSize = PAGE_SIZE, dateRange = getDateRange())
        getMatchList.execute(params)
            .onSuccess { matches -> onLoadedNewMatches(matches) }
            .onFailure { error -> onLoadingFailed(error) }
    }

    private fun onLoadedNewMatches(newMatches: List<Match>) {
        nextPage++

        if (newMatches.isEmpty()) {
            didReachEndOfResults = true
        } else {
            val processedResult = (matchList + newMatches)
                .distinctBy { it.id }
                .filter { it.status != MatchStatus.CANCELLED }

            matchList.clear()
            matchList.addAll(processedResult)
        }

        _viewState.value = ViewState(matchList = matchList)
    }

    private fun onLoadingFailed(error: Throwable) {
        error.printStackTrace()
        _viewState.value = ViewState(matchList = matchList, showError = true)
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