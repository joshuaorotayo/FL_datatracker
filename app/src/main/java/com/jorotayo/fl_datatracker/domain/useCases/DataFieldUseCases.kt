package com.jorotayo.fl_datatracker.domain.useCases

import com.jorotayo.fl_datatracker.domain.useCases.useCasesDataField.AddDataField
import com.jorotayo.fl_datatracker.domain.useCases.useCasesDataField.DeleteDataField
import com.jorotayo.fl_datatracker.domain.useCases.useCasesDataField.DeleteDataFields
import com.jorotayo.fl_datatracker.domain.useCases.useCasesDataField.GetDataFieldById
import com.jorotayo.fl_datatracker.domain.useCases.useCasesDataField.GetDataFieldNames
import com.jorotayo.fl_datatracker.domain.useCases.useCasesDataField.GetDataFields
import com.jorotayo.fl_datatracker.domain.useCases.useCasesDataField.GetDataFieldsByEnabled
import com.jorotayo.fl_datatracker.domain.useCases.useCasesDataField.GetDataFieldsByPresetId
import com.jorotayo.fl_datatracker.domain.useCases.useCasesDataField.GetDataFieldsByPresetIdEnabled
import com.jorotayo.fl_datatracker.domain.useCases.useCasesDataField.GetDataFieldsFlow
import com.jorotayo.fl_datatracker.domain.useCases.useCasesDataField.GetSubscribedDataFields
import com.jorotayo.fl_datatracker.domain.useCases.useCasesDataField.UpdateDataField

data class DataFieldUseCases(
    val addDataField: AddDataField,
    val deleteDataField: DeleteDataField,
    val getDataFields: GetDataFields,
    val updateDataField: UpdateDataField,
    val getDataFieldsFlow: GetDataFieldsFlow,
    val getDataFieldsByPresetId: GetDataFieldsByPresetId,
    val getDataFieldsByPresetIdEnabled: GetDataFieldsByPresetIdEnabled,
    val getDataFieldById: GetDataFieldById,
    val getDataFieldsByEnabled: GetDataFieldsByEnabled,
    val getDataFieldByNames: GetDataFieldNames,
    val getSubscribedDataFields: GetSubscribedDataFields,
    val deleteDataFields: DeleteDataFields,
)
