package com.stockgro.anchor.date.timeZone

import kotlin.jvm.JvmInline
import kotlinx.datetime.TimeZone

@JvmInline
value class AnchorTimeZone private constructor(val zoneId: String) {

    companion object {
        fun of(zoneId: String): AnchorTimeZone {
            return AnchorTimeZone(zoneId)
        }

        val UTC = AnchorTimeZone("UTC")
        val INDIA = AnchorTimeZone("Asia/Kolkata")
        val DUBAI = AnchorTimeZone("Asia/Dubai")

        fun systemDefault(): AnchorTimeZone {
            return AnchorTimeZone(TimeZone.currentSystemDefault().id)
        }
    }
}
