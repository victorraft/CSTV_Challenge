package com.vron.cstv.match_list.data.repository

import com.vron.cstv.match_list.domain.model.Match

interface MatchDataSource {
    suspend fun getMatches(
        page: Int,
        pageSize: Int,
        dateRange: Pair<String, String>
    ): Result<List<Match>>
}