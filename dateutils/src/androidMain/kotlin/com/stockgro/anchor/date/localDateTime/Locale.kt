package com.stockgro.anchor.date.localDateTime

import java.util.Locale as JavaLocale

actual class Locale(val javaLocale: JavaLocale) {
    actual companion object {
        actual fun default(): Locale = Locale(JavaLocale.getDefault())
        actual fun en(): Locale = Locale(JavaLocale.ENGLISH)
        actual fun forLanguageTag(languageTag: String): Locale = Locale(JavaLocale.forLanguageTag(languageTag))
    }
}
