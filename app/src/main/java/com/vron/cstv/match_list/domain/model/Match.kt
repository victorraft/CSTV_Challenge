package com.vron.cstv.match_list.domain.model

data class Match(
    val id: Int,
    val teams: List<Team>,
    val league: League,
    val serie: Serie
)
