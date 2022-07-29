package com.vron.cstv.common.data.remote

import com.vron.cstv.common.data.remote.dto.*
import com.vron.cstv.common.domain.model.*

class MatchMapper {
    fun toDomain(matchDto: MatchDto): Match {
        return Match(
            id = matchDto.id,
            teams = matchDto.opponents.mapNotNull(::mapOpponent),
            league = matchDto.league.let(::mapLeague),
            serie = matchDto.serie.let(::mapSerie),
            status = mapStatus(matchDto.status),
            beginAt = matchDto.beginAt.orEmpty()
        )
    }

    private fun mapOpponent(opponentDto: OpponentDto): Team? =
        when {
            opponentDto.opponent == null -> null
            opponentDto.type != OpponentTypeDto.TEAM -> null
            else -> Team(
                id = opponentDto.opponent.id,
                name = opponentDto.opponent.name,
                imageUrl = opponentDto.opponent.image_url.orEmpty()
            )
        }

    private fun mapLeague(leagueDto: LeagueDto): League =
        League(name = leagueDto.name, imageUrl = leagueDto.image_url)

    private fun mapSerie(serieDto: SerieDto): Serie =
        Serie(fullName = serieDto.fullName)

    private fun mapStatus(matchStatus: MatchStatusDto): MatchStatus =
        when (matchStatus) {
            MatchStatusDto.RUNNING -> MatchStatus.RUNNING
            MatchStatusDto.FINISHED -> MatchStatus.FINISHED
            MatchStatusDto.CANCELLED -> MatchStatus.CANCELLED
            MatchStatusDto.NOT_STARTED, MatchStatusDto.POSTPONED -> MatchStatus.NOT_STARTED
        }
}