package com.stockgro.anchor.date.epochTime

import com.stockgro.anchor.date.localDateTime.FormaterUtils.format
import com.stockgro.anchor.date.localDateTime.LocalDateTimeFormatter
import com.stockgro.anchor.date.localDateTime.Locale
import com.stockgro.anchor.date.localDateTime.changeTimeZone
import com.stockgro.anchor.date.timeZone.TimeZoneIds
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock.System
import kotlin.time.Instant
import kotlin.time.Instant.Companion.fromEpochSeconds

/**
 * Wraps a primitive Long explicitly as type-safe EpochSeconds.
 */
inline fun Long.toEpochSeconds(): EpochSeconds = EpochSeconds(this)

/**
 * Wraps a primitive Long explicitly as type-safe EpochMillis.
 */
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

/**
 * Formatting extensions for EpochSeconds
 */
fun EpochSeconds.format(
    pattern: String,
    timeZoneId: String = TimeZoneIds.systemDefault()
): String {
    val tz = TimeZone.of(timeZoneId)
    return this.toInstant().toLocalDateTime(tz).format(pattern)
}

/**
 * Formatting extensions for EpochMillis
 */
fun EpochMillis.format(
    pattern: String,
    timeZoneId: String = TimeZoneIds.systemDefault()
): String {
    val tz = TimeZone.of(timeZoneId)
    return this.toInstant().toLocalDateTime(tz).format(pattern)
}

/**
 * Parsing functions
 */
fun parseToEpochMillis(
    dateString: String,
    pattern: String,
    timeZoneId: String = TimeZoneIds.systemDefault()
): EpochMillis? {
    return try {
        val formatter = LocalDateTimeFormatter.ofPattern(pattern, Locale.en())
        val localDateTime = formatter.parse(dateString)
        val tz = TimeZone.of(timeZoneId)
        EpochMillis(localDateTime.toInstant(tz).toEpochMilliseconds())
    } catch (e: Exception) {
        null
    }
}

fun parseToEpochSeconds(
    dateString: String,
    pattern: String,
    timeZoneId: String = TimeZoneIds.systemDefault()
): EpochSeconds? {
    return parseToEpochMillis(dateString, pattern, timeZoneId)?.toEpochSeconds()
}

/**
 * Convert formatted date from one timezone to another and return the new formatted string.
 */
fun changeFormatTimeZone(
    dateString: String,
    pattern: String,
    fromTimeZoneId: String,
    toTimeZoneId: String
): String? {
    return try {
        val formatter = LocalDateTimeFormatter.ofPattern(pattern, Locale.en())
        val localDateTime = formatter.parse(dateString)
        val fromTz = TimeZone.of(fromTimeZoneId)
        val toTz = TimeZone.of(toTimeZoneId)
        localDateTime.changeTimeZone(fromTz, toTz).format(pattern)
    } catch (e: Exception) {
        null
    }
}

/**
 * Get start of the day for EpochSeconds in a specific timezone
 */
fun EpochSeconds.startOfDay(timeZoneId: String = TimeZoneIds.systemDefault()): EpochSeconds {
    val tz = TimeZone.of(timeZoneId)
    val localDate = this.toInstant().toLocalDateTime(tz).date
    return EpochSeconds(localDate.atStartOfDayIn(tz).epochSeconds)
}

/**
 * Get end of the day for EpochSeconds in a specific timezone
 */
fun EpochSeconds.endOfDay(timeZoneId: String = TimeZoneIds.systemDefault()): EpochSeconds {
    val tz = TimeZone.of(timeZoneId)
    val localDate = this.toInstant().toLocalDateTime(tz).date
    val nextDayStart = localDate.plus(1, DateTimeUnit.DAY).atStartOfDayIn(tz)
    return EpochSeconds(nextDayStart.epochSeconds - 1L)
}

/**
 * Get start of the day for EpochMillis in a specific timezone
 */
fun EpochMillis.startOfDay(timeZoneId: String = TimeZoneIds.systemDefault()): EpochMillis {
    val tz = TimeZone.of(timeZoneId)
    val localDate = this.toInstant().toLocalDateTime(tz).date
    return EpochMillis(localDate.atStartOfDayIn(tz).toEpochMilliseconds())
}

/**
 * Get end of the day for EpochMillis in a specific timezone
 */
fun EpochMillis.endOfDay(timeZoneId: String = TimeZoneIds.systemDefault()): EpochMillis {
    val tz = TimeZone.of(timeZoneId)
    val localDate = this.toInstant().toLocalDateTime(tz).date
    val nextDayStart = localDate.plus(1, DateTimeUnit.DAY).atStartOfDayIn(tz)
    return EpochMillis(nextDayStart.toEpochMilliseconds() - 1L)
}
