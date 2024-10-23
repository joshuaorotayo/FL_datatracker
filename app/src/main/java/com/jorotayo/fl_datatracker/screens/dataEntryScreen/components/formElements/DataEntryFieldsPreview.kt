package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jorotayo.fl_datatracker.domain.model.DataItem
import com.jorotayo.fl_datatracker.domain.util.DataFieldType
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.theme.AppTheme
import com.jorotayo.fl_datatracker.ui.theme.AppTheme.dimens

@DefaultPreviews
@Composable
fun PreviewDataEntryFieldsPreview() {
    AppTheme {
        DataEntryFieldsPreview()
    }
}

@Composable
fun DataEntryFieldsPreview() {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimens.xSmall),
        elevation = dimens.xSmall
    ) {
        val dataRowStateEx = DataRowState(
            DataItem(
                0,
                0,
                0,
                "Preview",
                dataFieldType = DataFieldType.BOOLEAN.ordinal,
                "",
                "",
                "",
                true,
                "",
                "0",
            )
        )

        val screenState = DataEntryScreenState(
            dataName = "Sample Preview",
            nameError = false,
            nameErrorMsg = "",
            dataRows = mutableListOf(dataRowStateEx),
            formSubmitted = false
        )

        FormNameHeader(setName = {}, data = screenState)
        formShortTextRowV2(data = dataRowStateEx, setDataValue = {})
        formLongTextRowV2(data = dataRowStateEx.dataItem, setDataValue = {})
        formRadioRowV2(data = dataRowStateEx, setDataValue = {})
        formCountRowV2(data = dataRowStateEx, setDataValue = {})
        formListRowV4(data = dataRowStateEx.copy(), setDataValue = {})
        formTimeRowV2(data = dataRowStateEx.copy(), setDataValue = {})
        formImageRowV2(data = dataRowStateEx.copy())
    }
}
