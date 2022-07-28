package com.vron.cstv.match_list.data.remote.dto

import com.google.gson.annotations.SerializedName

data class OpponentDto(
    @SerializedName("type")
    val type: String,
    @SerializedName("opponent")
    val opponent: TeamDto?,
)