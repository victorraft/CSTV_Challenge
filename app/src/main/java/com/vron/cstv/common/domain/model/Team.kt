package com.vron.cstv.common.domain.model

import java.io.Serializable

data class Team(
    val id: Int,
    val name: String,
    val imageUrl: String
) : Serializable