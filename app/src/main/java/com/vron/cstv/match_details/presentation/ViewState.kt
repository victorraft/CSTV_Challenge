package com.vron.cstv.match_details.presentation

import com.vron.cstv.common.domain.model.Match
import com.vron.cstv.common.domain.model.TeamDetails

data class ViewState(
    val isLoading: Boolean,
    val match: Match,
    val team1: TeamDetails? = null,
    val team2: TeamDetails? = null
)