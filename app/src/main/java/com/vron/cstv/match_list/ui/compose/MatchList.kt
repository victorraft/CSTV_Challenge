package com.vron.cstv.match_list.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import coil.compose.AsyncImage
import com.vron.cstv.R
import com.vron.cstv.common.domain.fakes.buildFakeMatch
import com.vron.cstv.common.domain.fakes.buildFakeMatches
import com.vron.cstv.common.domain.model.*
import com.vron.cstv.common.presentation.DateFormatter
import com.vron.cstv.common.ui.theme.*
import org.koin.androidx.compose.get

@Composable
fun MatchList(matches: List<Match>) {
    val itemPadding = dimensionResource(id = R.dimen.match_item_list_spacing)

    LazyColumn {
        items(matches) { match ->
            MatchListItem(
                match = match,
                modifier = Modifier.padding(horizontal = itemPadding, vertical = itemPadding / 2)
            )
        }
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
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )

            Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.divider_to_team_margin)))

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

//TODO Fix vs alignment and team name overflow when it's too big.
@Composable
fun TeamVsTeam(team1: Team?, team2: Team?, modifier: Modifier = Modifier) {
    Row(modifier) {
        TeamImageAndName(team1)

        val vsHorizontalMargin = dimensionResource(id = R.dimen.vs_margin)
        Spacer(modifier = Modifier.size(vsHorizontalMargin))
        Text(
            text = stringResource(id = R.string.match_vs),
            fontSize = 12.sp,
            color = White.copy(alpha = 0.5f),
            lineHeight = 14.06.sp,
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.match_logo_size))
                .align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.size(vsHorizontalMargin))

        TeamImageAndName(team2)
    }
}

@Composable
fun TeamImageAndName(team: Team?) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TeamImage(imageUrl = team?.imageUrl)
        Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.team_name_to_logo_margin)))
        TeamName(name = team?.name ?: stringResource(id = R.string.undefined))
    }
}

@Composable
fun TeamImage(imageUrl: String?) {
    RoundImage(
        imageUrl = imageUrl,
        contentDescription = stringResource(id = R.string.team_logo),
        size = dimensionResource(id = R.dimen.match_logo_size)
    )
}

@Composable
fun TeamName(name: String?) {
    Text(
        text = name ?: stringResource(id = R.string.undefined),
        fontSize = 10.sp,
        lineHeight = 11.72.sp
    )
}

@Composable
fun LeagueAndSerieRow(league: League, serie: Serie, modifier: Modifier) {
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
    size: Dp
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        placeholder = ColorPainter(Gray),
        error = ColorPainter(Gray),
        contentScale = ContentScale.Crop,
        modifier = Modifier
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