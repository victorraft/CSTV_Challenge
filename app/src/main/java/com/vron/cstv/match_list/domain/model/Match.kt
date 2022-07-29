package com.vron.cstv.match_list.domain.model

data class Match(
    val id: Int,
    val teams: List<Team>,
    val league: League,
    val serie: Serie,
    val status: MatchStatus,
    val beginAt: String
)

enum class MatchStatus {
    FINISHED, RUNNING, NOT_STARTED, CANCELLED
}