package com.jorotayo.fl_datatracker.domain.model

import android.net.Uri

/**
 * Sealed hierarchy of per-type UI state derived from a [DataField] entity.
 * Each subclass carries only the data its composable actually needs.
 */
sealed class DataFieldUiState {
    abstract val fieldId: Long
    abstract val label: String
    abstract val hint: String

    data class ShortText(
        override val fieldId: Long,
        override val label: String,
        override val hint: String,
        val value: String = ""
    ) : DataFieldUiState()

    data class LongText(
        override val fieldId: Long,
        override val label: String,
        override val hint: String,
        val value: String = ""
    ) : DataFieldUiState()

    data class Boolean(
        override val fieldId: Long,
        override val label: String,
        override val hint: String = "",
        /** Overrideable chip labels — defaults to Yes / No. */
        val trueLabel: String = "Yes",
        val falseLabel: String = "No",
        val value: kotlin.Boolean = false
    ) : DataFieldUiState()

    data class Date(
        override val fieldId: Long,
        override val label: String,
        override val hint: String = "",
        val value: String = ""
    ) : DataFieldUiState()

    data class Time(
        override val fieldId: Long,
        override val label: String,
        override val hint: String = "",
        val value: String = ""
    ) : DataFieldUiState()

    data class Count(
        override val fieldId: Long,
        override val label: String,
        override val hint: String = "",
        val min: Int = 0,
        val max: Int = 999999,
        val value: Int = 0
    ) : DataFieldUiState()

    data class TriState(
        override val fieldId: Long,
        override val label: String,
        override val hint: String = "",
        /** Three option labels stored in DataField.first / second / third. */
        val options: List<String> = listOf("Low", "Medium", "High"),
        /** -1 = nothing selected yet. */
        val selected: Int = -1
    ) : DataFieldUiState()

    data class Image(
        override val fieldId: Long,
        override val label: String,
        override val hint: String = "",
        val uri: Uri? = null
    ) : DataFieldUiState()

    data class DynamicList(
        override val fieldId: Long,
        override val label: String,
        override val hint: String = "",
        val items: List<String> = listOf("")
    ) : DataFieldUiState()
}
