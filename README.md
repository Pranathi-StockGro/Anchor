# Anchor

Anchor is a Kotlin Multiplatform (KMP) library providing enhanced date and time utilities, building upon `kotlinx-datetime`. It provides a consistent API across Android and iOS for formatting, timezone conversions, and type-safe epoch time handling.

## Features

- **Multiplatform Locale Support**: Unified `Locale` class for Android and iOS.
- **Type-safe Epoch Time**: `EpochSeconds` and `EpochMillis` value classes to prevent unit confusion.
- **Advanced Formatting**: Easy-to-use `LocalDateTimeFormatter` with caching for performance.
- **Timezone Utilities**: Shorthand methods for converting between UTC and system default timezones.
- **Relative Time**: Convert `Instant` to human-readable strings like "5 mins ago".

## Installation

Add `mavenLocal()` to your repository list in `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        mavenLocal() 
    }
}
```

Then add the dependency to your module's `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.stockgro.anchor:1.0.0")
}
```

## Usage

### Formatting
```kotlin
import com.stockgro.anchor.date.localDateTime.FormaterUtils.format

val now = LocalDateTime.now()
val formatted = now.format("dd MMM yyyy, hh:mm a")
```

### Timezone Conversion
```kotlin
import com.stockgro.anchor.date.localDateTime.toUtc
import com.stockgro.anchor.date.localDateTime.fromUtc

val utcTime = localTime.toUtc()
val localTime = utcTime.fromUtc()
```

### Epoch Time
```kotlin
import com.stockgro.anchor.date.epochTime.EpochTimeUtils.toEpochSeconds

val seconds = 1672531200L.toEpochSeconds()
val instant = seconds.toInstant()
```

### Relative Time
```kotlin
import com.stockgro.anchor.date.localDateTime.FormaterUtils.toRelativeString

val relative = instant.toRelativeString(TimeZone.currentSystemDefault(), "dd/MM/yyyy")
```

## Optimization

Anchor is designed with performance in mind:
- Uses **Value Classes** for epoch time to avoid object allocations.
- **Internal Caching**: Caches `LocalDateTimeFormatter` and `Locale` instances to reuse expensive underlying platform formatters (`java.time.format.DateTimeFormatter` on Android and `NSDateFormatter` on iOS).
- **Inline Functions**: Frequently used utility methods are marked as `inline` to reduce function call overhead.
