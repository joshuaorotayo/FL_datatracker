package com.jorotayo.fl_datatracker.domain.useCases

import com.jorotayo.fl_datatracker.domain.useCases.useCasesData.AddData
import com.jorotayo.fl_datatracker.domain.useCases.useCasesData.DeleteData
import com.jorotayo.fl_datatracker.domain.useCases.useCasesData.DeleteDataById
import com.jorotayo.fl_datatracker.domain.useCases.useCasesData.GetData
import com.jorotayo.fl_datatracker.domain.useCases.useCasesData.GetDataByDataId
import com.jorotayo.fl_datatracker.domain.useCases.useCasesData.GetDataByDataName
import com.jorotayo.fl_datatracker.domain.useCases.useCasesData.UpdateData
import com.jorotayo.fl_datatracker.domain.useCases.useCasesData.ValidateInsertDataForm

data class DataUseCases(
    val deleteData: DeleteData,
    val deleteDataById: DeleteDataById,
    val getData: GetData,
    val getDataByDataId: GetDataByDataId,
    val getDataByDataName: GetDataByDataName,
    val updateData: UpdateData,
    val addData: AddData,
    val validateInsertDataForm: ValidateInsertDataForm,
)