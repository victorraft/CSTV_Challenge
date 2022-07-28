package com.vron.cstv.match_list.data.remote

import com.vron.cstv.match_list.data.remote.dto.MatchDto
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
}