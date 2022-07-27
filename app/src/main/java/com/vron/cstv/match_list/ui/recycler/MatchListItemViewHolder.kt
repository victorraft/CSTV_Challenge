package com.vron.cstv.match_list.ui.recycler

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.vron.cstv.R
import com.vron.cstv.databinding.MatchListItemBinding
import com.vron.cstv.match_list.domain.model.Match

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

        binding.team1Name.text = item.team1.name
        binding.team2Name.text = item.team2.name

        val isHappeningNow = item.id == 0
        val matchTimeBackgroundResource = when {
            isHappeningNow -> R.drawable.match_item_time_now_background
            else -> R.drawable.match_item_time_background
        }
        binding.matchTime.setBackgroundResource(matchTimeBackgroundResource)
        binding.matchTime.text = "AGORA"

        binding.leagueAndSerie.text = "${item.league.name} - ${item.series.name}"

        loadImage(item.team1.imageUrl, binding.team1Logo)
        loadImage(item.team2.imageUrl, binding.team2Logo)
        loadImage(item.league.imageUrl, binding.leagueLogo)
    }

    private fun loadImage(url: String, imageView: ImageView) {
        Glide.with(imageView)
            .load(url)
//            .circleCrop()
            .apply(RequestOptions.circleCropTransform())
//            .placeholder(R.drawable.loading_spinner)
            .into(imageView)
    }
}