package com.vron.cstv.match_list.ui.recycler

import androidx.recyclerview.widget.DiffUtil
import com.vron.cstv.common.domain.model.Match

class MatchListDiffCallback(
    private val oldList: List<Match>,
    private val newList: List<Match>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}