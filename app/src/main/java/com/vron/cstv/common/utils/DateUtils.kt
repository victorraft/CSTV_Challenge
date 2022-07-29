package com.vron.cstv.common.utils

import android.text.format.DateUtils
import java.util.*
import kotlin.math.absoluteValue

fun getDifferenceInDays(dateA: Date, dateB: Date): Int {
    val diffInMilliseconds = removeTimeFromDate(dateA).time - removeTimeFromDate(dateB).time
    return (diffInMilliseconds / DateUtils.DAY_IN_MILLIS).toInt().absoluteValue
}

fun removeTimeFromDate(date: Date): Date {
    val calendar = Calendar.getInstance().apply {
        time = date
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    return calendar.time
}