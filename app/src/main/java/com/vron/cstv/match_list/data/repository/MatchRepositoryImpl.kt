package com.vron.cstv.match_list.data.repository

import com.vron.cstv.match_list.domain.model.Match
import com.vron.cstv.match_list.domain.repository.MatchRepository


class MatchRepositoryImpl(
    private val matchDataSource: MatchDataSource
) : MatchRepository {

    override suspend fun getMatches(page: Int, pageSize: Int): Result<List<Match>> =
        matchDataSource.getMatches(page = page, pageSize = pageSize)
}