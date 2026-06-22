package com.stockgro.anchor.date.localDateTime

import com.stockgro.anchor.date.timeZone.AnchorTimeZone
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Instant

object FormaterUtils {
    internal fun Instant.format(
        pattern: DateTimePattern,
        timeZone: AnchorTimeZone = AnchorTimeZone.systemDefault()
    ): String = this.toLocalDateTime(TimeZone.of(timeZone.zoneId)).format(pattern)

    internal fun LocalDateTime.format(pattern: DateTimePattern): String =
        LocalDateTimeFormatter.ofPattern(pattern, Locale.en()).format(this)

    internal fun LocalDate.format(pattern: DateTimePattern): String =
        LocalDateTimeFormatter.ofPattern(pattern, Locale.en()).format(this)

    internal fun LocalTime.format(pattern: DateTimePattern): String =
        LocalDateTimeFormatter.ofPattern(pattern, Locale.en()).format(this)

    internal fun Instant.toRelativeString(timeZone: AnchorTimeZone, pattern: DateTimePattern, compareWith: Instant = Clock.System.now()): String {
        val diff = compareWith - this

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
