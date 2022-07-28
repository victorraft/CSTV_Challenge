package com.vron.cstv.match_list.data.remote

import com.vron.cstv.match_list.data.repository.MatchDataSource
import com.vron.cstv.match_list.domain.model.League
import com.vron.cstv.match_list.domain.model.Match
import com.vron.cstv.match_list.domain.model.Series
import com.vron.cstv.match_list.domain.model.Team
import kotlinx.coroutines.delay

class FakeMatchDataSource() : MatchDataSource {
    private val team = Team(
        name = "Team 1",
        imageUrl = "https://i.pinimg.com/564x/96/bd/a9/96bda99439bb61f5e9c9c08439325622.jpg"
    )
    private val league = League(
        name = "League 1",
        imageUrl = "https://w7.pngwing.com/pngs/140/598/png-transparent-eleague-major-boston-2018-counter-strike-global-offensive-cs-go-eleague-grand-finals-counter-miscellaneous-logo-video-game.png"
    )
    private val serie = Series(name = "Series 2022")

    override suspend fun getMatches(page: Int, pageSize: Int): Result<List<Match>> {
        delay(1000)

        return Result.success(
            List(20) {
                Match(
                    id = it,
                    team1 = team,
                    team2 = team.copy(name = "Team 2"),
                    league = league,
                    series = serie
                )
            }
        )
    }
}