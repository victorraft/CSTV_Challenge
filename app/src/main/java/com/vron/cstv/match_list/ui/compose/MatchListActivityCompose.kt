package com.vron.cstv.match_list.ui.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.vron.cstv.R
import com.vron.cstv.common.domain.fakes.buildFakeMatches
import com.vron.cstv.common.ui.compose.theme.CSTVTheme
import com.vron.cstv.match_list.presentation.MatchListViewModel
import com.vron.cstv.match_list.presentation.ViewState
import com.vron.cstv.match_list.ui.compose.list.InfiniteListHandler
import com.vron.cstv.match_list.ui.compose.list.MatchList
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
    val listState = rememberLazyListState()
    val viewState by viewModel.viewState.observeAsState(ViewState())

    MatchListScreen(
        viewState = viewState,
        listState = listState,
        onErrorClick = viewModel::loadMoreItems
    )

    InfiniteListHandler(
        listState = listState,
        buffer = END_OF_LIST_ITEM_COUNT,
        onLoadMore = viewModel::loadMoreItems
    )
}

@Composable
fun MatchListScreen(
    viewState: ViewState,
    listState: LazyListState = rememberLazyListState(),
    onErrorClick: () -> Unit = {}
) {
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(Modifier.fillMaxSize()) {
            Text(
                text = stringResource(id = R.string.match_list_screen_title),
                fontSize = 32.sp,
                lineHeight = 40.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.match_item_list_spacing))
            )

            val (showLoading, showError) = when {
                viewState.matchList.isEmpty() -> false to false
                viewState.showLoading -> true to false
                viewState.showError -> false to true
                else -> false to false
            }

            MatchList(
                matches = viewState.matchList,
                showLoadingFooter = showLoading,
                showErrorFooter = showError,
                listState = listState,
                onErrorClick = onErrorClick
            )
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