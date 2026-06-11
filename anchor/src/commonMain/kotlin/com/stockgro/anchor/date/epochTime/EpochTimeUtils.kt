package com.stockgro.anchor.date.epochTime

import kotlin.time.Clock
import kotlin.time.Instant

object EpochTimeUtils {

    /**
     * Wraps a primitive Long explicitly as type-safe EpochSeconds.
     */
    fun Long.toEpochSeconds(): EpochSeconds = EpochSeconds(this)

    /**
     * Wraps a primitive Long explicitly as type-safe EpochMillis.
     */
    fun Long.toEpochMillis(): EpochMillis = EpochMillis(this)

    /**
     * Gets the current point in time from the system clock as type-safe EpochSeconds.
     */
    fun getCurrentSeconds(): EpochSeconds {
        return EpochSeconds(Clock.System.now().epochSeconds)
    }

    /**
     * Gets the current point in time from the system clock as type-safe EpochMillis.
     */
    fun getCurrentMillis(): EpochMillis {
        return EpochMillis(Clock.System.now().toEpochMilliseconds())
    }

    /**
     * Converts type-safe EpochSeconds explicitly into type-safe EpochMillis.
     */
    fun EpochSeconds.toEpochMillis(): EpochMillis {
        return EpochMillis(this.value * 1000L)
    }

    /**
     * Instantiates a kotlinx.datetime.Instant explicitly from this EpochSeconds instance.
     */
    fun EpochSeconds.toInstant(): Instant {
        return Instant.fromEpochSeconds(this.value)
    }

    /**
     * Converts type-safe EpochMillis explicitly into type-safe EpochSeconds (dropping decimals).
     */
    fun EpochMillis.toEpochSeconds(): EpochSeconds {
        return EpochSeconds(this.value / 1000L)
    }

    /**
     * Instantiates a kotlinx.datetime.Instant explicitly from this EpochMillis instance.
     */
    fun EpochMillis.toInstant(): Instant {
        return Instant.fromEpochMilliseconds(this.value)
    }

    fun Instant.toEpochSecondsWrapper(): EpochSeconds {
        return EpochSeconds(this.epochSeconds)
    }

    /**
     * Extracts the value out of an existing Instant as type-safe EpochMillis.
     */
    fun Instant.toEpochMillisWrapper(): EpochMillis {
        return EpochMillis(this.toEpochMilliseconds())
    }
}