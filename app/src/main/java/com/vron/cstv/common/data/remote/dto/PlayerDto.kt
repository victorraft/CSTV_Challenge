package com.vron.cstv.common.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PlayerDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("last_name")
    val lastName: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("image_url")
    val imageUrl: String?
)