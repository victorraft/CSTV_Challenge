package com.vron.cstv.match_details.ui.compose

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.vron.cstv.common.domain.fakes.buildFakePlayer
import com.vron.cstv.common.domain.model.Player
import com.vron.cstv.common.ui.compose.RoundImage

@Composable
fun TeamPlayer(player: Player) {
    ConstraintLayout {
        val (background, playerNickname, playerName, playerPicture) = createRefs()

        Surface(
            modifier = Modifier.constrainAs(background) {

            }
        ) {}

        RoundImage(
            imageUrl = "",
            contentDescription = "",
            size = 60.dp,
            modifier = Modifier.constrainAs(playerPicture) {

            },
        )
    }
}

@Preview
@Composable
fun PlayerPreview() {
    TeamPlayer(player = buildFakePlayer())
}