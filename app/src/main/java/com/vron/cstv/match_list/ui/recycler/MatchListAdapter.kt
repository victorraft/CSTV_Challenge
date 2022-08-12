package com.vron.cstv.match_list.ui.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.vron.cstv.common.presentation.DateFormatter
import com.vron.cstv.databinding.MatchListErrorItemBinding
import com.vron.cstv.databinding.MatchListItemBinding
import com.vron.cstv.databinding.MatchListLoadingItemBinding
import com.vron.cstv.match_list.presentation.MatchListItem
import com.vron.cstv.match_list.ui.recycler.viewholder.ErrorViewHolder
import com.vron.cstv.match_list.ui.recycler.viewholder.LoadingViewHolder
import com.vron.cstv.match_list.ui.recycler.viewholder.MatchListItemViewHolder

private const val TYPE_MATCH = 0
private const val TYPE_LOADING = 1
private const val TYPE_ERROR = 2

class MatchListAdapter(
    private val dateFormatter: DateFormatter,
    private val onItemClicked: (MatchListItem) -> Unit,
    private val endOfListItemCount: Int,
    private val onEndOfListApproaching: () -> Unit
) : RecyclerView.Adapter<ViewHolder>() {

    private val list = mutableListOf<MatchListItem>()

    override fun getItemViewType(position: Int): Int =
        when (list[position]) {
            is MatchListItem.MatchItem -> TYPE_MATCH
            is MatchListItem.LoadingItem -> TYPE_LOADING
            is MatchListItem.ErrorItem -> TYPE_ERROR
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        when (viewType) {
            TYPE_MATCH -> {
                val binding = MatchListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MatchListItemViewHolder(binding, dateFormatter, onItemClicked)
            }
            TYPE_LOADING -> {
                val binding = MatchListLoadingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                LoadingViewHolder(binding)
            }
            TYPE_ERROR -> {
                val binding = MatchListErrorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ErrorViewHolder(binding) { onItemClicked(MatchListItem.ErrorItem) }
            }
            else -> throw IllegalArgumentException("Invalid View Type: $viewType")
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        if (holder is MatchListItemViewHolder && item is MatchListItem.MatchItem) {
            holder.bind(item)
            checkIfEndOfTheListIsApproaching(position)
        }
    }

    private fun checkIfEndOfTheListIsApproaching(position: Int) {
        val distanceFromEndOfList = list.size - position
        if (distanceFromEndOfList <= endOfListItemCount) {
            onEndOfListApproaching()
        }
    }

    override fun getItemCount(): Int = list.size

    fun setItems(newList: List<MatchListItem>) {
        val diffResult = DiffUtil.calculateDiff(MatchListDiffCallback(this.list, newList))
        diffResult.dispatchUpdatesTo(this)

        list.clear()
        list.addAll(newList)
    }
}