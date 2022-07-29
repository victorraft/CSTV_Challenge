package com.vron.cstv.common.domain.repository

import com.vron.cstv.common.domain.model.Match

interface MatchRepository {
    suspend fun getMatches(page: Int, pageSize: Int, dateRange: Pair<String, String>): Result<List<Match>>
}