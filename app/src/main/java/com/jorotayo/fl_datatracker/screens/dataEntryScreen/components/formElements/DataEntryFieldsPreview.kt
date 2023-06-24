package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jorotayo.fl_datatracker.domain.model.DataItem
import com.jorotayo.fl_datatracker.domain.util.DataFieldType
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.util.Dimen.xSmall

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Preview(showBackground = true, name = "Light Mode")
@Composable
fun PreviewDataEntryFieldsPreview() {
    FL_DatatrackerTheme {
        DataEntryFieldsPreview()
    }
}

@Composable
fun DataEntryFieldsPreview() {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(xSmall),
        elevation = xSmall
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

        /*    val data = Data(
                0,
                0,
                "Preview Template",
                "06-06-2023",
                "01-01-2023"
            )*/

        FormNameHeader(setName = {}, data = screenState)
        formShortTextRowV2(data = dataRowStateEx, setDataValue = {})
        formLongTextRowV2(data = dataRowStateEx.dataItem, setDataValue = {})
        formRadioRowV2(data = dataRowStateEx, setDataValue = {})
        formCountRowV2(data = dataRowStateEx, setDataValue = {})
        formListRowV4(data = dataRowStateEx.copy(), setDataValue = {})
        formTimeRowV2(data = dataRowStateEx.copy(), setDataValue = {})
        formImageRowV2(data = dataRowStateEx.copy(), setDataValue = {})
    }
}