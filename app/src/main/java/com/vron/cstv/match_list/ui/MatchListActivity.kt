package com.vron.cstv.match_list.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.vron.cstv.R
import com.vron.cstv.common.domain.model.Match
import com.vron.cstv.common.presentation.DateFormatter
import com.vron.cstv.databinding.ActivityMatchListBinding
import com.vron.cstv.match_list.presentation.MatchListViewModel
import com.vron.cstv.match_list.presentation.ViewState
import com.vron.cstv.match_list.ui.recycler.MarginItemDecoration
import com.vron.cstv.match_list.ui.recycler.MatchListAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MatchListActivity : AppCompatActivity() {

    private val viewModel: MatchListViewModel by viewModel()
    private val dateFormatter: DateFormatter by inject()
    private val navigator: MatchListNavigator by inject { parametersOf(this) }

    private lateinit var binding: ActivityMatchListBinding
    private val matchListAdapter = MatchListAdapter(dateFormatter = dateFormatter, onItemClicked = ::onItemClicked)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        binding = ActivityMatchListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureRecycler()

        viewModel.viewState.observe(this, ::onViewStateChanged)
    }

    private fun configureRecycler() {
        binding.matchesRecycler.apply {
            layoutManager = LinearLayoutManager(this@MatchListActivity)
            addItemDecoration(MarginItemDecoration(spaceSize = resources.getDimensionPixelSize(R.dimen.match_item_list_spacing)))
            adapter = matchListAdapter
        }
    }

    private fun onViewStateChanged(viewState: ViewState) {
        binding.loadingIndicator.isVisible = viewState.isLoading
        binding.matchesRecycler.isVisible = !viewState.isLoading

        matchListAdapter.setItems(viewState.matchList)
    }

    private fun onItemClicked(match: Match) {
        navigator.openMatchDetails(match)
    }
}