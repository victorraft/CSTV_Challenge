package com.vron.cstv.match_list.ui.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.vron.cstv.R
import com.vron.cstv.common.domain.fakes.buildFakeMatches
import com.vron.cstv.common.ui.theme.CSTVTheme
import com.vron.cstv.match_list.presentation.MatchListViewModel
import com.vron.cstv.match_list.presentation.ViewState
import kotlinx.coroutines.*
import org.koin.androidx.compose.getViewModel
import kotlin.coroutines.CoroutineContext

private const val SPLASHSCREEN_DURATION_MS = 1500L
private const val END_OF_LIST_ITEM_COUNT = 5

class MatchListActivityCompose : ComponentActivity(), CoroutineScope {

    override val coroutineContext: CoroutineContext
            by lazy { Dispatchers.Default + SupervisorJob() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureSplashScreen()

        setContent {
            CSTVTheme {
                MatchListScreen()
            }
        }
    }

    private fun configureSplashScreen() {
        val splashScreen = installSplashScreen()
        var shouldDisplaySplash = true

        splashScreen.setKeepOnScreenCondition { shouldDisplaySplash }

        launch {
            delay(SPLASHSCREEN_DURATION_MS)
            shouldDisplaySplash = false
        }
    }
}

@Composable
fun MatchListScreen() {
    val viewModel = getViewModel<MatchListViewModel>()
    val viewState by viewModel.viewState.observeAsState()

    MatchListScreen(viewState = viewState ?: ViewState())
}

@Composable
fun MatchListScreen(viewState: ViewState) {
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(Modifier.fillMaxSize()) {
            Text(text = stringResource(id = R.string.match_list_screen_title))

            MatchList(viewState.matchList)
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    CSTVTheme {
        MatchListScreen(ViewState(matchList = buildFakeMatches()))
    }
}