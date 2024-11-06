package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.domain.model.DataItem
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.ui.theme.subtitleTextColour
import com.jorotayo.fl_datatracker.util.Dimen.medium
import com.jorotayo.fl_datatracker.util.Dimen.small
import com.jorotayo.fl_datatracker.util.Dimen.xSmall
import kotlin.math.floor

@DefaultPreviews
@Composable
fun PreviewEmptyFormRadioRowV2() {
    FL_DatatrackerTheme {
        formRadioRowV2(
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
}

@DefaultPreviews
@Composable
fun PreviewFormRadioRowV2() {
    FL_DatatrackerTheme {
        formRadioRowV2(
            data = DataRowState(
                DataItem(
                    dataItemId = 0,
                    fieldName = "Data Field for Radio Row",
                    first = "No",
                    second = "N/A",
                    third = "Yes",
                    presetId = 0,
                    dataId = 1,
                    dataValue = "afds"
                )
            ),
            setDataValue = {}
        )
    }
}

@Composable
fun formRadioRowV2(
    data: DataRowState,
    setDataValue: (String) -> Unit,
): String {
    val options = listOf(data.dataItem.first, data.dataItem.second, data.dataItem.third)

    val defaultSelected =
        if (data.dataItem.dataValue == "") {
            floor(options.size.toDouble() / 2) // default value
        } else {
            // if previously selected
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
    if (data.dataItem.dataValue == "") {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(small)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = data.dataItem.fieldName,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colors.subtitleTextColour,
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(xSmall),
                text = "No options provided for ${data.dataItem.fieldName}. " +
                        "Please edit the Data Field and provide values",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.primary,
            )
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(small)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = data.dataItem.fieldName,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colors.subtitleTextColour,
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
                            .padding(start = small, end = small, top = 5.dp),
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
                    .padding(top = xSmall),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Button Data capture
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.6f),
                    shape = RoundedCornerShape(medium),
                    elevation = xSmall
                ) {
                    Row(
                        modifier = Modifier
                            .wrapContentSize()
                    ) {
                        options.forEach { text ->
                            if (text.isNotBlank()) {
                                Text(
                                    text = text,
                                    style = MaterialTheme.typography.body1,
                                    color = if (text == selectedOption) Color.White else MaterialTheme.colors.primary,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable {
                                            onSelectionChange(text)
                                            setDataValue(selectedOption)
                                        }
                                        .background(
                                            if (text == selectedOption) {
                                                MaterialTheme.colors.primary
                                            } else {
                                                MaterialTheme.colors.surface
                                            }
                                        )
                                        .padding(
                                            vertical = xSmall,
                                            horizontal = 5.dp,
                                        ),
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    return selectedOption
}
