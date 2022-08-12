package com.vron.cstv.match_list.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.vron.cstv.R
import com.vron.cstv.common.presentation.DateFormatter
import com.vron.cstv.databinding.ActivityMatchListBinding
import com.vron.cstv.match_list.presentation.MatchListItem
import com.vron.cstv.match_list.presentation.MatchListItem.MatchItem
import com.vron.cstv.match_list.presentation.MatchListViewModel
import com.vron.cstv.match_list.presentation.ViewState
import com.vron.cstv.match_list.ui.recycler.MarginItemDecoration
import com.vron.cstv.match_list.ui.recycler.MatchListAdapter
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import kotlin.coroutines.CoroutineContext

private const val SPLASHSCREEN_DURATION_MS = 1500L
private const val END_OF_LIST_ITEM_COUNT = 5

class MatchListActivity : AppCompatActivity(), CoroutineScope {

    override val coroutineContext: CoroutineContext
            by lazy { Dispatchers.Default + SupervisorJob() }

    private val viewModel: MatchListViewModel by viewModel()
    private val dateFormatter: DateFormatter by inject()
    private val navigator: MatchListNavigator by inject { parametersOf(this) }

    private lateinit var binding: ActivityMatchListBinding
    private val matchListAdapter = MatchListAdapter(
        dateFormatter = dateFormatter,
        onItemClicked = ::onItemClicked,
        endOfListItemCount = END_OF_LIST_ITEM_COUNT,
        onEndOfListApproaching = ::onEndOfListApproaching
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureSplashScreen()

        binding = ActivityMatchListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureRecycler()
        configureClickListeners()

        viewModel.viewState.observe(this, ::onViewStateChanged)
    }

    private fun configureClickListeners() {
        binding.errorText.setOnClickListener {
            viewModel.refresh()
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

    private fun configureRecycler() {
        binding.matchesRecycler.apply {
            layoutManager = LinearLayoutManager(this@MatchListActivity)
            addItemDecoration(MarginItemDecoration(spaceSize = resources.getDimensionPixelSize(R.dimen.match_item_list_spacing)))
            adapter = matchListAdapter
        }
    }

    private fun onViewStateChanged(viewState: ViewState) {
        val matches = viewState.matchList
        val isEmpty = matches.isEmpty()

        binding.loadingIndicator.isVisible = isEmpty && viewState.showLoading
        binding.errorText.isVisible = isEmpty && viewState.showError
        binding.matchesRecycler.isVisible = !isEmpty

        val recyclerItems = buildList {
            addAll(matches.map { match -> MatchItem(match) })

            when {
                isEmpty -> Unit
                viewState.showLoading -> add(MatchListItem.LoadingItem)
                viewState.showError -> add(MatchListItem.ErrorItem)
            }
        }

        // To avoid adding more items while the RecyclerView is busy drawing.
        binding.matchesRecycler.post {
            matchListAdapter.setItems(recyclerItems)
        }
    }

    private fun onItemClicked(matchListItem: MatchListItem) {
        when (matchListItem) {
            is MatchItem -> navigator.openMatchDetails(matchListItem.match)
            is MatchListItem.ErrorItem -> viewModel.loadMoreItems()
            is MatchListItem.LoadingItem -> Unit
        }
    }

    private fun onEndOfListApproaching() {
        viewModel.loadMoreItems()
    }
}