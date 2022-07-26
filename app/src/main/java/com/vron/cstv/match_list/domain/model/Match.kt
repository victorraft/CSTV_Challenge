package com.vron.cstv.match_list.domain.model

data class Match(
    val id: Int,
    val teamA: Team,
    val teamB: Team,
    val league: League,
    val series: Series
)

