package com.vron.cstv.app.ui.match_details.player

import android.widget.ImageView
import android.widget.TextView

interface PlayerInfoView {
    val playerPicture: ImageView
    val playerName: TextView
    val playerNickname: TextView
}