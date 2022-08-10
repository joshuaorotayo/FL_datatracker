package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.util.TransparentTextField

@Composable
fun EditBoolValues(
    dataField: DataField,
    optionsMaxChars: Int,
    editStateValues: (Pair<Int, String>) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        //boolean text fields for editable
        TransparentTextField(
            modifier = Modifier.weight(1f),
            text = dataField.dataList?.get(0) ?: "1st Value",
            label = "1st Value",
            placeholder = dataField.dataList?.get(0) ?: "1st Value",
            onValueChange = {
                if (it.length <= optionsMaxChars)
                    editStateValues(Pair(0, it))
            }
        )
        TransparentTextField(
            modifier = Modifier.weight(1f),
            text = dataField.dataList?.get(1) ?: "2nd Value",
            label = "2nd Value",
            placeholder = dataField.dataList?.get(1) ?: "2nd Value",
            onValueChange = {
                if (it.length <= optionsMaxChars)
                    editStateValues(Pair(1, it))
            }
        )
    }
}