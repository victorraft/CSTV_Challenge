package com.vron.cstv.match_details.ui.view.player

import android.widget.ImageView
import android.widget.TextView

interface PlayerInfoView {
    val playerPicture: ImageView
    val playerName: TextView
    val playerNickname: TextView
}