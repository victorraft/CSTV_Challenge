package com.vron.cstv.common.data.repository

import com.vron.cstv.common.domain.model.Match
import com.vron.cstv.common.domain.model.TeamDetails
import com.vron.cstv.common.domain.repository.MatchRepository


class MatchRepositoryImpl(
    private val matchDataSource: MatchDataSource
) : MatchRepository {

    override suspend fun getMatches(page: Int, pageSize: Int, dateRange: Pair<String, String>): Result<List<Match>> =
        matchDataSource.getMatches(page = page, pageSize = pageSize, dateRange = dateRange)

    override suspend fun getTeams(teamIds: List<Int>): Result<List<TeamDetails>> =
        matchDataSource.getTeams(teamIds = teamIds)
}