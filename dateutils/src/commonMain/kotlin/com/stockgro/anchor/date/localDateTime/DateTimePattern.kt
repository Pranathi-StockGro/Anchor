package com.stockgro.anchor.date.localDateTime

import kotlin.jvm.JvmInline

@JvmInline
value class DateTimePattern private constructor(val pattern: String) {

    companion object {
        fun of(pattern: String): DateTimePattern {
            return DateTimePattern(pattern)
        }

        const val DATE_TIME_12H_STR = "d MMM yyyy, hh:mm:ss a"
        const val DATE_TIME_24H_STR = "d MMM yyyy, HH:mm:ss"
        const val DATE_TIME_SHORT_STR = "d MMM yyyy, hh:mm a"
        const val DATE_TIME_PIPE_STR = "dd MMM yyyy '|' HH:mm"
        const val DATE_DOT_TIME_STR = "d MMM '.' h:mm a"
        const val DATE_FULL_STR = "d MMMM yyyy"
        const val DATE_SHORT_STR = "d MMM yyyy"
        const val DATE_NUMERIC_STR = "dd/MM/yyyy"
        const val TIME_12H_STR = "HH:mm a"
        const val TIME_12H_WITH_SECONDS_STR = "HH:mm:ss a"
        const val TIME_24H_STR = "HH:mm"
        const val TIME_24H_WITH_SECONDS_STR = "HH:mm:ss"
        const val DATE_TIME_12H_WITH_WEEKDAY_STR = "EEEE, d MMM yyyy, hh:mm:ss a"
        const val DATE_TIME_SHORT_WITH_WEEKDAY_STR = "EEE, d MMM yyyy, hh:mm a"
        const val DATE_FULL_WITH_WEEKDAY_STR = "EEEE, d MMMM yyyy"
        const val DATE_SHORT_WITH_WEEKDAY_STR = "EEE, d MMM yyyy"

        val DATE_TIME_12H = DateTimePattern(DATE_TIME_12H_STR)
        val DATE_TIME_24H = DateTimePattern(DATE_TIME_24H_STR)
        val DATE_TIME_SHORT = DateTimePattern(DATE_TIME_SHORT_STR)
        val DATE_TIME_PIPE = DateTimePattern(DATE_TIME_PIPE_STR)
        val DATE_DOT_TIME = DateTimePattern(DATE_DOT_TIME_STR)
        val DATE_FULL = DateTimePattern(DATE_FULL_STR)
        val DATE_SHORT = DateTimePattern(DATE_SHORT_STR)
        val DATE_NUMERIC = DateTimePattern(DATE_NUMERIC_STR)
        val TIME_12H = DateTimePattern(TIME_12H_STR)
        val TIME_12H_WITH_SECONDS = DateTimePattern(TIME_12H_WITH_SECONDS_STR)
        val TIME_24H = DateTimePattern(TIME_24H_STR)
        val TIME_24H_WITH_SECONDS = DateTimePattern(TIME_24H_WITH_SECONDS_STR)
        val DATE_TIME_12H_WITH_WEEKDAY = DateTimePattern(DATE_TIME_12H_WITH_WEEKDAY_STR)
        val DATE_TIME_SHORT_WITH_WEEKDAY = DateTimePattern(DATE_TIME_SHORT_WITH_WEEKDAY_STR)
        val DATE_FULL_WITH_WEEKDAY = DateTimePattern(DATE_FULL_WITH_WEEKDAY_STR)
        val DATE_SHORT_WITH_WEEKDAY = DateTimePattern(DATE_SHORT_WITH_WEEKDAY_STR)
    }
}
