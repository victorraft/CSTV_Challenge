package com.vron.cstv.common.data.remote.dto

import com.google.gson.annotations.SerializedName

enum class MatchStatusDto {
    @SerializedName("canceled")
    CANCELLED,

    @SerializedName("finished")
    FINISHED,

    @SerializedName("not_started")
    NOT_STARTED,

    @SerializedName("postponed")
    POSTPONED,

    @SerializedName("running")
    RUNNING,
}