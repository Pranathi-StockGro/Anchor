package com.stockgro.anchor.date.localDateTime

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Instant

object FormaterUtils {
    fun Instant.format(
        pattern: String,
        timeZone: TimeZone = TimeZone.currentSystemDefault()
    ): String = this.toLocalDateTime(timeZone).format(pattern)

    fun LocalDateTime.format(pattern: String): String =
        LocalDateTimeFormatter.ofPattern(pattern, Locale.en()).format(this)

    fun LocalDate.format(pattern: String): String =
        LocalDateTimeFormatter.ofPattern(pattern, Locale.en()).format(this)

    fun LocalTime.format(pattern: String): String =
        LocalDateTimeFormatter.ofPattern(pattern, Locale.en()).format(this)

    fun Instant.toRelativeString(timeZone: TimeZone, pattern: String): String {
        val now = Clock.System.now()
        val diff = now - this

        val diffMinutes = diff.inWholeMinutes
        val diffHours = diff.inWholeHours

        return when {
            diffMinutes in 0..<60 -> {
                "$diffMinutes mins ago"
            }

            diffHours in 1..<24 -> {
                val label = if (diffHours == 1L) "hour" else "hours"
                "$diffHours $label ago"
            }

            diffHours in 24..<48 -> {
                "Yesterday"
            }

            else -> {
                this.format(pattern, timeZone)
            }
        }
    }
}