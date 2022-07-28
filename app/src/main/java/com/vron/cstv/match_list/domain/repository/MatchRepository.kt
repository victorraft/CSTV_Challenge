package com.vron.cstv.match_list.domain.repository

import com.vron.cstv.match_list.domain.model.Match

interface MatchRepository {
    suspend fun getMatches(page: Int, pageSize: Int): Result<List<Match>>
}