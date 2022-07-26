package com.vron.cstv.match_list.domain.usecase

import com.vron.cstv.match_list.domain.model.Match
import com.vron.cstv.match_list.domain.repository.MatchRepository

class GetMatchList(
    private val matchRepository: MatchRepository
) {
    suspend fun execute(): Result<List<Match>> {
        return matchRepository.getMatches()
    }
}