package com.vron.cstv.common.data.remote.dto

import com.google.gson.annotations.SerializedName

enum class OpponentTypeDto {
    @SerializedName("Team")
    TEAM,

    @SerializedName("Player")
    PLAYER
}