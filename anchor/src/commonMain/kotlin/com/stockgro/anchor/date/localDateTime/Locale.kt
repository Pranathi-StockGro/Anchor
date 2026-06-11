package com.stockgro.anchor.date.localDateTime

expect class Locale {
    companion object {
        fun default(): Locale
        fun en(): Locale
        fun forLanguageTag(languageTag: String): Locale
    }
}