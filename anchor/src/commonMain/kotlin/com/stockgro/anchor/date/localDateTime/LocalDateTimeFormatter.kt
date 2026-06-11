package com.stockgro.anchor.date.localDateTime

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

expect class LocalDateTimeFormatter {

    fun format(localDateTime: LocalDateTime): String
    fun format(localTime: LocalTime): String
    fun format(localDate: LocalDate): String

    companion object {
        fun ofPattern(pattern: String, locale: Locale): LocalDateTimeFormatter
    }
}