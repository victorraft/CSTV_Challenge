package com.vron.cstv.common.data.repository

import com.vron.cstv.common.domain.model.Match
import com.vron.cstv.common.domain.model.TeamDetails

interface MatchDataSource {
    suspend fun getMatches(page: Int, pageSize: Int, dateRange: Pair<String, String>): Result<List<Match>>

    suspend fun getTeams(teamIds: List<Int>): Result<List<TeamDetails>>
}