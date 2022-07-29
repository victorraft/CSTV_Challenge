package com.vron.cstv.match_list.domain.usecase

import com.vron.cstv.match_list.domain.model.Match
import com.vron.cstv.match_list.domain.repository.MatchRepository

class GetMatchList(
    private val matchRepository: MatchRepository
) {
    data class Params(
        val page: Int,
        val pageSize: Int,
        val dateRange: Pair<String, String>
    )

    suspend fun execute(params: Params): Result<List<Match>> =
        matchRepository.getMatches(page = params.page, pageSize = params.pageSize, dateRange = params.dateRange)
}