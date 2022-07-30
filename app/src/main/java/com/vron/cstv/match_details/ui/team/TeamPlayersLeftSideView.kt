package com.vron.cstv.match_details.ui.team

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.vron.cstv.common.domain.model.Player
import com.vron.cstv.databinding.TeamPlayersLeftSideBinding

class TeamPlayersLeftSideView : LinearLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val binding = TeamPlayersLeftSideBinding.inflate(LayoutInflater.from(context), this)

    init {
        orientation = VERTICAL
    }

    fun setPlayers(players: List<Player>) {
        binding.player1.setPlayer(players.getOrNull(0))
        binding.player2.setPlayer(players.getOrNull(1))
        binding.player3.setPlayer(players.getOrNull(2))
        binding.player4.setPlayer(players.getOrNull(3))
        binding.player5.setPlayer(players.getOrNull(4))
    }
}