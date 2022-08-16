package com.vron.cstv.match_list.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.vron.cstv.R
import com.vron.cstv.common.domain.fakes.buildFakeMatch
import com.vron.cstv.common.domain.fakes.buildFakeMatches
import com.vron.cstv.common.domain.model.Match
import com.vron.cstv.common.domain.model.MatchStatus
import com.vron.cstv.common.domain.model.Team
import com.vron.cstv.common.presentation.DateFormatter
import com.vron.cstv.common.ui.theme.*
import org.koin.androidx.compose.get

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
            Modifier
                .fillMaxWidth()
                .clickable { onClick() }) {

            MatchTimeLabel(match, modifier = Modifier.align(Alignment.End))

            TeamVsTeam(
                team1 = match.teams.getOrNull(0),
                team2 = match.teams.getOrNull(1),
                modifier = Modifier.align(Alignment.CenterHorizontally),
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
        Text(text = text, modifier = Modifier.padding(dimensionResource(id = R.dimen.match_time_padding)))
    }
}

@Composable
fun TeamVsTeam(team1: Team?, team2: Team?, modifier: Modifier = Modifier) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        TeamAvatarAndName(team1)
        Text(text = stringResource(id = R.string.match_vs))
        TeamAvatarAndName(team2)
    }
}

@Composable
fun TeamAvatarAndName(team: Team?) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            model = team?.imageUrl,
            contentDescription = stringResource(id = R.string.team_logo),
            placeholder = ColorPainter(Gray),
            error = ColorPainter(Gray),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.match_logo_size))
                .clip(CircleShape)
        )

        val name = team?.name ?: stringResource(id = R.string.undefined)
        Text(text = name)
    }
}

@Composable
fun MatchList(matches: List<Match>) {
    val padding = dimensionResource(id = R.dimen.match_item_list_spacing)

    LazyColumn {
        items(matches) { match ->
            MatchListItem(
                match = match,
                modifier = Modifier.padding(horizontal = padding, vertical = padding / 2)
            )
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