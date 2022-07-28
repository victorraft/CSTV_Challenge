package com.vron.cstv.match_list.data.remote.dto

import com.google.gson.annotations.SerializedName

data class TeamDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("image_url")
    val image_url: String?
)