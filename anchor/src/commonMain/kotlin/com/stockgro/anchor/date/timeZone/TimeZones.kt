package com.stockgro.anchor.date.timeZone

import kotlinx.datetime.TimeZone

object TimeZoneIds {
    const val UTC = "UTC"
    const val INDIA = "Asia/Kolkata"
    const val DUBAI = "Asia/Dubai"
    
    fun systemDefault(): String = TimeZone.currentSystemDefault().id
}
