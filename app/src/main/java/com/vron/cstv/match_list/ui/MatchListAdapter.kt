package com.vron.cstv.match_list.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vron.cstv.databinding.MatchListItemBinding
import com.vron.cstv.match_list.domain.model.Match

class MatchListAdapter(
    private val onItemClicked: (Match) -> Unit
) : RecyclerView.Adapter<MatchListItemViewHolder>() {
    private val list = mutableListOf<Match>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchListItemViewHolder {
        val binding = MatchListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MatchListItemViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: MatchListItemViewHolder, position: Int) {
        holder.setup(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun setItems(newList: List<Match>) {
        val diffResult = DiffUtil.calculateDiff(MatchListDiffCallback(this.list, newList))
        diffResult.dispatchUpdatesTo(this)

        list.clear()
        list.addAll(newList)
    }
}