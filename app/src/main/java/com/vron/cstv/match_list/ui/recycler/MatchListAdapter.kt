package com.vron.cstv.match_list.ui.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.vron.cstv.common.domain.model.Match
import com.vron.cstv.common.presentation.DateFormatter
import com.vron.cstv.databinding.MatchListItemBinding

class MatchListAdapter(
    private val dateFormatter: DateFormatter,
    private val onItemClicked: (Match) -> Unit
) : PagingDataAdapter<Match, MatchListItemViewHolder>(MatchComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchListItemViewHolder {
        val binding = MatchListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MatchListItemViewHolder(binding, dateFormatter, onItemClicked)
    }

    override fun onBindViewHolder(holder: MatchListItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}