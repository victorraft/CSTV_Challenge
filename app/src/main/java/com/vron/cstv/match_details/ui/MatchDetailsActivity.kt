package com.vron.cstv.match_details.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.vron.cstv.common.domain.model.Match
import com.vron.cstv.common.domain.model.Player
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

        configureToolbar(match)

        viewModel.initialize(match)
        viewModel.viewState.observe(this, ::onViewStateChanged)
    }

    private fun configureToolbar(match: Match) {
        binding.toolbarTitle.text = "${match.league.name} - ${match.serie.fullName}"
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun onViewStateChanged(viewState: ViewState) {
        binding.loadingIndicator.isVisible = viewState.isLoading
        binding.matchAndPlayersViews.isVisible = !viewState.isLoading

        setMatchInfo(viewState.match)
        setPlayers(viewState.team1Details?.players, viewState.team2Details?.players)
    }

    private fun setMatchInfo(match: Match) {
        binding.matchTime.text = dateFormatter.formatToLocalDateTime(match.beginAt)

        val team1 = match.teams.getOrNull(0)
        val team2 = match.teams.getOrNull(1)
        binding.teamVsTeam.setTeams(team1, team2)
    }

    private fun setPlayers(team1Players: List<Player>?, team2Players: List<Player>?) {
        binding.team1Players.setPlayers(team1Players)
        binding.team2Players.setPlayers(team2Players)
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