package com.vron.cstv.match_list.data.remote.dto

import com.google.gson.annotations.SerializedName

enum class OpponentTypeDto {
    @SerializedName("Team")
    TEAM,

    @SerializedName("Player")
    PLAYER
}