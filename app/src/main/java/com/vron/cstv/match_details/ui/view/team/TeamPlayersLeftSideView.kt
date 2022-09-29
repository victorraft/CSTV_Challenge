package com.vron.cstv.match_details.ui.view.team

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.vron.cstv.databinding.TeamPlayersLeftSideBinding
import com.vron.cstv.match_details.ui.view.player.PlayerInfoView

class TeamPlayersLeftSideView : LinearLayout, TeamPlayersView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val binding = TeamPlayersLeftSideBinding.inflate(LayoutInflater.from(context), this)

    override val player1: PlayerInfoView
        get() = binding.player1
    override val player2: PlayerInfoView
        get() = binding.player2
    override val player3: PlayerInfoView
        get() = binding.player3
    override val player4: PlayerInfoView
        get() = binding.player4
    override val player5: PlayerInfoView
        get() = binding.player5

    init {
        orientation = VERTICAL
    }
}