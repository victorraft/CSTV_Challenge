package com.vron.cstv.common.domain.fakes

import com.vron.cstv.common.domain.model.*

fun buildFakeMatch(id: Int = 0) = Match(
    id = id,
    teams = listOf(
        Team(id = 3209, name = "Astralis $id", imageUrl = "https://cdn.pandascore.co/images/team/image/3209/ASTRALIS.png"),
        Team(id = 127911, name = "MASONIC $id", imageUrl = "https://cdn.pandascore.co/images/team/image/127911/t56871.png")
    ),
    league = League(name = "IEM", imageUrl = "https://cdn.pandascore.co/images/league/image/4161/500px-IEM_logo_2014.png"),
    serie = Serie(fullName = "Road to Rio: Europe Open Qualifier #1 2022"),
    status = MatchStatus.RUNNING,
    beginAt = "2022-08-16T16:09:05Z"
)

fun buildFakeMatches(page: Int = 1, pageSize: Int = 5) = List(pageSize) {
    buildFakeMatch(id = it + (pageSize * page - 1))
}