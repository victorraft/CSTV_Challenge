package com.vron.cstv.common.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LeagueDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("image_url")
    val image_url: String?
)