package com.stockgro.anchor.date.timeZone

import kotlinx.datetime.TimeZone

/**
 * Extension property to access India Standard Time (IST) natively.
 */
val TimeZone.Companion.INDIA: TimeZone
    get() = TimeZone.of("Asia/Kolkata")

/**
 * Extension property to access Gulf Standard Time (GST / UAE) natively.
 */
val TimeZone.Companion.UAE: TimeZone
    get() = TimeZone.of("Asia/Dubai")