package com.vron.cstv.match_details.domain.usecase

import com.vron.cstv.common.domain.model.TeamDetails
import com.vron.cstv.common.domain.repository.MatchRepository

class GetTeamsDetailsList(
    private val matchRepository: MatchRepository
) {

    suspend fun execute(teamIds: List<Int>): Result<List<TeamDetails>> =
        matchRepository.getTeams(teamIds = teamIds)
}