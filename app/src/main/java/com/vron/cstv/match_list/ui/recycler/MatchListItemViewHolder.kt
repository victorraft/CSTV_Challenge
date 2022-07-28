package com.vron.cstv.match_list.ui.recycler

import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
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

    private var currentItem: Match? = null

    init {
        binding.root.setOnClickListener {
            currentItem?.let(onItemClicked)
        }
    }

    fun setup(item: Match) {
        currentItem = item

        val team1 = item.teams.getOrNull(0)
        val team2 = item.teams.getOrNull(1)

        setupTeamInfo(team1, binding.team1Name, binding.team1Logo)
        setupTeamInfo(team2, binding.team2Name, binding.team2Logo)
        binding.vs.isVisible = team1 != null && team2 != null

        val isHappeningNow = item.id == 0
        val matchTimeBackgroundResource = when {
            isHappeningNow -> R.drawable.match_item_time_now_background
            else -> R.drawable.match_item_time_background
        }
        binding.matchTime.setBackgroundResource(matchTimeBackgroundResource)
        binding.matchTime.text = "AGORA"

        binding.leagueAndSerie.text = "${item.league.name} - ${item.serie.fullName}"

        loadImage(item.league.imageUrl, binding.leagueLogo)
    }

    private fun setupTeamInfo(team: Team?, teamNameTextView: TextView, teamLogoImageView: ImageView) {
        teamNameTextView.isVisible = team != null
        teamLogoImageView.isVisible = team != null

        if (team != null) {
            teamNameTextView.text = team.name
            loadImage(team.imageUrl, teamLogoImageView)
        }
    }

    private fun loadImage(url: String, imageView: ImageView) {
        Glide.with(imageView)
            .load(url)
            .apply(RequestOptions.circleCropTransform())
            .into(imageView)
    }
}