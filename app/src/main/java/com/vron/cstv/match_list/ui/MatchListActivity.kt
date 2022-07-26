package com.vron.cstv.match_list.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.vron.cstv.databinding.ActivityMatchListBinding
import com.vron.cstv.match_list.domain.model.Match
import com.vron.cstv.match_list.presentation.MatchListViewModel
import com.vron.cstv.match_list.presentation.ViewState
import org.koin.androidx.viewmodel.ext.android.viewModel

class MatchListActivity : AppCompatActivity() {

    private val viewModel: MatchListViewModel by viewModel()

    private lateinit var binding: ActivityMatchListBinding
    private val matchListAdapter = MatchListAdapter(onItemClicked = ::onItemClicked)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureRecycler()

        viewModel.viewState.observe(this, ::onViewStateChanged)
    }

    private fun configureRecycler() {
        binding.matchesRecycler.apply {
            layoutManager = LinearLayoutManager(this@MatchListActivity)
            adapter = matchListAdapter
        }
    }

    private fun onViewStateChanged(viewState: ViewState) {
        binding.loadingIndicator.isVisible = viewState.isLoading
        binding.matchesRecycler.isVisible = !viewState.isLoading

        matchListAdapter.setItems(viewState.matchList)
    }

    private fun onItemClicked(match: Match) {
        Toast.makeText(this, "Item clicked: ${match.id}", Toast.LENGTH_SHORT).show()
    }
}