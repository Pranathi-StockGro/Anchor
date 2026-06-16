package com.stockgro.anchor.date.localDateTime

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.nanoseconds

const val DAY_HOURS = 24L
val LocalTime.Companion.MIN get() = LocalTime(0, 0)
val LocalTime.Companion.MAX get() = LocalTime(23, 59, 59, 999999999)

fun LocalDateTime.Companion.now(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDateTime {
    return Clock.System.now().toLocalDateTime(timeZone)
}

fun LocalDate.Companion.now(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDate {
    return LocalDateTime.now(timeZone).date
}

fun LocalTime.Companion.now(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalTime {
    return LocalDateTime.now(timeZone).time
}

fun LocalDate.atStartOfDay(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDateTime {
    return this.atStartOfDayIn(timeZone).toLocalDateTime(timeZone)
}

fun LocalDate.atEndOfDay(): LocalDateTime {
    return LocalDateTime(this, LocalTime.MAX)
}

fun LocalDate.atEndOfDay(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDateTime {
    return this
        .plus(1, DateTimeUnit.DAY)
        .atStartOfDayIn(timeZone).minus(1.nanoseconds)
        .toLocalDateTime(timeZone)
}

/**
 * Changes a [LocalDateTime] from its current time zone to a target time zone.
 * * @param from The current time zone of this LocalDateTime.
 * @param to The destination time zone you want to convert to.
 */
fun LocalDateTime.changeTimeZone(
    from: TimeZone,
    to: TimeZone
): LocalDateTime {
    // 1. Map the wall clock time to an absolute point in time (Instant) based on its current zone
    val instant = this.toInstant(from)
    // 2. Convert that absolute point in time to the target zone's wall clock time
    return instant.toLocalDateTime(to)
}

/**
 * Convenience function to shift a [LocalDateTime] from the system's current time zone
 * to a target time zone (e.g., UTC).
 */
fun LocalDateTime.fromSystemToTimeZone(to: TimeZone): LocalDateTime {
    return this.changeTimeZone(from = TimeZone.currentSystemDefault(), to = to)
}

/**
 * Convenience function to shift a [LocalDateTime] from a specific time zone (e.g., UTC)
 * to the user's current system default time zone.
 */
fun LocalDateTime.fromTimeZoneToSystem(from: TimeZone): LocalDateTime {
    return this.changeTimeZone(from = from, to = TimeZone.currentSystemDefault())
}

/**
 * Direct shorthand to convert a [LocalDateTime] to UTC from the system default.
 */
fun LocalDateTime.toUtc(): LocalDateTime {
    return this.changeTimeZone(from = TimeZone.currentSystemDefault(), to = TimeZone.UTC)
}

/**
 * Direct shorthand to convert a UTC [LocalDateTime] to the local system default.
 */
fun LocalDateTime.fromUtc(): LocalDateTime {
    return this.changeTimeZone(from = TimeZone.UTC, to = TimeZone.currentSystemDefault())
}

infix fun LocalDateTime.durationUntil(end: LocalDateTime): Duration {
    val timeZone = TimeZone.currentSystemDefault()

    return end.toInstant(timeZone) - this.toInstant(timeZone)
}