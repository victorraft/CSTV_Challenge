package com.vron.cstv.app.ui.match_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.vron.cstv.R
import com.vron.cstv.common.domain.fakes.buildFakePlayer
import com.vron.cstv.common.domain.model.Player
import com.vron.cstv.app.ui.common.theme.CSTVTheme

@Composable
fun TeamPlayers(
    players: List<Player>,
    isLeftSideTeam: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier.padding(
            bottom = dimensionResource(id = R.dimen.space_between_players)
        )
    ) {
        players.forEach {
            TeamPlayer(player = it, isLeftSidePlayer = isLeftSideTeam)
        }
    }
}

@Preview
@Composable
fun TeamPlayersLeftSidePreview() {
    val players = List(5) { buildFakePlayer() }
    CSTVTheme {
        TeamPlayers(players = players, isLeftSideTeam = true)
    }
}

@Preview
@Composable
fun TeamPlayersRightSidePreview() {
    val players = List(5) { buildFakePlayer() }
    CSTVTheme {
        TeamPlayers(players = players, isLeftSideTeam = false)
    }
}