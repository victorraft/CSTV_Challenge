package com.vron.cstv.match_details.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.vron.cstv.common.domain.fakes.buildFakePlayer
import com.vron.cstv.common.domain.model.Player
import com.vron.cstv.common.ui.compose.theme.CSTVTheme

@Composable
fun TeamPlayers(
    players: List<Player>,
    isLeftSideTeam: Boolean = true
) {
    Column {
        players.forEach {
            TeamPlayer(player = it, isLeftSidePlayer = isLeftSideTeam)
        }
    }
}

@Preview
@Composable
fun TeamPlayersPreview() {
    val players = List(5) { buildFakePlayer() }
    CSTVTheme {
        TeamPlayers(players = players)
    }
}