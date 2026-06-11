package com.stockgro.anchor.date.localDateTime

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.atDate
import kotlinx.datetime.atTime
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
    actual fun format(localDateTime: LocalDateTime): String {
        val date = localDateTime.toNsDate()
            ?: throw IllegalStateException("Failed to convert $LocalDateTime to NSDate")
        return NSDateFormatter().apply {
            dateFormat = pattern
            locale = this@LocalDateTimeFormatter.locale.nsLocale
        }.stringFromDate(date)
    }

    actual fun format(localTime: LocalTime): String {
        val dateTime = localTime.atDate(LocalDate.now())
        return format(dateTime)
    }

    actual fun format(localDate: LocalDate): String {
        val dateTime = localDate.atTime(LocalTime(0, 0))
        return format(dateTime)
    }

    actual companion object {
        actual fun ofPattern(pattern: String, locale: Locale): LocalDateTimeFormatter {
            return LocalDateTimeFormatter(pattern, locale)
        }
    }
}