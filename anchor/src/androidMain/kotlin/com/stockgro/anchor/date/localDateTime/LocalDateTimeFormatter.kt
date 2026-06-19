package com.stockgro.anchor.date.localDateTime

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toJavaLocalTime
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalQueries

actual class LocalDateTimeFormatter private constructor(
    private val pattern: String,
    private val locale: Locale
) {

    private val formatter: DateTimeFormatter by lazy {
        DateTimeFormatter.ofPattern(pattern, locale.javaLocale)
    }

    internal actual fun format(localDateTime: LocalDateTime): String {
        return formatter.format(localDateTime.toJavaLocalDateTime())
    }

    internal actual fun format(localTime: LocalTime): String {
        return formatter.format(localTime.toJavaLocalTime())
    }

    internal actual fun format(localDate: LocalDate): String {
        return formatter.format(localDate.toJavaLocalDate())
    }

    internal actual fun parse(text: String): LocalDateTime {
        val temporalAccessor = formatter.parse(text)
        val localDate = temporalAccessor.query(TemporalQueries.localDate()) ?: java.time.LocalDate.now()
        val localTime = temporalAccessor.query(TemporalQueries.localTime()) ?: java.time.LocalTime.MIDNIGHT
        return java.time.LocalDateTime.of(localDate, localTime).toKotlinLocalDateTime()
    }

    actual companion object {
        internal actual fun ofPattern(pattern: String, locale: Locale): LocalDateTimeFormatter {
            return LocalDateTimeFormatter(pattern, locale)
        }
    }
}