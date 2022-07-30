package com.vron.cstv.match_details.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.vron.cstv.common.domain.model.Match
import com.vron.cstv.common.domain.model.TeamDetails
import com.vron.cstv.common.presentation.DateFormatter
import com.vron.cstv.databinding.ActivityMatchDetailsBinding
import com.vron.cstv.match_details.presentation.MatchDetailsViewModel
import com.vron.cstv.match_details.presentation.ViewState
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MatchDetailsActivity : AppCompatActivity() {

    private val viewModel: MatchDetailsViewModel by viewModel()
    private val dateFormatter: DateFormatter by inject()

    private lateinit var binding: ActivityMatchDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val match = intent?.extras?.get(BUNDLE_KEY_MATCH) as? Match
        if (match == null) {
            finish()
            return
        }

        viewModel.initialize(match)
        viewModel.viewState.observe(this, ::onViewStateChanged)
    }

    private fun onViewStateChanged(viewState: ViewState) {
        binding.loadingIndicator.isVisible = viewState.isLoading
        binding.matchAndPlayersViews.isVisible = !viewState.isLoading

        val (team1, team2) = viewState.match.teams.getOrNull(0) to viewState.match.teams.getOrNull(1)
        binding.teamVsTeam.setTeams(team1, team2)
        setPlayers(viewState.team1Details, viewState.team2Details)
    }

    private fun setPlayers(team1: TeamDetails?, team2: TeamDetails?) {
        val team1Players = team1?.players
        binding.team1Players.isVisible = team1Players != null
        if (team1Players != null) {
            binding.team1Players.setPlayers(team1Players)
        }

        val team2Players = team2?.players
        binding.team2Players.isVisible = team2Players != null
        if (team2Players != null) {
            binding.team2Players.setPlayers(team2Players)
        }
    }

    companion object {
        private const val BUNDLE_KEY_MATCH = "match"

        fun buildIntent(context: Context, match: Match): Intent {
            val intent = Intent(context, MatchDetailsActivity::class.java)
            intent.putExtra(BUNDLE_KEY_MATCH, match)
            return intent
        }
    }
}