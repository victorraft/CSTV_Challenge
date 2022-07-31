package com.vron.cstv.match_details.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vron.cstv.common.domain.model.Match
import com.vron.cstv.match_details.domain.usecase.GetTeamsDetailsList
import kotlinx.coroutines.launch

class MatchDetailsViewModel(
    private val getTeamDetails: GetTeamsDetailsList
) : ViewModel() {

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewState

    fun initialize(match: Match) {
        fetchTeamDetails(match)
    }

    private fun fetchTeamDetails(match: Match) {
        viewModelScope.launch {
            val teamIds = match.teams.map { it.id }
            if (teamIds.isEmpty()) {
                _viewState.value = ViewState(match = match)
                return@launch
            }

            _viewState.value = ViewState(showLoading = true, match = match)

            getTeamDetails.execute(teamIds)
                .onSuccess { teams ->
                    _viewState.value = ViewState(
                        match = match,
                        team1Details = teams.getOrNull(0),
                        team2Details = teams.getOrNull(1)
                    )
                }.onFailure { error ->
                    error.printStackTrace()
                    _viewState.value = ViewState(match = match, showError = true)
                }
        }
    }
}