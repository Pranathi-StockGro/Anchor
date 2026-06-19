package com.example.baseapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stockgro.anchor.date.localDateTime.DateTimePatterns
import com.stockgro.anchor.date.localDateTime.FormaterUtils.format
import com.stockgro.anchor.date.localDateTime.changeTimeZone
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

enum class InputType(val label: String) {
    EPOCH_SECONDS("Epoch Seconds"),
    EPOCH_MILLIS("Epoch Millis"),
    DATE_PICKER("Date Picker")
}

// Data class tracking TimeZone layout options
data class UiTimeZoneDisplay(
    val displayName: String,
    val zone: TimeZone
)

val appTargetTimeZoneList = listOf(
    UiTimeZoneDisplay("System Default", TimeZone.currentSystemDefault()),
    UiTimeZoneDisplay("India (IST)", TimeZone.of("Asia/Kolkata")),
    UiTimeZoneDisplay("UAE (GST)", TimeZone.of("Asia/Dubai")),
    UiTimeZoneDisplay("Coordinated Universal Time (UTC)", TimeZone.UTC)
)


@Composable
@Preview
fun App() {
    MaterialTheme {
        // Enum to manage user's preferred input type
        DateTimeConverterScreen()
    }
}

data class UiPatternDisplay(
    val name: String,
    val patternString: String,
    val example: String
)

