package com.vron.cstv.match_list.domain.model

data class Match(
    val id: Int,
    val team1: Team,
    val team2: Team,
    val league: League,
    val series: Series
)
