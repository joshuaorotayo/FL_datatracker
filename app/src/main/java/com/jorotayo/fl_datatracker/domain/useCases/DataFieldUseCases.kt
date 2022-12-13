package com.jorotayo.fl_datatracker.domain.useCases

import com.jorotayo.fl_datatracker.domain.useCases.useCasesDataField.*

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
)
