package com.stockgro.anchor.date.localDateTime

object DateTimePatterns {
    const val DATE_TIME_12H = "d MMM yyyy, hh:mm:ss a" // Example: "10 Jun 2026, 02:54:59 PM"
    const val DATE_TIME_24H = "d MMM yyyy, HH:mm:ss"   // Example: "10 Jun 2026, 14:54:59"
    const val DATE_TIME_SHORT = "d MMM yyyy, hh:mm a"    // Example: "10 Jun 2026, 02:54 PM"
    const val DATE_TIME_PIPE = "dd MMM yyyy '|' HH:mm"  // Example: "07 May 2025 | 10:33"
    const val DATE_DOT_TIME = "d MMM '.' h:mm a"       // Example: "10 Jun . 9:34 AM"
    const val DATE_FULL = "d MMMM yyyy"            // Example: "10 June 2026"
    const val DATE_SHORT = "d MMM yyyy"             // Example: "10 Jun 2026"
    const val DATE_NUMERIC = "dd/MM/yyyy"             // Example: "10/06/2026"
    const val TIME_12H = "HH:mm a"                // Example: "12:54 PM"
    const val TIME_12H_WITH_SECONDS = "HH:mm:ss a"             // Example: "12:54:59 PM"
    const val TIME_24H = "HH:mm"                  // Example: "14:54"
    const val TIME_24H_WITH_SECONDS = "HH:mm:ss"               // Example: "14:54:59"
    const val DATE_TIME_12H_WITH_WEEKDAY = "EEEE, d MMM yyyy, hh:mm:ss a" // Example: "Wednesday, 10 Jun 2026, 04:45:01 PM"
    const val DATE_TIME_SHORT_WITH_WEEKDAY = "EEE, d MMM yyyy, hh:mm a" // Example: "Wed, 10 Jun 2026, 04:45 PM"
    const val DATE_FULL_WITH_WEEKDAY = "EEEE, d MMMM yyyy" // Example: "Wednesday, 10 June 2026"
    const val DATE_SHORT_WITH_WEEKDAY = "EEE, d MMM yyyy" // Example: "Wed, 10 Jun 2026"
}