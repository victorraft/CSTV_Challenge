package com.vron.cstv.match_list.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MatchDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
)