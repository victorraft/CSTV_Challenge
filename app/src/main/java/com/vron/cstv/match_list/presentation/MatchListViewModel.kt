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

private const val PAGE_SIZE = 20

class MatchListViewModel(
    private val getMatchList: GetMatchList
) : ViewModel() {

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewState

    private var currentPage = 1
    private var isLoading = false
    private var didReachEndOfResults = false
    private val matchList = mutableListOf<Match>()

    init {
        refresh()
    }

    fun refresh() {
        currentPage = 1
        didReachEndOfResults = false
        matchList.clear()

        loadPage()
    }

    fun onEndOfListApproaching() {
        if (isLoading) {
            return
        }

        currentPage++
        loadPage()
    }

    private fun loadPage() {
        if (didReachEndOfResults) {
            return
        }

        isLoading = true

        viewModelScope.launch {
            _viewState.value = ViewState(matchList = matchList, showLoading = matchList.isEmpty())
            val params = GetMatchList.Params(page = currentPage, pageSize = PAGE_SIZE, dateRange = getDateRange())

            getMatchList.execute(params)
                .onSuccess { matches ->
                    onLoadedNewMatches(matches)
                    isLoading = false
                }
                .onFailure { error ->
                    onLoadingFailed(error)
                    isLoading = false
                }
        }
    }

    private fun onLoadedNewMatches(newMatches: List<Match>) {
        if (newMatches.isEmpty()) {
            didReachEndOfResults = true
        } else {
            val processedResult = (matchList + newMatches)
                .distinctBy { it.id }
                .filter { it.status != MatchStatus.CANCELLED }

            matchList.clear()
            matchList.addAll(processedResult)
        }

        _viewState.value = ViewState(matchList = matchList, showLoading = false)
    }

    private fun onLoadingFailed(error: Throwable) {
        error.printStackTrace()
        _viewState.value = ViewState(matchList = emptyList(), showLoading = false, showError = matchList.isEmpty())
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