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

internal const val DAY_HOURS = 24L
internal val LocalTime.Companion.MIN get() = LocalTime(0, 0)
internal val LocalTime.Companion.MAX get() = LocalTime(23, 59, 59, 999999999)

internal fun LocalDateTime.Companion.now(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDateTime {
    return Clock.System.now().toLocalDateTime(timeZone)
}

internal fun LocalDate.Companion.now(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDate {
    return LocalDateTime.now(timeZone).date
}

internal fun LocalTime.Companion.now(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalTime {
    return LocalDateTime.now(timeZone).time
}

internal fun LocalDate.atStartOfDay(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDateTime {
    return this.atStartOfDayIn(timeZone).toLocalDateTime(timeZone)
}

internal fun LocalDate.atEndOfDay(): LocalDateTime {
    return LocalDateTime(this, LocalTime.MAX)
}

internal fun LocalDate.atEndOfDay(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDateTime {
    return this
        .plus(1, DateTimeUnit.DAY)
        .atStartOfDayIn(timeZone).minus(1.nanoseconds)
        .toLocalDateTime(timeZone)
}

internal fun LocalDateTime.changeTimeZone(
    from: TimeZone,
    to: TimeZone
): LocalDateTime {
    val instant = this.toInstant(from)
    return instant.toLocalDateTime(to)
}

internal fun LocalDateTime.fromSystemToTimeZone(to: TimeZone): LocalDateTime {
    return this.changeTimeZone(from = TimeZone.currentSystemDefault(), to = to)
}

internal fun LocalDateTime.fromTimeZoneToSystem(from: TimeZone): LocalDateTime {
    return this.changeTimeZone(from = from, to = TimeZone.currentSystemDefault())
}

internal fun LocalDateTime.toUtc(): LocalDateTime {
    return this.changeTimeZone(from = TimeZone.currentSystemDefault(), to = TimeZone.UTC)
}

internal fun LocalDateTime.fromUtc(): LocalDateTime {
    return this.changeTimeZone(from = TimeZone.UTC, to = TimeZone.currentSystemDefault())
}

internal infix fun LocalDateTime.durationUntil(end: LocalDateTime): Duration {
    val timeZone = TimeZone.currentSystemDefault()
    return end.toInstant(timeZone) - this.toInstant(timeZone)
}