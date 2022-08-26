package com.vron.cstv.match_list.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.vron.cstv.R
import com.vron.cstv.common.domain.fakes.buildFakeMatch
import com.vron.cstv.common.domain.fakes.buildFakeMatches
import com.vron.cstv.common.domain.model.*
import com.vron.cstv.common.presentation.DateFormatter
import com.vron.cstv.common.ui.theme.*
import org.koin.androidx.compose.get

@Composable
fun MatchList(
    matches: List<Match>,
    showLoadingFooter: Boolean = false,
    showErrorFooter: Boolean = false,
    listState: LazyListState = rememberLazyListState(),
) {
    val itemPadding = dimensionResource(id = R.dimen.match_item_list_spacing)
    val itemPaddingModifier = Modifier.padding(horizontal = itemPadding, vertical = itemPadding / 2)

    LazyColumn(state = listState) {
        items(matches) { match ->
            MatchListItem(
                match = match,
                modifier = itemPaddingModifier
            )
        }

        if (showLoadingFooter) {
            item {
                ListLoadingItem(modifier = itemPaddingModifier)
            }
        } else if (showErrorFooter) {
            item {
                ListErrorItem(modifier = itemPaddingModifier)
            }
        }
    }
}

@Composable
fun ListLoadingItem(modifier: Modifier = Modifier) {
    ElevatedCard(
        shape = CardShape,
        modifier = modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.loading_footer_height))
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Composable
fun ListErrorItem(modifier: Modifier = Modifier) {
    ElevatedCard(
        shape = CardShape,
        modifier = modifier.fillMaxWidth()
    ) {
        // TODO
    }
}

@Composable
fun MatchListItem(
    match: Match,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    ElevatedCard(
        shape = CardShape,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
        ) {
            MatchTimeLabel(match, modifier = Modifier.align(Alignment.End))

            TeamVsTeam(
                team1 = match.teams.getOrNull(0),
                team2 = match.teams.getOrNull(1),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = dimensionResource(id = R.dimen.team_vs_team_vertical_margin)),
            )

            Divider(color = White.copy(alpha = 0.2f))

            LeagueAndSerieRow(
                match.league,
                match.serie,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 8.dp)
            )
        }
    }
}

@Composable
fun MatchTimeLabel(
    match: Match,
    dateFormatter: DateFormatter = get(),
    modifier: Modifier = Modifier
) {
    val color = if (match.status == MatchStatus.RUNNING) Red else Color(0x33FAFAFA)
    val text = when (match.status) {
        MatchStatus.RUNNING -> stringResource(id = R.string.time_label_now)
        else -> dateFormatter.formatToLocalDateTime(match.beginAt)
    }

    Surface(
        shape = TimeLabelShape,
        color = color,
        modifier = modifier
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.match_time_padding)),
            fontSize = 8.sp,
            lineHeight = 9.38.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

//TODO Fix team name overflow when it's too big.
@Composable
fun TeamVsTeam(team1: Team?, team2: Team?, modifier: Modifier = Modifier) {
    ConstraintLayout(modifier = modifier.fillMaxWidth()) {
        val (team1Image, team2Image, team1Name, team2Name, vs) = createRefs()

        val teamImageToNameMargin = dimensionResource(id = R.dimen.team_name_to_logo_margin)
        val vsMargin = dimensionResource(id = R.dimen.vs_margin)

        createHorizontalChain(team1Image, vs, team2Image, chainStyle = ChainStyle.Packed)

        TeamImage(
            imageUrl = team1?.imageUrl,
            modifier = Modifier.constrainAs(team1Image) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
        )

        TeamName(
            name = team1?.name,
            modifier = Modifier.constrainAs(team1Name) {
                top.linkTo(team1Image.bottom, teamImageToNameMargin)
                end.linkTo(team1Image.end)
                start.linkTo(team1Image.start)
            }
        )

        Text(
            text = stringResource(id = R.string.match_vs),
            fontSize = 12.sp,
            color = White.copy(alpha = 0.5f),
            lineHeight = 14.06.sp,
            modifier = Modifier
                .padding(vsMargin)
                .constrainAs(vs) {
                    top.linkTo(team1Image.top)
                    bottom.linkTo(team1Image.bottom)
                }
        )

        TeamImage(
            imageUrl = team2?.imageUrl,
            modifier = Modifier.constrainAs(team2Image) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            }
        )

        TeamName(
            name = team2?.name,
            modifier = Modifier.constrainAs(team2Name) {
                top.linkTo(team2Image.bottom, teamImageToNameMargin)
                start.linkTo(team2Image.start)
                end.linkTo(team2Image.end)
            }
        )
    }
}

@Composable
fun TeamImage(imageUrl: String?, modifier: Modifier) {
    RoundImage(
        imageUrl = imageUrl,
        contentDescription = stringResource(id = R.string.team_logo),
        size = dimensionResource(id = R.dimen.match_logo_size),
        modifier = modifier
    )
}

@Composable
fun TeamName(name: String?, modifier: Modifier = Modifier) {
    Text(
        text = name ?: stringResource(id = R.string.undefined),
        fontSize = 10.sp,
        lineHeight = 11.72.sp,
        modifier = modifier
    )
}

@Composable
fun LeagueAndSerieRow(league: League, serie: Serie, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        RoundImage(
            imageUrl = league.imageUrl,
            contentDescription = stringResource(id = R.string.league_logo),
            size = dimensionResource(id = R.dimen.league_logo_size)
        )

        Spacer(modifier = Modifier.size(8.dp))

        Text(
            text = "${league.name} - ${serie.fullName}",
            fontSize = 8.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 9.38.sp
        )
    }
}

@Composable
fun RoundImage(
    imageUrl: String?,
    contentDescription: String,
    size: Dp,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        placeholder = ColorPainter(Gray),
        error = ColorPainter(Gray),
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(size)
            .clip(CircleShape)
    )
}

@Preview
@Composable
fun MatchListItemPreview() {
    CSTVTheme {
        MatchListItem(match = buildFakeMatch())
    }
}

@Preview
@Composable
fun MatchListPreview() {
    CSTVTheme {
        MatchList(matches = buildFakeMatches())
    }
}