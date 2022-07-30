package com.vron.cstv.match_details.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.vron.cstv.R
import com.vron.cstv.common.domain.model.Player
import com.vron.cstv.databinding.PlayerInfoLeftSideBinding

class PlayerInfoLeftSideView : ConstraintLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, attributeSetId: Int) : super(context, attrs, attributeSetId)

    private val binding = PlayerInfoLeftSideBinding.inflate(LayoutInflater.from(context), this)

    fun setPlayer(player: Player?) {
        setPlayerNameAndNickname(player)
        loadImage(player?.imageUrl)
    }

    private fun setPlayerNameAndNickname(player: Player?) {
        binding.playerNickname.text = player?.name ?: context.getText(R.string.undefined)

        val playerName = player?.let { "${it.firstName.trim()} ${it.lastName.trim()}" }
        binding.playerName.text = playerName ?: context.getText(R.string.undefined)
    }

    private fun loadImage(url: String?) {
        Glide.with(this)
            .load(url)
            .transform(CenterCrop(), RoundedCorners(resources.getDimensionPixelSize(R.dimen.player_picture_corner_radius)))
            .into(binding.playerPicture)
    }
}