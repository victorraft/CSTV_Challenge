package com.vron.cstv.match_list.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.vron.cstv.R
import com.vron.cstv.common.domain.fakes.buildFakeMatch
import com.vron.cstv.common.domain.fakes.buildFakeMatches
import com.vron.cstv.common.domain.model.Match
import com.vron.cstv.common.domain.model.Team
import com.vron.cstv.common.ui.theme.CSTVTheme
import com.vron.cstv.common.ui.theme.CardShape
import com.vron.cstv.common.ui.theme.Gray

@Composable
fun MatchListItem(
    match: Match,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    ElevatedCard(
        shape = CardShape,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
    ) {
        Column {
            Text(text = match.beginAt, modifier = Modifier.align(Alignment.End))

            TeamVsTeam(
                team1 = match.teams.getOrNull(0),
                team2 = match.teams.getOrNull(1)
            )


        }
    }
}

@Composable
fun TeamVsTeam(team1: Team?, team2: Team?) {
    Row {
        TeamAvatarAndName(team1)
        Text(text = stringResource(id = R.string.match_vs))
        TeamAvatarAndName(team2)
    }
}

@Composable
fun TeamAvatarAndName(team: Team?) {
    Column {
        AsyncImage(
            model = team?.imageUrl,
            contentDescription = stringResource(id = R.string.team_logo),
            placeholder = ColorPainter(Gray),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
        )
    }
}

@Composable
fun MatchList(matches: List<Match>) {
    LazyColumn {
        items(matches) { match ->
            MatchListItem(match = match)
        }
    }
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