package com.vron.cstv.common.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.vron.cstv.R
import com.vron.cstv.common.domain.model.Team
import com.vron.cstv.databinding.TeamVsTeamBinding

class TeamVsTeamView : ConstraintLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, attributeSetId: Int) : super(context, attrs, attributeSetId)

    private val binding = TeamVsTeamBinding.inflate(LayoutInflater.from(context), this)

    fun setTeams(team1: Team?, team2: Team?) {
        setupTeamInfo(team1, binding.team1Name, binding.team1Logo)
        setupTeamInfo(team2, binding.team2Name, binding.team2Logo)
    }

    private fun setupTeamInfo(team: Team?, teamNameTextView: TextView, teamLogoImageView: ImageView) {
        teamNameTextView.text = team?.name ?: context.getText(R.string.team_undefined)
        loadImage(team?.imageUrl, teamLogoImageView)
    }

    private fun loadImage(url: String?, imageView: ImageView) {
        Glide.with(this)
            .load(url)
            .apply(RequestOptions.circleCropTransform())
            .into(imageView)
    }
}