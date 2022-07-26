package com.vron.cstv.app.ui.match_details.player

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.vron.cstv.databinding.PlayerInfoRightSideBinding

class PlayerInfoRightSideView : ConstraintLayout, PlayerInfoView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, attributeSetId: Int) : super(context, attrs, attributeSetId)

    private val binding = PlayerInfoRightSideBinding.inflate(LayoutInflater.from(context), this)

    override val playerPicture: ImageView
        get() = binding.playerPicture

    override val playerName: TextView
        get() = binding.playerName

    override val playerNickname: TextView
        get() = binding.playerNickname
}