package com.vron.cstv.match_list.presentation

import com.vron.cstv.common.domain.model.League
import com.vron.cstv.common.domain.model.Match
import com.vron.cstv.common.domain.model.MatchStatus
import com.vron.cstv.common.domain.model.Serie
import com.vron.cstv.match_list.domain.usecase.GetMatchList
import java.io.IOException

private val pageSize = 20
val fakeMatchesPage1 = buildFakeMatches(1)
val fakeMatchesPage2 = buildFakeMatches(2)


class GetMatchListFakeImpl(
    var returnFailure: Boolean = false
) : GetMatchList {

    override suspend fun execute(params: GetMatchList.Params): Result<List<Match>> {
        return when {
            returnFailure -> Result.failure(IOException())
            params.page == 1 -> Result.success(fakeMatchesPage1)
            else -> Result.success(fakeMatchesPage2)
        }
    }
}

fun buildFakeMatches(page: Int) = List(pageSize) {
    Match(
        id = it + (pageSize * page - 1),
        teams = emptyList(),
        league = League("", ""),
        serie = Serie(""),
        status = MatchStatus.RUNNING,
        beginAt = ""
    )
}