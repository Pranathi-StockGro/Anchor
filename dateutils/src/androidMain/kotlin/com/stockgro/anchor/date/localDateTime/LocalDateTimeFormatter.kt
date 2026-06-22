package com.stockgro.anchor.date.localDateTime

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toJavaLocalTime
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.format.DateTimeFormatter

actual class LocalDateTimeFormatter private constructor(
    private val formatter: DateTimeFormatter
) {
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
        return java.time.LocalDateTime.parse(text, formatter).toKotlinLocalDateTime()
    }

    actual companion object {
        internal actual fun ofPattern(pattern: DateTimePattern, locale: Locale): LocalDateTimeFormatter {
            val javaFormatter = DateTimeFormatter.ofPattern(pattern.pattern, locale.javaLocale)
            return LocalDateTimeFormatter(javaFormatter)
        }
    }
}
