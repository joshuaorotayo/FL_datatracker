package com.jorotayo.fl_datatracker.ui.screens.dataEntry

sealed class DataEntryEvent {
    /** New record — load the currently selected preset from preferences. */
    object LoadFromPreference : DataEntryEvent()

    /** Edit existing record — load its saved values, verify preset still exists. */
    data class LoadRecord(val recordId: Long) : DataEntryEvent()

    /** User changed a field value. */
    data class UpdateValue(val fieldId: Long, val value: String) : DataEntryEvent()

    /** User tapped the Edit button on a read-only record. */
    object EnableEditing : DataEntryEvent()

    /** Save / submit the form. */
    object Submit : DataEntryEvent()

    /** Reset all values back to their defaults. */
    object Clear : DataEntryEvent()

    /** Dismiss the active toast. */
    object DismissToast : DataEntryEvent()
}