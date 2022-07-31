package com.vron.cstv.match_list.ui.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vron.cstv.common.domain.model.Match
import com.vron.cstv.common.presentation.DateFormatter
import com.vron.cstv.databinding.MatchListItemBinding

class MatchListAdapter(
    private val dateFormatter: DateFormatter,
    private val onItemClicked: (Match) -> Unit,
    private val endOfListItemCount: Int,
    private val onEndOfListApproaching: () -> Unit
) : RecyclerView.Adapter<MatchListItemViewHolder>() {

    private val list = mutableListOf<Match>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchListItemViewHolder {
        val binding = MatchListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MatchListItemViewHolder(binding, dateFormatter, onItemClicked)
    }

    override fun onBindViewHolder(holder: MatchListItemViewHolder, position: Int) {
        holder.bind(list[position])

        val distanceFromEndOfList = list.size - position
        if (distanceFromEndOfList <= endOfListItemCount) {
            onEndOfListApproaching()
        }
    }

    override fun getItemCount(): Int = list.size

    fun setItems(newList: List<Match>) {
        val diffResult = DiffUtil.calculateDiff(MatchListDiffCallback(this.list, newList))
        diffResult.dispatchUpdatesTo(this)

        list.clear()
        list.addAll(newList)
    }
}