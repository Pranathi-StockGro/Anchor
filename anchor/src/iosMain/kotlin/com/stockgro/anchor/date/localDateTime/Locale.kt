package com.stockgro.anchor.date.localDateTime

import com.stockgro.anchor.date.localDateTime.Locale
import platform.Foundation.NSLocale
import platform.Foundation.currentLocale

actual class Locale private constructor(val nsLocale: NSLocale) {
    actual companion object {
        actual fun default(): Locale {
            return Locale(NSLocale.currentLocale)
        }

        actual fun en(): Locale {
            return forLanguageTag("en")
        }

        actual fun forLanguageTag(languageTag: String): Locale {
            return Locale(NSLocale(languageTag))
        }
    }
}