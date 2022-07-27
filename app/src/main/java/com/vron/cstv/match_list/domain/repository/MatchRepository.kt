package com.vron.cstv.match_list.domain.repository

import com.vron.cstv.match_list.domain.model.Match

interface MatchRepository {
    suspend fun getMatches(): Result<List<Match>>
}