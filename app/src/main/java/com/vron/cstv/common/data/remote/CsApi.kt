package com.vron.cstv.common.data.remote

import com.vron.cstv.common.data.remote.dto.MatchDto
import com.vron.cstv.common.data.remote.dto.TeamDetailsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CsApi {
    @GET("matches")
    suspend fun getMatches(
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int,
        @Query("sort") sort: String,
        @Query("range[begin_at]") beginAt: String
    ): List<MatchDto>

    @GET("teams")
    suspend fun getTeams(
        @Query("filter[id]") teamIds: String
    ): List<TeamDetailsDto>
}