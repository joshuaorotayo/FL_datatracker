package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.DataFieldEvent
import com.jorotayo.fl_datatracker.util.TransparentTextField
import com.jorotayo.fl_datatracker.viewModels.DataFieldsViewModel

@Composable
fun BoolValues(
    viewModel: DataFieldsViewModel,
    optionsMaxChars: Int
) {
    val newDataField = viewModel.newDataField.value

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp, start = 5.dp, end = 5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp)
        ) {
            Text(
                text = "Enter in the values for the boolean e.g. Yes and No",
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.caption,
                color = Color.Gray
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TransparentTextField(
                modifier = Modifier.weight(1f),
                text = newDataField.firstValue,
                label = "1st Value",
                placeholder = newDataField.firstValue.ifBlank { "1st Value" },
                onValueChange = {
                    if (it.length <= optionsMaxChars) viewModel.onEvent(
                        DataFieldEvent.AddFirstValue(it)
                    )
                }
            )
            TransparentTextField(
                modifier = Modifier.weight(1f),
                text = newDataField.secondValue,
                label = "2nd Value",
                placeholder = newDataField.secondValue.ifBlank { "2nd Value" },
                onValueChange = {
                    if (it.length <= optionsMaxChars) viewModel.onEvent(
                        DataFieldEvent.AddSecondValue(it)
                    )
                }
            )
        }
    }
}