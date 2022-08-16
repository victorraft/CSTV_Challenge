package com.vron.cstv.match_list.ui.view.recycler

import androidx.recyclerview.widget.DiffUtil
import com.vron.cstv.match_list.presentation.MatchListItem

class MatchListDiffCallback(
    private val oldList: List<MatchListItem>,
    private val newList: List<MatchListItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return when {
            oldItem is MatchListItem.MatchItem && newItem is MatchListItem.MatchItem -> oldItem.match.id == newItem.match.id
            else -> oldItem == newItem
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}