package com.jorotayo.fl_datatracker.util

import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.model.DataItem
import com.jorotayo.fl_datatracker.domain.model.Preset
import com.jorotayo.fl_datatracker.domain.util.DataFieldType.BOOLEAN
import com.jorotayo.fl_datatracker.domain.util.DataFieldType.COUNT
import com.jorotayo.fl_datatracker.domain.util.DataFieldType.DATE
import com.jorotayo.fl_datatracker.domain.util.DataFieldType.IMAGE
import com.jorotayo.fl_datatracker.domain.util.DataFieldType.LIST
import com.jorotayo.fl_datatracker.domain.util.DataFieldType.LONG_TEXT
import com.jorotayo.fl_datatracker.domain.util.DataFieldType.SHORT_TEXT
import com.jorotayo.fl_datatracker.domain.util.DataFieldType.TIME
import com.jorotayo.fl_datatracker.domain.util.DataFieldType.TRISTATE
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.DataEntryScreenState
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.DataRowState
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.states.DataFieldRowState

val exampleShortField = DataField(
    dataFieldId = 0,
    fieldName = "Short Field",
    dataFieldType = SHORT_TEXT,
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
    dataFieldType = LONG_TEXT,
    presetId = 0,
    first = "0",
    second = "2",
    third = "4",
    isEnabled = true,
    fieldHint = ""
)

val exampleBooleanField = DataField(
    dataFieldId = 2,
    fieldName = "Boolean Field",
    dataFieldType = BOOLEAN,
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
    dataFieldType = DATE,
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
    dataFieldType = TIME,
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
    dataFieldType = COUNT,
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
    dataFieldType = TRISTATE,
    presetId = 0,
    first = "0",
    second = "2",
    third = "4",
    isEnabled = true,
    fieldHint = ""
)

private val exampleImageField = DataField(
    dataFieldId = 7,
    fieldName = "Image Field",
    dataFieldType = IMAGE,
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
    dataFieldType = LIST,
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

val shortData: DataRowState = DataRowState(
    dataItem = DataItem(
        dataItemId = 0,
        dataId = 0,
        presetId = 0,
        fieldName = "Short",
        dataFieldType = SHORT_TEXT,
        first = "",
        second = "",
        third = "",
        isEnabled = true,
        fieldDescription = "Short String",
        dataValue = ""
    ), hasError = false, errorMsg = ""
)

val LongData: DataRowState = DataRowState(
    dataItem = DataItem(
        dataItemId = 1,
        dataId = 1,
        presetId = 0,
        fieldName = "Long Data",
        dataFieldType = LONG_TEXT,
        first = "",
        second = "",
        third = "",
        isEnabled = true,
        fieldDescription = "Long Text",
        dataValue = ""
    ), hasError = false, errorMsg = ""
)

val two_options: DataRowState = DataRowState(
    dataItem = DataItem(
        dataItemId = 2,
        dataId = 2,
        presetId = 0,
        fieldName = "Two Options",
        dataFieldType = BOOLEAN,
        first = "Yes",
        second = "No",
        third = "",
        isEnabled = true,
        fieldDescription = "Two Options",
        dataValue = ""
    ), hasError = false, errorMsg = ""
)

val date: DataRowState = DataRowState(
    dataItem = DataItem(
        dataItemId = 3,
        dataId = 3,
        presetId = 0,
        fieldName = "Date",
        dataFieldType = DATE,
        first = "",
        second = "",
        third = "",
        isEnabled = true,
        fieldDescription = "Date Field",
        dataValue = ""
    ), hasError = false, errorMsg = ""
)

val timeData: DataRowState = DataRowState(
    dataItem = DataItem(
        dataItemId = 4,
        dataId = 4,
        presetId = 0,
        fieldName = "Time",
        dataFieldType = TIME,
        first = "",
        second = "",
        third = "",
        isEnabled = true,
        fieldDescription = "Time data",
        dataValue = ""
    ), hasError = false, errorMsg = "Robert"
)

val count: DataRowState = DataRowState(
    dataItem = DataItem(
        dataItemId = 5,
        dataId = 5,
        presetId = 0,
        fieldName = "Count",
        dataFieldType = COUNT,
        first = "",
        second = "",
        third = "",
        isEnabled = true,
        fieldDescription = "Count data",
        dataValue = ""
    ), hasError = false, errorMsg = "Robert"
)

val three_options: DataRowState = DataRowState(
    dataItem = DataItem(
        dataItemId = 6,
        dataId = 6,
        presetId = 0,
        fieldName = "Three",
        dataFieldType = TRISTATE,
        first = "1",
        second = "2",
        third = "3",
        isEnabled = true,
        fieldDescription = "Three Options",
        dataValue = ""
    ), hasError = false, errorMsg = "Robert"
)

val imageData: DataRowState = DataRowState(
    dataItem = DataItem(
        dataItemId = 7,
        dataId = 7,
        presetId = 0,
        fieldName = "Deanne",
        dataFieldType = IMAGE,
        first = "",
        second = "",
        third = "",
        isEnabled = true,
        fieldDescription = "Image Item",
        dataValue = ""
    ), hasError = false, errorMsg = "Robert"
)

val listData: DataRowState = DataRowState(
    dataItem = DataItem(
        dataItemId = 8,
        dataId = 8,
        presetId = 0,
        fieldName = "List",
        dataFieldType = LIST,
        first = "",
        second = "",
        third = "",
        isEnabled = true,
        fieldDescription = "List Item",
        dataValue = ""
    ), hasError = false, errorMsg = "Robert"
)

val examplePopulatedDataEntry =
    DataEntryScreenState(
        dataRows = mutableListOf(
            shortData, LongData, two_options, date, timeData, count, three_options, imageData,
            listData
        ),
        currentDataId = 0,
        dataName = "Test Data",
        nameError = false,
        nameErrorMsg = "",
        formSubmitted = false,
        currentImageIndex = 0,
        presetSetting = Preset(
            presetId = 0,
            presetName = "Default"
        )
    )
