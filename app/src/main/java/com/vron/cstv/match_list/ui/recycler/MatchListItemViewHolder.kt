package com.vron.cstv.match_list.ui.recycler

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.vron.cstv.R
import com.vron.cstv.databinding.MatchListItemBinding
import com.vron.cstv.match_list.domain.model.Match
import com.vron.cstv.match_list.domain.model.Team

class MatchListItemViewHolder(
    private val binding: MatchListItemBinding,
    private val onItemClicked: (Match) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var currentItem: Match

    init {
        binding.root.setOnClickListener {
            onItemClicked(currentItem)
        }
    }

    fun bind(item: Match) {
        currentItem = item

        setupBothTeams()
        setupTimeLabel()
        setupLeagueAndSerie()
    }

    private fun setupBothTeams() {
        val team1 = currentItem.teams.getOrNull(0)
        val team2 = currentItem.teams.getOrNull(1)

        setupTeamInfo(team1, binding.team1Name, binding.team1Logo)
        setupTeamInfo(team2, binding.team2Name, binding.team2Logo)
    }

    private fun setupTeamInfo(team: Team?, teamNameTextView: TextView, teamLogoImageView: ImageView) {
        teamNameTextView.text = team?.name ?: binding.root.resources.getText(R.string.team_undefined)
        loadImage(team?.imageUrl, teamLogoImageView)
    }

    private fun setupTimeLabel() {
        val isHappeningNow = currentItem.id == 0
        val matchTimeBackgroundResource = when {
            isHappeningNow -> R.drawable.match_item_time_now_background
            else -> R.drawable.match_item_time_background
        }
        binding.matchTime.setBackgroundResource(matchTimeBackgroundResource)
        binding.matchTime.text = "AGORA"
    }

    private fun setupLeagueAndSerie() {
        binding.leagueAndSerie.text = "${currentItem.league.name} - ${currentItem.serie.fullName}"
        loadImage(currentItem.league.imageUrl, binding.leagueLogo)
    }

    private fun loadImage(url: String?, imageView: ImageView) {
        Glide.with(imageView)
            .load(url)
            .apply(RequestOptions.circleCropTransform())
            .into(imageView)
    }
}