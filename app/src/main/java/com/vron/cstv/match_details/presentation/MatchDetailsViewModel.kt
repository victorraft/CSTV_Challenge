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

    private lateinit var match: Match

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewState

    fun initialize(match: Match) {
        this.match = match
        fetchTeamDetails()
    }

    fun fetchTeamDetails() {
        viewModelScope.launch {
            _viewState.value = ViewState(isLoading = true, match = match)

            val teamIds = match.teams.map { it.id }
            getTeamDetails.execute(teamIds)
                .onSuccess { teams ->
                    _viewState.value = ViewState(
                        isLoading = false,
                        match = match,
                        team1 = teams.getOrNull(0),
                        team2 = teams.getOrNull(1)
                    )
                }.onFailure { error ->
                    error.printStackTrace()
                    _viewState.value = ViewState(isLoading = false, match = match)
                }
        }
    }
}