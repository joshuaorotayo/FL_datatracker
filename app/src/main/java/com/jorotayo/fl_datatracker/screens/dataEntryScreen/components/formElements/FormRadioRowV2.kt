package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.domain.model.DataItem
import kotlin.math.floor

@Preview(showBackground = true)
@Composable
fun PreviewFormRadioRowV2() {
    FormRadioRowV2(
        data = DataRowState(
            DataItem(
                dataItemId = 0,
                fieldName = "Data Field for Radio Row",
                first = "No",
                second = "N/A",
                third = "Yes",
                presetId = 0,
                dataId = 1
            )
        ),
        setDataValue = {}
    )
}

@Composable
fun FormRadioRowV2(
    data: DataRowState,
    setDataValue: (String) -> Unit,
): String {
    val options = listOf(data.dataItem.first, data.dataItem.second, data.dataItem.third)

    // val defaultSelected = floor(options.size.toDouble() / 2)

    val defaultSelected =
        if (data.dataItem.dataValue == "") {
            floor(options.size.toDouble() / 2) //default value
        } else {
            //if previously selected
            when (data.dataItem.dataValue) {
                options[0] -> {
                    (0).toDouble()
                }
                options[1] -> {
                    (1).toDouble()
                }
                else -> {
                    (2).toDouble()
                }
            }
        }

    var selectedOption by rememberSaveable {
        mutableStateOf(options[defaultSelected.toInt()])
    }

    val onSelectionChange = { text: String ->
        selectedOption = text
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(shape = RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.surface)
    ) {
        Text(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                .fillMaxWidth(),
            text = data.dataItem.fieldName,
            textAlign = TextAlign.Start,
            color = MaterialTheme.colors.onSurface,
        )

        AnimatedVisibility(visible = data.hasError && data.dataItem.dataValue.isBlank()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 5.dp),
                    text = stringResource(id = R.string.radio_row_error),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.caption,
                    color = Color.Red,
                )
                Icon(
                    modifier = Modifier
                        .padding(end = 10.dp),
                    imageVector = Icons.Default.Warning,
                    contentDescription = stringResource(id = R.string.row_error_description),
                    tint = MaterialTheme.colors.primary
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            //Button Data capture
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .clip(shape = RoundedCornerShape(15.dp))
                    .wrapContentSize()
            ) {
                options.forEach { text ->
                    if (text.isNotBlank()) {
                        Text(
                            text = text,
                            style = MaterialTheme.typography.body1,
                            color = if (data.dataItem.dataValue.isNotEmpty()) Color.White else Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    onSelectionChange(text)
                                    setDataValue(selectedOption)
                                }
                                .background(
                                    if (text == selectedOption) {
                                        MaterialTheme.colors.primaryVariant
                                    } else {
                                        MaterialTheme.colors.primary
                                    }
                                )
                                .padding(
                                    vertical = 12.dp,
                                    horizontal = 5.dp,
                                ),
                        )
                    }
                }
            }
        }
    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
    )

    return selectedOption
}