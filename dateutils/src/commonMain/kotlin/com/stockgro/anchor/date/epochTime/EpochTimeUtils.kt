package com.stockgro.anchor.date.epochTime

import com.stockgro.anchor.date.localDateTime.DateTimePattern
import com.stockgro.anchor.date.localDateTime.FormaterUtils.format
import com.stockgro.anchor.date.localDateTime.FormaterUtils.toRelativeString
import com.stockgro.anchor.date.localDateTime.LocalDateTimeFormatter
import com.stockgro.anchor.date.localDateTime.Locale
import com.stockgro.anchor.date.localDateTime.changeTimeZone
import com.stockgro.anchor.date.timeZone.AnchorTimeZone
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock.System
import kotlin.time.Instant
import kotlin.time.Instant.Companion.fromEpochSeconds

inline fun Long.toEpochSeconds(): EpochSeconds = EpochSeconds(this)

inline fun Long.toEpochMillis(): EpochMillis = EpochMillis(this)

fun EpochSeconds.Companion.now(): EpochSeconds =
    EpochSeconds(System.now().epochSeconds)

fun EpochMillis.Companion.now(): EpochMillis =
    EpochMillis(System.now().toEpochMilliseconds())

inline fun EpochSeconds.toEpochMillis(): EpochMillis =
    EpochMillis(this.value * 1000L)

inline fun EpochMillis.toEpochSeconds(): EpochSeconds =
    EpochSeconds(this.value / 1000L)

internal inline fun EpochSeconds.toInstant(): Instant =
    fromEpochSeconds(this.value)

internal inline fun EpochMillis.toInstant(): Instant =
    Instant.fromEpochMilliseconds(this.value)

fun EpochSeconds.format(
    pattern: DateTimePattern,
    timeZone: AnchorTimeZone = AnchorTimeZone.systemDefault()
): String {
    val tz = TimeZone.of(timeZone.zoneId)
    return this.toInstant().toLocalDateTime(tz).format(pattern)
}

fun EpochMillis.format(
    pattern: DateTimePattern,
    timeZone: AnchorTimeZone = AnchorTimeZone.systemDefault()
): String {
    val tz = TimeZone.of(timeZone.zoneId)
    return this.toInstant().toLocalDateTime(tz).format(pattern)
}

fun parseToEpochMillis(
    dateString: String,
    pattern: DateTimePattern,
    timeZone: AnchorTimeZone = AnchorTimeZone.systemDefault()
): EpochMillis? {
    return try {
        val formatter = LocalDateTimeFormatter.ofPattern(pattern, Locale.en())
        val localDateTime = formatter.parse(dateString)
        val tz = TimeZone.of(timeZone.zoneId)
        EpochMillis(localDateTime.toInstant(tz).toEpochMilliseconds())
    } catch (e: Exception) {
        null
    }
}

fun parseToEpochSeconds(
    dateString: String,
    pattern: DateTimePattern,
    timeZone: AnchorTimeZone = AnchorTimeZone.systemDefault()
): EpochSeconds? {
    return parseToEpochMillis(dateString, pattern, timeZone)?.toEpochSeconds()
}

fun EpochMillis.toRelativeString(
    pattern: DateTimePattern,
    compareWith: EpochMillis = EpochMillis.now(),
    timeZone: AnchorTimeZone = AnchorTimeZone.systemDefault()
): String {
    return this.toInstant().toRelativeString(timeZone, pattern, compareWith.toInstant())
}

fun EpochSeconds.toRelativeString(
    pattern: DateTimePattern,
    compareWith: EpochSeconds = EpochSeconds.now(),
    timeZone: AnchorTimeZone = AnchorTimeZone.systemDefault()
): String = this.toEpochMillis().toRelativeString(pattern, compareWith.toEpochMillis(),timeZone)

fun changeFormatTimeZone(
    dateString: String,
    pattern: DateTimePattern,
    fromTimeZone: AnchorTimeZone,
    toTimeZone: AnchorTimeZone
): String? {
    return try {
        val formatter = LocalDateTimeFormatter.ofPattern(pattern, Locale.en())
        val localDateTime = formatter.parse(dateString)
        val fromTz = TimeZone.of(fromTimeZone.zoneId)
        val toTz = TimeZone.of(toTimeZone.zoneId)
        localDateTime.changeTimeZone(fromTz, toTz).format(pattern)
    } catch (e: Exception) {
        null
    }
}

fun EpochSeconds.startOfDay(timeZone: AnchorTimeZone = AnchorTimeZone.systemDefault()): EpochSeconds {
    val tz = TimeZone.of(timeZone.zoneId)
    val localDate = this.toInstant().toLocalDateTime(tz).date
    return EpochSeconds(localDate.atStartOfDayIn(tz).epochSeconds)
}

fun EpochSeconds.endOfDay(timeZone: AnchorTimeZone = AnchorTimeZone.systemDefault()): EpochSeconds {
    val tz = TimeZone.of(timeZone.zoneId)
    val localDate = this.toInstant().toLocalDateTime(tz).date
    val nextDayStart = localDate.plus(1, DateTimeUnit.DAY).atStartOfDayIn(tz)
    return EpochSeconds(nextDayStart.epochSeconds - 1L)
}

fun EpochMillis.startOfDay(timeZone: AnchorTimeZone = AnchorTimeZone.systemDefault()): EpochMillis {
    val tz = TimeZone.of(timeZone.zoneId)
    val localDate = this.toInstant().toLocalDateTime(tz).date
    return EpochMillis(localDate.atStartOfDayIn(tz).toEpochMilliseconds())
}

fun EpochMillis.endOfDay(timeZone: AnchorTimeZone = AnchorTimeZone.systemDefault()): EpochMillis {
    val tz = TimeZone.of(timeZone.zoneId)
    val localDate = this.toInstant().toLocalDateTime(tz).date
    val nextDayStart = localDate.plus(1, DateTimeUnit.DAY).atStartOfDayIn(tz)
    return EpochMillis(nextDayStart.toEpochMilliseconds() - 1L)
}
