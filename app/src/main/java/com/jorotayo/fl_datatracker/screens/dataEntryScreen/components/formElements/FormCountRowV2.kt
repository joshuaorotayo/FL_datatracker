package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.domain.model.DataItem

@Preview(showBackground = true)
@Composable
fun PreviewFormCountRowV2() {
    FormCountRowV2(
        data = DataRowState(
            DataItem(
                dataItemId = 0,
                fieldName = "Data Field for Date Row Example",
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
fun FormCountRowV2(
    data: DataRowState,
    setDataValue: (String) -> Unit,
): String {
    var count = remember { mutableStateOf(0) }
    var unChanged = remember { mutableStateOf(true) }

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
            color = Color.Gray,
        )

        AnimatedVisibility(visible = data.hasError && unChanged.value) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 5.dp),
                    text = stringResource(id = R.string.count_row_error),
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

        Column(modifier = Modifier
            .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.5f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    modifier = Modifier
                        .size(28.dp)
                        .weight(1f)
                        .background(MaterialTheme.colors.primary)
                        .padding(vertical = 5.dp),
                    onClick = {
                        if ((count.value - 1) >= 0) {
                            count.value = count.value - 1
                            unChanged.value = false
                            setDataValue(count.value.toString())
                        }
                    })
                {
                    Icon(
                        imageVector = Icons.Default.Remove,
                        contentDescription = stringResource(id = R.string.decrement_description),
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
                TextField(
                    modifier = Modifier
                        .weight(3f),
                    value = count.value.toString(),
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    placeholder = {
                        Text(
                            text = if (count.value == 0) "0" else count.value.toString(),
                            style = MaterialTheme.typography.h6,
                            color = MaterialTheme.colors.primary
                        )
                    },
                    onValueChange = {
                        if (it.toIntOrNull() == null) {
                        } else if (it.isBlank()) {
                            count.value = 0
                        } else {
                            count.value = it.toInt()
                        }
                        unChanged.value = false
                        setDataValue(count.value.toString())
                    },
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
                        .also { MaterialTheme.typography.subtitle1 },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        textColor = MaterialTheme.colors.onSurface
                    )
                )
                IconButton(
                    modifier = Modifier
                        .size(28.dp)
                        .weight(1f)
                        .background(MaterialTheme.colors.primary)
                        .padding(vertical = 5.dp),
                    onClick = {
                        count.value = count.value + 1
                        unChanged.value = false
                        setDataValue(count.value.toString())
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.increment_description),
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }

    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(5.dp)
    )

    return count.value.toString()
}