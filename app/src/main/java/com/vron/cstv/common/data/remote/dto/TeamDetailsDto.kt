package com.vron.cstv.common.data.remote.dto

import com.google.gson.annotations.SerializedName

data class TeamDetailsDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("players")
    val players: List<PlayerDto>,
)