package com.jorotayo.fl_datatracker.domain.useCases

import com.jorotayo.fl_datatracker.domain.useCases.useCasesData.*

data class DataUseCases(
    val deleteData: DeleteData,
    val deleteDataById: DeleteDataById,
    val getDataByDataId: GetDataByDataId,
    val getDataByDataName: GetDataByDataName,
    val updateData: UpdateData,
    val addData: AddData,
    val validateInsertDataForm: ValidateInsertDataForm,
)