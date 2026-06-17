package com.stockgro.anchor.date.localDateTime

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toJavaLocalTime
import java.time.format.DateTimeFormatter

actual class LocalDateTimeFormatter private constructor(
    private val pattern: String,
    private val locale: Locale
) {

    private val formatter: DateTimeFormatter by lazy {
        DateTimeFormatter.ofPattern(pattern, locale.javaLocale)
    }

    actual fun format(localDateTime: LocalDateTime): String {
        return formatter.format(localDateTime.toJavaLocalDateTime())
    }

    actual fun format(localTime: LocalTime): String {
        return formatter.format(localTime.toJavaLocalTime())
    }

    actual fun format(localDate: LocalDate): String {
        return formatter.format(localDate.toJavaLocalDate())
    }

    actual companion object {
        actual fun ofPattern(pattern: String, locale: Locale): LocalDateTimeFormatter {
            return LocalDateTimeFormatter(pattern, locale)
        }
    }
}