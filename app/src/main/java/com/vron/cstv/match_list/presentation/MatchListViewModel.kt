package com.vron.cstv.match_list.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vron.cstv.common.API_PAGE_SIZE
import com.vron.cstv.common.domain.model.MatchStatus
import com.vron.cstv.match_list.domain.usecase.GetMatchList
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.YEAR

class MatchListViewModel(
    private val getMatchList: GetMatchList
) : ViewModel() {

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewState

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _viewState.value = ViewState(matchList = emptyList(), isLoading = true)
            val params = GetMatchList.Params(page = 1, pageSize = API_PAGE_SIZE, dateRange = getDateRange())

            getMatchList.execute(params)
                .onSuccess { matches ->
                    val processedResult = matches.filter { it.status != MatchStatus.CANCELLED }
                    _viewState.value = ViewState(matchList = processedResult, isLoading = false)
                }.onFailure { error ->
                    error.printStackTrace()
                    _viewState.value = ViewState(matchList = emptyList(), isLoading = false)
                }
        }
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