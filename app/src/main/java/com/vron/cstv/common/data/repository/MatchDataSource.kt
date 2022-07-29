package com.vron.cstv.common.data.repository

import com.vron.cstv.common.domain.model.Match

interface MatchDataSource {
    suspend fun getMatches(
        page: Int,
        pageSize: Int,
        dateRange: Pair<String, String>
    ): Result<List<Match>>
}