package com.vron.cstv.match_list.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SerieDto(
    @SerializedName("full_name")
    val fullName: String
)