package com.vron.cstv.match_details.ui.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.vron.cstv.R
import com.vron.cstv.common.domain.fakes.buildFakePlayer
import com.vron.cstv.common.domain.model.Player
import com.vron.cstv.common.ui.compose.theme.CSTVTheme
import com.vron.cstv.common.ui.compose.theme.CardShape
import com.vron.cstv.common.ui.compose.theme.Gray
import com.vron.cstv.common.ui.compose.theme.PlayerPictureShape

@Composable
fun TeamPlayer(
    player: Player,
    isLeftSidePlayer: Boolean
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val (background, playerNickname, playerName, playerPicture) = createRefs()

        val topMargin = dimensionResource(id = R.dimen.player_background_offset_top)
        Surface(
            color = MaterialTheme.colorScheme.primary,
            shape = CardShape,
            modifier = Modifier.constrainAs(background) {
                top.linkTo(parent.top, margin = topMargin)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        ) {}

        val pictureMarginTop = dimensionResource(id = R.dimen.space_between_players)
        val pictureMarginEnd = dimensionResource(id = R.dimen.player_picture_margin_side)
        val pictureMarginBottom = dimensionResource(id = R.dimen.player_picture_margin_bottom)
        PlayerPicture(
            avatarUrl = player.imageUrl,
            modifier = Modifier
                .constrainAs(playerPicture) {
                    bottom.linkTo(background.bottom, pictureMarginBottom)
                    top.linkTo(parent.top, pictureMarginTop)
                    top.linkTo(parent.top)

                    if (isLeftSidePlayer) {
                        end.linkTo(background.end, pictureMarginEnd)
                    } else {
                        start.linkTo(background.start, pictureMarginEnd)
                    }
                }
        )

        val margin = dimensionResource(id = R.dimen.player_name_margin_side)
        Text(
            text = player.name,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.constrainAs(playerNickname) {
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent

                bottom.linkTo(playerName.top)
                top.linkTo(background.top)

                if (isLeftSidePlayer) {
                    end.linkTo(playerPicture.start, margin = margin)
                } else {
                    start.linkTo(playerPicture.end, margin = margin)
                }
            }
        )

        Text(
            text = "${player.firstName.trim()} ${player.lastName.trim()}",
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.constrainAs(playerName) {
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent

                bottom.linkTo(playerPicture.bottom)

                if (isLeftSidePlayer) {
                    end.linkTo(playerPicture.start, margin = margin)
                } else {
                    start.linkTo(playerPicture.end, margin = margin)
                }
            }
        )
    }
}

@Composable
fun PlayerPicture(avatarUrl: String?, modifier: Modifier = Modifier) {
    AsyncImage(
        model = avatarUrl,
        contentDescription = stringResource(id = R.string.player_picture),
        placeholder = ColorPainter(Gray),
        error = ColorPainter(Gray),
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(60.dp)
            .clip(PlayerPictureShape)
    )
}

@Preview
@Composable
fun PlayerLeftSidePreview() {
    CSTVTheme {
        TeamPlayer(player = buildFakePlayer(), isLeftSidePlayer = true)
    }
}

@Preview
@Composable
fun PlayerRightSidePreview() {
    CSTVTheme {
        TeamPlayer(player = buildFakePlayer(), isLeftSidePlayer = false)
    }
}