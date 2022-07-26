package com.vron.cstv.match_list.ui

import androidx.recyclerview.widget.RecyclerView
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

        binding.leagueAndSerie.text = "${item.league.name} - ${item.series.name}"
    }
}