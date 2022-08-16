package com.vron.cstv.match_list.ui.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.vron.cstv.databinding.MatchListErrorItemBinding

class ErrorViewHolder(
    binding: MatchListErrorItemBinding,
    private val onClick: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.setOnClickListener {
            onClick()
        }
    }
}