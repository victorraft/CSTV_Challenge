package com.vron.cstv.common.data.remote

import com.vron.cstv.common.data.remote.dto.MatchDto
import com.vron.cstv.common.data.remote.dto.TeamDetailsDto
import com.vron.cstv.common.data.remote.mapper.MatchMapper
import com.vron.cstv.common.data.remote.mapper.TeamDetailsMapper
import com.vron.cstv.common.data.repository.MatchDataSource
import com.vron.cstv.common.domain.model.Match
import com.vron.cstv.common.domain.model.TeamDetails
import com.vron.cstv.common.utils.runSuspendCatching

private const val FIELD_BEGIN_AT = "begin_at"

class RemoteMatchDataSource(
    private val api: CsApi,
    private val matchMapper: MatchMapper,
    private val teamDetailsMapper: TeamDetailsMapper
) : MatchDataSource {

    override suspend fun getMatches(
        page: Int,
        pageSize: Int,
        dateRange: Pair<String, String>
    ): Result<List<Match>> =
        runSuspendCatching {
            api.getMatches(
                page = page,
                pageSize = pageSize,
                sort = FIELD_BEGIN_AT,
                beginAt = "${dateRange.first},${dateRange.second}"
            )
        }.map { matchDtoList -> mapAllMatchesToDomain(matchDtoList) }

    override suspend fun getTeams(teamIds: List<Int>): Result<List<TeamDetails>> {
        return runSuspendCatching {
            api.getTeams(teamIds = teamIds.joinToString())
        }.map { teamDtoList ->
            mapAllTeamsToDomain(teamDtoList)
        }
    }

    private fun mapAllMatchesToDomain(matchDtos: List<MatchDto>): List<Match> =
        matchDtos.map(matchMapper::toDomain)

    private fun mapAllTeamsToDomain(teamDetailsDtos: List<TeamDetailsDto>): List<TeamDetails> =
        teamDetailsDtos.map(teamDetailsMapper::toDomain)
}