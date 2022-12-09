package com.vron.cstv.app.ui.match_details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.vron.cstv.R
import com.vron.cstv.common.domain.model.Match
import com.vron.cstv.common.domain.model.Player
import com.vron.cstv.common.presentation.DateFormatter
import com.vron.cstv.databinding.ActivityMatchDetailsBinding
import com.vron.cstv.match_details.presentation.MatchDetailsViewModel
import com.vron.cstv.match_details.presentation.ViewState
import com.vron.cstv.app.ui.match_details.player.PlayerInfoView
import com.vron.cstv.app.ui.match_details.team.TeamPlayersView
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
        configureClickListeners(match)

        viewModel.initialize(match)
        viewModel.viewState.observe(this, ::onViewStateChanged)
    }

    private fun configureClickListeners(match: Match) {
        binding.errorText.setOnClickListener {
            viewModel.initialize(match)
        }
    }

    private fun configureToolbar(match: Match) {
        binding.toolbarTitle.text = "${match.league.name} - ${match.serie.fullName}"
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun onViewStateChanged(viewState: ViewState) {
        binding.loadingIndicator.isVisible = viewState.showLoading
        binding.matchAndPlayersInfoViews.isVisible = !viewState.showLoading && !viewState.showError
        binding.errorText.isVisible = viewState.showError

        setMatchInfo(viewState.match)
        setTeamPlayers(viewState.team1Details?.players, binding.team1Players)
        setTeamPlayers(viewState.team2Details?.players, binding.team2Players)
    }

    private fun setMatchInfo(match: Match) {
        binding.matchTime.text = dateFormatter.formatToLocalDateTime(match.beginAt)

        val team1 = match.teams.getOrNull(0)
        val team2 = match.teams.getOrNull(1)
        binding.teamVsTeam.setTeams(team1, team2)
    }

    private fun setTeamPlayers(players: List<Player>?, teamPlayersView: TeamPlayersView) {
        setPlayer(players?.getOrNull(0), teamPlayersView.player1)
        setPlayer(players?.getOrNull(1), teamPlayersView.player2)
        setPlayer(players?.getOrNull(2), teamPlayersView.player3)
        setPlayer(players?.getOrNull(3), teamPlayersView.player4)
        setPlayer(players?.getOrNull(4), teamPlayersView.player5)
    }

    private fun setPlayer(player: Player?, playerInfoView: PlayerInfoView) {
        playerInfoView.playerNickname.text = player?.name ?: getText(R.string.undefined)

        val playerName = player?.let { "${it.firstName.trim()} ${it.lastName.trim()}" }
        playerInfoView.playerName.text = playerName ?: ""

        Glide.with(this)
            .load(player?.imageUrl)
            .transform(CenterCrop(), RoundedCorners(resources.getDimensionPixelSize(R.dimen.player_picture_corner_radius)))
            .into(playerInfoView.playerPicture)
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