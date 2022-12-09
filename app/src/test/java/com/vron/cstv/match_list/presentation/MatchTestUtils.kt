package com.vron.cstv.match_list.presentation

import com.vron.cstv.common.domain.fakes.buildFakeMatches
import com.vron.cstv.common.domain.model.Match
import com.vron.cstv.match_list.domain.usecase.GetMatchList
import java.io.IOException

private const val pageSize = 20
val fakeMatchesPage1 = buildFakeMatches(1, pageSize)
val fakeMatchesPage2 = buildFakeMatches(2, pageSize)

class GetMatchListFakeImpl(
    var returnFailure: Boolean = false
) : GetMatchList {

    var timesCalled = 0
        private set

    override suspend fun execute(params: GetMatchList.Params): Result<List<Match>> {
        timesCalled++

        return when {
            returnFailure -> Result.failure(IOException())
            params.page == 1 -> Result.success(fakeMatchesPage1)
            else -> Result.success(fakeMatchesPage2)
        }
    }
}