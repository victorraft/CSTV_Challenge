package com.vron.cstv.match_list.domain.usecase

import com.vron.cstv.match_list.domain.model.Match
import com.vron.cstv.match_list.domain.repository.MatchRepository

class GetMatchList(
    private val matchRepository: MatchRepository
) {
    suspend fun execute(page: Int, pageSize: Int): Result<List<Match>> {
        return matchRepository.getMatches(page = page, pageSize = pageSize)
    }
}