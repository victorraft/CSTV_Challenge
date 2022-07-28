package com.vron.cstv.match_list.data.remote

import com.vron.cstv.common.runSuspendCatching
import com.vron.cstv.match_list.data.remote.dto.MatchDto
import com.vron.cstv.match_list.data.repository.MatchDataSource
import com.vron.cstv.match_list.domain.model.Match

class RemoteMatchDataSource(
    private val api: CsApi,
    private val matchMapper: MatchMapper
) : MatchDataSource {
    override suspend fun getMatches(page: Int, pageSize: Int): Result<List<Match>> =
        runSuspendCatching {
            api.getMatches(page, pageSize)
        }.map { matchDtoList ->
            matchDtoList.mapAllToDomain()
        }

    private fun List<MatchDto>.mapAllToDomain(): List<Match> = this.map(matchMapper::toDomain)
}