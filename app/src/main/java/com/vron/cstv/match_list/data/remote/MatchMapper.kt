package com.vron.cstv.match_list.data.remote

import com.vron.cstv.match_list.data.remote.dto.LeagueDto
import com.vron.cstv.match_list.data.remote.dto.MatchDto
import com.vron.cstv.match_list.data.remote.dto.OpponentDto
import com.vron.cstv.match_list.data.remote.dto.SerieDto
import com.vron.cstv.match_list.domain.model.League
import com.vron.cstv.match_list.domain.model.Match
import com.vron.cstv.match_list.domain.model.Serie
import com.vron.cstv.match_list.domain.model.Team

class MatchMapper {
    fun toDomain(matchDto: MatchDto): Match = Match(
        id = matchDto.id,
        teams = matchDto.opponents.mapNotNull(::mapOpponent),
        league = matchDto.league.let(::mapLeague),
        serie = matchDto.serie.let(::mapSerie)
    )

    private fun mapOpponent(opponentDto: OpponentDto): Team? =
        when {
            opponentDto.opponent == null -> null
            opponentDto.type != "Team" -> null
            else -> Team(
                name = opponentDto.opponent.name,
                imageUrl = opponentDto.opponent.image_url.orEmpty()
            )
        }

    private fun mapLeague(leagueDto: LeagueDto): League =
        League(name = leagueDto.name, imageUrl = leagueDto.image_url)

    private fun mapSerie(serieDto: SerieDto): Serie =
        Serie(fullName = serieDto.fullName)
}