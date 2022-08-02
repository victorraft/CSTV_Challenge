package com.vron.cstv.match_list.domain.usecase

import com.vron.cstv.common.domain.model.Match
import com.vron.cstv.common.domain.repository.MatchRepository

interface GetMatchList {
    data class Params(
        val page: Int,
        val pageSize: Int,
        val dateRange: Pair<String, String>
    )

    suspend fun execute(params: Params): Result<List<Match>>
}

class GetMatchListImpl(
    private val matchRepository: MatchRepository
) : GetMatchList {
    override suspend fun execute(params: GetMatchList.Params): Result<List<Match>> =
        matchRepository.getMatches(page = params.page, pageSize = params.pageSize, dateRange = params.dateRange)
}