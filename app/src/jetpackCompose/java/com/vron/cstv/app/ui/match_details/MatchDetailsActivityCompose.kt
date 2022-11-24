@file:OptIn(ExperimentalMaterial3Api::class)

package com.vron.cstv.app.ui.match_details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.vron.cstv.R
import com.vron.cstv.app.ui.match_list.list.TeamVsTeam
import com.vron.cstv.common.domain.fakes.buildFakeMatch
import com.vron.cstv.common.domain.fakes.buildFakePlayer
import com.vron.cstv.common.domain.model.Match
import com.vron.cstv.common.domain.model.TeamDetails
import com.vron.cstv.common.presentation.DateFormatter
import com.vron.cstv.app.ui.common.ErrorMessage
import com.vron.cstv.app.ui.common.LoadingBar
import com.vron.cstv.app.ui.common.theme.CSTVTheme
import com.vron.cstv.match_details.presentation.MatchDetailsViewModel
import com.vron.cstv.match_details.presentation.ViewState
import org.koin.androidx.compose.getViewModel

class MatchDetailsActivityCompose : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val match = intent?.extras?.get(BUNDLE_KEY_MATCH) as? Match
        if (match == null) {
            finish()
            return
        }

        setContent {
            CSTVTheme {
                MatchDetailsScreen(match, onBackArrowClick = { finish() })
            }
        }
    }

    companion object {
        private const val BUNDLE_KEY_MATCH = "match"

        fun buildIntent(context: Context, match: Match): Intent {
            val intent = Intent(context, MatchDetailsActivityCompose::class.java)
            intent.putExtra(BUNDLE_KEY_MATCH, match)
            return intent
        }
    }
}

@Composable
fun MatchDetailsScreen(match: Match, onBackArrowClick: () -> Unit) {
    val viewModel = getViewModel<MatchDetailsViewModel>()
    val viewState by viewModel.viewState.observeAsState(ViewState(match))

    LaunchedEffect(match) {
        viewModel.initialize(match)
    }

    MatchDetailsScreen(
        viewState = viewState,
        onBackArrowClick = onBackArrowClick,
        onErrorClick = { viewModel.initialize(match) }
    )
}

@Composable
fun MatchDetailsScreen(
    viewState: ViewState,
    onBackArrowClick: () -> Unit = {},
    onErrorClick: () -> Unit = {},
) {
    val match = viewState.match
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            MatchDetailsAppbar(
                title = "${match.league.name} - ${match.serie.fullName}",
                onBackArrowClick = { onBackArrowClick() },
            )
        }
    ) { padding ->
        when {
            viewState.showLoading -> LoadingBar(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            )

            viewState.showError -> ErrorMessage(
                text = stringResource(id = R.string.error_text),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clickable { onErrorClick() }
            )

            else -> MatchDetailsLoaded(
                match = match,
                team1Details = viewState.team1Details,
                team2Details = viewState.team2Details,
                modifier = Modifier
                    .padding(padding)
                    .verticalScroll(scrollState)
            )
        }
    }
}

@Composable
fun MatchDetailsLoaded(
    match: Match,
    team1Details: TeamDetails? = null,
    team2Details: TeamDetails? = null,
    modifier: Modifier = Modifier
) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        TeamVsTeam(
            team1 = match.teams.getOrNull(0),
            team2 = match.teams.getOrNull(1),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = dimensionResource(id = R.dimen.team_vs_team_vertical_margin)),
        )

        MatchTime(match)

        Row {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if (team1Details != null) {
                    TeamPlayers(
                        players = team1Details.players,
                        isLeftSideTeam = true
                    )
                }
            }

            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_between_players)))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if (team2Details != null) {
                    TeamPlayers(
                        players = team2Details.players,
                        isLeftSideTeam = false
                    )
                }
            }
        }
    }
}

@Composable
fun MatchDetailsAppbar(
    title: String,
    onBackArrowClick: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
            )
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        navigationIcon = {
            IconButton(onClick = onBackArrowClick) {
                Icon(Icons.Filled.ArrowBack, "Back")
            }
        },
    )
}

@Composable
fun MatchTime(
    match: Match,
    dateFormatter: DateFormatter = DateFormatter(LocalContext.current),
) {
    Text(
        text = dateFormatter.formatToLocalDateTime(match.beginAt),
        fontSize = 12.sp,
        lineHeight = 14.sp,
        fontWeight = FontWeight.Bold
    )
}

@Preview
@Composable
fun MatchDetailsPreview() {
    val players = List(5) { buildFakePlayer() }

    CSTVTheme {
        MatchDetailsScreen(
            ViewState(
                match = buildFakeMatch(),
                team1Details = TeamDetails(123, players),
                team2Details = TeamDetails(456, players),
            )
        )
    }
}

@Preview
@Composable
fun MatchDetailsSingleTeamPreview() {
    val players = List(5) { buildFakePlayer() }

    CSTVTheme {
        MatchDetailsScreen(
            ViewState(
                match = buildFakeMatch(),
                team1Details = TeamDetails(123, players),
            )
        )
    }
}

@Preview
@Composable
fun MatchDetailsLoadingPreview() {
    CSTVTheme {
        MatchDetailsScreen(
            ViewState(
                match = buildFakeMatch(),
                showLoading = true
            )
        )
    }
}

@Preview
@Composable
fun MatchDetailsErrorPreview() {
    CSTVTheme {
        MatchDetailsScreen(
            ViewState(
                match = buildFakeMatch(),
                showError = true
            )
        )
    }
}