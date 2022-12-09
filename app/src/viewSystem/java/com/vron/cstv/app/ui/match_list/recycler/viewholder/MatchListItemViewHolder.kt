package com.vron.cstv.app.ui.match_list.recycler.viewholder

import android.content.Context
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.vron.cstv.R
import com.vron.cstv.common.domain.model.MatchStatus
import com.vron.cstv.common.presentation.DateFormatter
import com.vron.cstv.databinding.MatchListItemBinding
import com.vron.cstv.match_list.presentation.MatchListItem
import com.vron.cstv.match_list.presentation.MatchListItem.MatchItem

class MatchListItemViewHolder(
    private val binding: MatchListItemBinding,
    private val dateFormatter: DateFormatter,
    private val onItemClicked: (MatchListItem) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var currentItem: MatchItem

    private val context: Context
        get() = binding.root.context

    init {
        binding.root.setOnClickListener {
            onItemClicked(currentItem)
        }
    }

    fun bind(item: MatchItem) {
        currentItem = item

        setupBothTeams()
        setupTimeLabel()
        setupLeagueAndSerie()
    }

    private fun setupBothTeams() {
        val team1 = currentItem.match.teams.getOrNull(0)
        val team2 = currentItem.match.teams.getOrNull(1)

        binding.teamVsTeam.setTeams(team1, team2)
    }

    private fun setupTimeLabel() {
        if (currentItem.match.status == MatchStatus.RUNNING) {
            binding.matchTime.setBackgroundResource(R.drawable.match_item_time_now_background)
            binding.matchTime.text = context.getText(R.string.time_label_now)
        } else {
            binding.matchTime.setBackgroundResource(R.drawable.match_item_time_background)
            binding.matchTime.text = dateFormatter.formatToLocalDateTime(currentItem.match.beginAt)
        }
    }

    private fun setupLeagueAndSerie() {
        binding.leagueAndSerie.text = "${currentItem.match.league.name} - ${currentItem.match.serie.fullName}"
        loadImage(currentItem.match.league.imageUrl, binding.leagueLogo)
    }

    private fun loadImage(url: String?, imageView: ImageView) {
        Glide.with(context)
            .load(url)
            .apply(RequestOptions.circleCropTransform())
            .into(imageView)
    }
}