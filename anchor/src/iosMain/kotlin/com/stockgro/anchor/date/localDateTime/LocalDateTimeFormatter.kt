package com.stockgro.anchor.date.localDateTime

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atDate
import kotlinx.datetime.atTime
import kotlinx.datetime.toKotlinInstant
import kotlinx.datetime.toLocalDateTime
import platform.Foundation.NSDateFormatter

actual class LocalDateTimeFormatter private constructor(
    private val pattern: String,
    private val locale: Locale
) {

    private val formatter: NSDateFormatter by lazy {
        NSDateFormatter().apply {
            dateFormat = pattern
            locale = this@LocalDateTimeFormatter.locale.nsLocale
        }
    }

    /**
     * @throws IllegalStateException localDateTime cannot be converted to NSDate
     */
    internal actual fun format(localDateTime: LocalDateTime): String {
        val date = localDateTime.toNsDate()
            ?: throw IllegalStateException("Failed to convert $localDateTime to NSDate")
        return formatter.stringFromDate(date)
    }

    internal actual fun format(localTime: LocalTime): String {
        val dateTime = localTime.atDate(LocalDate.now())
        return format(dateTime)
    }

    internal actual fun format(localDate: LocalDate): String {
        val dateTime = localDate.atTime(LocalTime(0, 0))
        return format(dateTime)
    }

    internal actual fun parse(text: String): LocalDateTime {
        val date = formatter.dateFromString(text)
            ?: throw IllegalArgumentException("Failed to parse $text with pattern $pattern")
        return date.toKotlinInstant().toLocalDateTime(TimeZone.UTC)
    }

    actual companion object {
        internal actual fun ofPattern(pattern: String, locale: Locale): LocalDateTimeFormatter {
            return LocalDateTimeFormatter(pattern, locale)
        }
    }
}