package com.jorotayo.fl_datatracker.util

import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.states.DataFieldRowState

val exampleShortField = DataField(
    dataFieldId = 0,
    fieldName = "Short Field",
    dataFieldType = 0,
    presetId = 0,
    first = "0",
    second = "2",
    third = "4",
    isEnabled = true,
    fieldHint = ""
)

val exampleLongField = DataField(
    dataFieldId = 1,
    fieldName = "Long Field",
    dataFieldType = 1,
    presetId = 0,
    first = "0",
    second = "2",
    third = "4",
    isEnabled = true,
    fieldHint = ""
)

val exampleBooleanField = DataField(
    dataFieldId = 2,
    fieldName = "Long Field",
    dataFieldType = 2,
    presetId = 0,
    first = "0",
    second = "2",
    third = "4",
    isEnabled = true,
    fieldHint = ""
)

val exampleDateField = DataField(
    dataFieldId = 3,
    fieldName = "Date Field",
    dataFieldType = 3,
    presetId = 0,
    first = "0",
    second = "2",
    third = "4",
    isEnabled = true,
    fieldHint = ""
)

val exampleTimeField = DataField(
    dataFieldId = 4,
    fieldName = "Time Field",
    dataFieldType = 4,
    presetId = 0,
    first = "0",
    second = "2",
    third = "4",
    isEnabled = true,
    fieldHint = ""
)

val exampleCountField = DataField(
    dataFieldId = 5,
    fieldName = "Count Field",
    dataFieldType = 5,
    presetId = 0,
    first = "0",
    second = "2",
    third = "4",
    isEnabled = true,
    fieldHint = ""
)

val exampleTristateField = DataField(
    dataFieldId = 6,
    fieldName = "Tristate Field",
    dataFieldType = 6,
    presetId = 0,
    first = "0",
    second = "2",
    third = "4",
    isEnabled = true,
    fieldHint = ""
)

private val dataField = DataField(
    dataFieldId = 7,
    fieldName = "Image Field",
    dataFieldType = 7,
    presetId = 0,
    first = "0",
    second = "2",
    third = "4",
    isEnabled = true,
    fieldHint = ""
)

val exampleImageField = DataField(
    dataFieldId = 7,
    fieldName = "Image Field",
    dataFieldType = 7,
    presetId = 0,
    first = "0",
    second = "2",
    third = "4",
    isEnabled = true,
    fieldHint = ""
)

val exampleListField = DataField(
    dataFieldId = 8,
    fieldName = "List Field",
    dataFieldType = 8,
    presetId = 0,
    first = "0",
    second = "2",
    third = "4",
    isEnabled = true,
    fieldHint = ""
)

val exampleDataFieldList = listOf(
    exampleShortField,
    exampleLongField,
    exampleBooleanField,
    exampleDateField,
    exampleTimeField,
    exampleCountField,
    exampleTristateField,
    exampleImageField,
    exampleListField
)

val exampleShortDataRowState =
    DataFieldRowState(
        dataField = exampleTristateField
    )