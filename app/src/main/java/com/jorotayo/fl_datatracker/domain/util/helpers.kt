package com.jorotayo.fl_datatracker.domain.util

import android.net.Uri
import com.jorotayo.fl_datatracker.data.model.DataField
import com.jorotayo.fl_datatracker.domain.model.DataFieldUiState

// =============================================================================
// CONVENIENCE — map a list of fields in one call
// =============================================================================

fun List<DataField>.toUiStateList(): List<DataFieldUiState> = map { it.toUiState() }

// =============================================================================
// PRIVATE HELPERS
// =============================================================================

private const val LIST_DELIMITER = "|"

/** Converts a pipe-delimited string to a list, returning [""] for an empty string. */
fun String.toListItems(): List<String> =
    if (isBlank()) listOf("") else split(LIST_DELIMITER).map { it.trim() }

/** Converts a list of strings to a pipe-delimited string for storage. */
fun List<String>.toDelimited(): String =
    filter { it.isNotBlank() }.joinToString(LIST_DELIMITER)

/** Converts a URI string to a [Uri], returning null for blank/invalid strings. */
fun String.toUri(): Uri? = if (isBlank()) null else Uri.parse(this)
