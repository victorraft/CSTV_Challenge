package com.vron.cstv.match_list.data.remote

import com.vron.cstv.common.utils.runSuspendCatching
import com.vron.cstv.match_list.data.remote.dto.MatchDto
import com.vron.cstv.match_list.data.repository.MatchDataSource
import com.vron.cstv.match_list.domain.model.Match

private const val FIELD_BEGIN_AT = "begin_at"

class RemoteMatchDataSource(
    private val api: CsApi,
    private val matchMapper: MatchMapper
) : MatchDataSource {

    override suspend fun getMatches(
        page: Int,
        pageSize: Int,
        dateRange: Pair<String, String>
    ): Result<List<Match>> =
        runSuspendCatching {
            api.getMatches(
                page = page,
                pageSize = pageSize,
                sort = FIELD_BEGIN_AT,
                beginAt = "${dateRange.first},${dateRange.second}"
            )
        }.map { matchDtoList ->
            matchDtoList.mapAllToDomain()
        }

    private fun List<MatchDto>.mapAllToDomain(): List<Match> = this.map(matchMapper::toDomain)
}