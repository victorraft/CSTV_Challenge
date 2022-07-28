package com.vron.cstv.match_list.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vron.cstv.common.API_PAGE_SIZE
import com.vron.cstv.match_list.domain.usecase.GetMatchList
import kotlinx.coroutines.launch

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

            getMatchList.execute(page = 0, pageSize = API_PAGE_SIZE)
                .onSuccess { matches ->
                    _viewState.value = ViewState(matchList = matches, isLoading = false)
                }.onFailure { error ->
                    error.printStackTrace()
                    _viewState.value = ViewState(matchList = emptyList(), isLoading = false)
                }
        }
    }
}