// A static list created on the App side using your library constants
val appPatternList = listOf(
    UiPatternDisplay("DATE_TIME_12H", DateTimePatterns.DATE_TIME_12H, "10 Jun 2026, 02:54:59 PM"),
    UiPatternDisplay("DATE_TIME_24H", DateTimePatterns.DATE_TIME_24H, "10 Jun 2026, 14:54:59"),
    UiPatternDisplay("DATE_TIME_SHORT", DateTimePatterns.DATE_TIME_SHORT, "10 Jun 2026, 02:54 PM"),
    UiPatternDisplay("DATE_TIME_PIPE", DateTimePatterns.DATE_TIME_PIPE, "07 May 2025 | 10:33"),
    UiPatternDisplay("DATE_DOT_TIME", DateTimePatterns.DATE_DOT_TIME, "10 Jun . 9:34 AM"),
    UiPatternDisplay("DATE_FULL", DateTimePatterns.DATE_FULL, "10 June 2026"),
    UiPatternDisplay("DATE_SHORT", DateTimePatterns.DATE_SHORT, "10 Jun 2026"),
    UiPatternDisplay("DATE_NUMERIC", DateTimePatterns.DATE_NUMERIC, "10/06/2026"),
    UiPatternDisplay("TIME_12H", DateTimePatterns.TIME_12H, "12:54 PM"),
    UiPatternDisplay(
        "TIME_12H_WITH_SECONDS",
        DateTimePatterns.TIME_12H_WITH_SECONDS,
        "12:54:59 PM"
    ),
    UiPatternDisplay("TIME_24H", DateTimePatterns.TIME_24H, "14:54"),
    UiPatternDisplay("TIME_24H_WITH_SECONDS", DateTimePatterns.TIME_24H_WITH_SECONDS, "14:54:59"),
    UiPatternDisplay("DATE_TIME_12H_WITH_WEEKDAY", DateTimePatterns.DATE_TIME_12H_WITH_WEEKDAY, "Wednesday, 10 Jun 2026, 04:45:01 PM")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimeConverterScreen() {
    var selectedPatternItem by remember { mutableStateOf(appPatternList.first()) }
    var selectedInputType by remember { mutableStateOf(InputType.EPOCH_SECONDS) }

    // Source is LOCKED to System Default. User chooses a target.
    val sourceSystemZone = remember { TimeZone.currentSystemDefault() }
    var selectedTargetZoneItem by remember { mutableStateOf(appTargetTimeZoneList.first()) }

    var textInput by remember { mutableStateOf("") }
    var selectedPickerMillis by remember { mutableStateOf<Long?>(null) }

    var showDatePicker by remember { mutableStateOf(false) }
    var showPatternDropdown by remember { mutableStateOf(false) }
    var showTargetZoneDropdown by remember { mutableStateOf(false) }

    // --- PIPELINE 1: Extract the baseline absolute instant ---
    val baseInstant = remember(textInput, selectedPickerMillis, selectedInputType) {
        try {
            when (selectedInputType) {
                InputType.EPOCH_SECONDS -> {
                    if (textInput.isBlank()) return@remember null
                    val seconds = textInput.toLongOrNull() ?: return@remember null
                    Instant.fromEpochSeconds(seconds)
                }

                InputType.EPOCH_MILLIS -> {
                    if (textInput.isBlank()) return@remember null
                    val millis = textInput.toLongOrNull() ?: return@remember null
                    Instant.fromEpochMilliseconds(millis)
                }

                InputType.DATE_PICKER -> {
                    val millis = selectedPickerMillis ?: return@remember null
                    Instant.fromEpochMilliseconds(millis)
                }
            }
        } catch (e: Exception) {
            null
        }
    }

    // --- PIPELINE 2: Format the Local System Time Output ---
    val systemTimeResult = remember(baseInstant, selectedPatternItem) {
        if (baseInstant == null) "Waiting for valid input..."
        else baseInstant.format(selectedPatternItem.patternString, sourceSystemZone)
    }

    // --- PIPELINE 3: Shift time and format Target Zone Output ---
    val targetTimeResult = remember(baseInstant, selectedPatternItem, selectedTargetZoneItem) {
        if (baseInstant == null) return@remember "Waiting for valid input..."
        try {
            // Convert current absolute instant to local wall clock time representation
            val sourceLocalDateTime = baseInstant.toLocalDateTime(sourceSystemZone)

            // Shift zone mapping utilizing your library's changeTimeZone extension function
            val shiftedLocalDateTime = sourceLocalDateTime.changeTimeZone(
                from = sourceSystemZone,
                to = selectedTargetZoneItem.zone
            )

            val finalInstant = shiftedLocalDateTime.toInstant(selectedTargetZoneItem.zone)
            finalInstant.format(selectedPatternItem.patternString, selectedTargetZoneItem.zone)
        } catch (e: Exception) {
            "Conversion Error: ${e.message}"
        }
    }

    Column(
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("KMP Date-Time Converter", style = MaterialTheme.typography.headlineMedium)

        Text("Select Input Mode:", style = MaterialTheme.typography.titleMedium)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            InputType.entries.forEach { type ->
                FilterChip(
                    selected = selectedInputType == type,
                    onClick = {
                        selectedInputType = type
                        textInput = ""
                    },
                    label = { Text(type.label) }
                )
            }
        }

        when (selectedInputType) {
            InputType.EPOCH_SECONDS, InputType.EPOCH_MILLIS -> {
                OutlinedTextField(
                    value = textInput,
                    onValueChange = { input -> textInput = input.filter { it.isDigit() } },
                    label = { Text("Enter ${selectedInputType.label}") },
                    placeholder = { Text(if (selectedInputType == InputType.EPOCH_SECONDS) "1717977600" else "1717977600000") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }

            InputType.DATE_PICKER -> {
                OutlinedTextField(
                    value = selectedPickerMillis?.let {
                        Instant.fromEpochMilliseconds(it).toString()
                    } ?: "No Date Selected",
                    onValueChange = {},
                    label = { Text("Picked Calendar Date") },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker = true }) {
                            Icon(Icons.Default.DateRange, contentDescription = "Open Date Picker")
                        }
                    }
                )
            }
        }

        Text("Select Target Time Zone Conversion:", style = MaterialTheme.typography.titleMedium)
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = selectedTargetZoneItem.displayName,
                onValueChange = {},
                readOnly = true,
                label = { Text("Convert Destination Zone") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        Icons.Default.ArrowDropDown, contentDescription = null,
                        Modifier.clickable { showTargetZoneDropdown = true })
                }
            )
            Box(modifier = Modifier.matchParentSize().clickable { showTargetZoneDropdown = true })

            DropdownMenu(
                expanded = showTargetZoneDropdown,
                onDismissRequest = { showTargetZoneDropdown = false },
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                appTargetTimeZoneList.forEach { zoneItem ->
                    DropdownMenuItem(
                        text = { Text(zoneItem.displayName) },
                        onClick = {
                            selectedTargetZoneItem = zoneItem
                            showTargetZoneDropdown = false
                        }
                    )
                }
            }
        }

        Text("Target Presentation Format:", style = MaterialTheme.typography.titleMedium)
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = selectedPatternItem.name,
                onValueChange = {},
                readOnly = true,
                label = { Text("Active Formatter Pattern") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        Icons.Default.ArrowDropDown, contentDescription = null,
                        Modifier.clickable { showPatternDropdown = true })
                }
            )
            Box(modifier = Modifier.matchParentSize().clickable { showPatternDropdown = true })

            DropdownMenu(
                expanded = showPatternDropdown,
                onDismissRequest = { showPatternDropdown = false },
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                appPatternList.forEach { patternVariant ->
                    DropdownMenuItem(
                        text = {
                            Column {
                                Text(patternVariant.name, style = MaterialTheme.typography.bodyLarge)
                                Text(patternVariant.example, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
                            }
                        },
                        onClick = {
                            selectedPatternItem = patternVariant
                            showPatternDropdown = false
                        }
                    )
                }
            }
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

        // --- DUAL OUTPUT PANEL ---
        Text("Conversion Results:", style = MaterialTheme.typography.titleMedium)

        // 1. Current Local System Time Presentation Box
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Your Local Time (${sourceSystemZone.id}):", style = MaterialTheme.typography.labelMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = systemTimeResult, style = MaterialTheme.typography.titleLarge)
            }
        }

        // 2. Transformed Target Time Presentation Box
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Converted Target Time (${selectedTargetZoneItem.zone.id}):",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = targetTimeResult, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onPrimaryContainer)
            }
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    selectedPickerMillis = datePickerState.selectedDateMillis
                    showDatePicker = false
                }) { Text("Confirm") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}