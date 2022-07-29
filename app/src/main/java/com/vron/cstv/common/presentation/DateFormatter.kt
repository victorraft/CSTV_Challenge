package com.vron.cstv.common.presentation

import android.content.Context
import android.text.format.DateUtils
import com.vron.cstv.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue

private const val INPUT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
private const val INPUT_DATE_TIMEZONE = "UTC"

class DateFormatter(context: Context) {

    private val inputDateFormat: DateFormat =
        SimpleDateFormat(INPUT_DATE_FORMAT).apply { timeZone = TimeZone.getTimeZone(INPUT_DATE_TIMEZONE) }

    private val displayDateFormat: DateFormat =
        SimpleDateFormat(context.getString(R.string.match_time_format)).apply { timeZone = TimeZone.getDefault() }

    private val todayDisplayDateFormat: DateFormat =
        SimpleDateFormat(context.getString(R.string.match_time_format_today)).apply { timeZone = TimeZone.getDefault() }

    private val closeDisplayDateFormat: DateFormat =
        SimpleDateFormat(context.getString(R.string.match_time_format_close)).apply { timeZone = TimeZone.getDefault() }

    fun formatToLocalDateTime(utcDateString: String): String =
        try {
            val inputDate = inputDateFormat.parse(utcDateString)!!
            val diffInDays = getDifferenceInDays(inputDate, Date())

            when {
                diffInDays == 0 -> todayDisplayDateFormat.format(inputDate)
                diffInDays < 3 -> closeDisplayDateFormat.format(inputDate).capitalize().replace(".", "")
                else -> displayDateFormat.format(inputDate)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            ""
        }

    private fun getDifferenceInDays(dateA: Date, dateB: Date): Int {
        val diffInMilliseconds = removeTimeFromDate(dateA).time - removeTimeFromDate(dateB).time
        return (diffInMilliseconds / DateUtils.DAY_IN_MILLIS).toInt().absoluteValue
    }

    private fun removeTimeFromDate(date: Date): Date {
        val calendar = Calendar.getInstance().apply {
            time = date
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.time
    }
}