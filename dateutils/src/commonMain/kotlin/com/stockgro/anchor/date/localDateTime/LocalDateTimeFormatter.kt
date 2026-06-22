package com.stockgro.anchor.date.localDateTime

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

expect class LocalDateTimeFormatter {

    internal fun format(localDateTime: LocalDateTime): String
    internal fun format(localTime: LocalTime): String
    internal fun format(localDate: LocalDate): String
    internal fun parse(text: String): LocalDateTime

    companion object {
        internal fun ofPattern(pattern: DateTimePattern, locale: Locale): LocalDateTimeFormatter
    }
}
