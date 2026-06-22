package com.stockgro.anchor.date.epochTime

import kotlin.jvm.JvmInline

@JvmInline
value class EpochSeconds(val value: Long) {
    companion object
}

@JvmInline
value class EpochMillis(val value: Long) {
    companion object
}
