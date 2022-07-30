package com.vron.cstv.common.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SerieDto(
    @SerializedName("full_name")
    val fullName: String
)