package com.vron.cstv.match_list.ui.recycler

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.vron.cstv.R
import com.vron.cstv.common.presentation.DateFormatter
import com.vron.cstv.databinding.MatchListItemBinding
import com.vron.cstv.common.domain.model.Match
import com.vron.cstv.common.domain.model.MatchStatus
import com.vron.cstv.common.domain.model.Team

class MatchListItemViewHolder(
    private val binding: MatchListItemBinding,
    private val dateFormatter: DateFormatter,
    private val onItemClicked: (Match) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var currentItem: Match

    private val context: Context
        get() = binding.root.context

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
        teamNameTextView.text = team?.name ?: context.getText(R.string.team_undefined)
        loadImage(team?.imageUrl, teamLogoImageView)
    }

    private fun setupTimeLabel() {
        if (currentItem.status == MatchStatus.RUNNING) {
            binding.matchTime.setBackgroundResource(R.drawable.match_item_time_now_background)
            binding.matchTime.text = context.getText(R.string.time_label_now)
        } else {
            binding.matchTime.setBackgroundResource(R.drawable.match_item_time_background)
            binding.matchTime.text = dateFormatter.formatToLocalDateTime(currentItem.beginAt)
        }
    }

    private fun setupLeagueAndSerie() {
        binding.leagueAndSerie.text = "${currentItem.league.name} - ${currentItem.serie.fullName}"
        loadImage(currentItem.league.imageUrl, binding.leagueLogo)
    }

    private fun loadImage(url: String?, imageView: ImageView) {
        Glide.with(context)
            .load(url)
            .apply(RequestOptions.circleCropTransform())
            .into(imageView)
    }
}