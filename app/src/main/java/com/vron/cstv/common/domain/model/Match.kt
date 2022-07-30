package com.vron.cstv.common.domain.model

import java.io.Serializable

data class Match(
    val id: Int,
    val teams: List<Team>,
    val league: League,
    val serie: Serie,
    val status: MatchStatus,
    val beginAt: String
) : Serializable