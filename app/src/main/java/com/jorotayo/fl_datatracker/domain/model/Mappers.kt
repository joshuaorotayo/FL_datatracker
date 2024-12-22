package com.jorotayo.fl_datatracker.domain.model

import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.DataRowState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Mappers @Inject constructor() {

    fun mapToDataItem(dataRow: DataRowState, id: Long): DataItem {
        return DataItem(
            dataId = id,
            presetId = dataRow.dataItem.dataItemId,
            dataItemId = dataRow.dataItem.dataItemId,
            fieldName = dataRow.dataItem.fieldName,
            dataFieldType = dataRow.dataItem.dataFieldType,
            first = dataRow.dataItem.first,
            second = dataRow.dataItem.second,
            third = dataRow.dataItem.third,
            isEnabled = dataRow.dataItem.isEnabled,
            fieldDescription = dataRow.dataItem.fieldDescription,
            dataValue = dataRow.dataItem.dataValue
        )
    }

    fun mapToDataRowState(dataField: DataField, id: Long, presetId: Long): DataRowState {
        return DataRowState(
            DataItem(
                dataId = id,
                presetId = presetId,
                fieldName = dataField.fieldName,
                dataFieldType = dataField.dataFieldType,
                first = dataField.first,
                second = dataField.second,
                third = dataField.third,
                isEnabled = dataField.isEnabled,
                fieldDescription = dataField.fieldHint,
                dataValue = ""
            ),
            hasError = false,
            errorMsg = ""
        )
    }
}
