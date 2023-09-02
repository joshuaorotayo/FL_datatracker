package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.domain.model.DataItem
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.ui.theme.darkSurfaceHeadingColour
import com.jorotayo.fl_datatracker.ui.theme.lightSurfaceHeadingColour
import com.jorotayo.fl_datatracker.util.Dimen
import com.jorotayo.fl_datatracker.util.Dimen.xSmall

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true, name = "Light Mode")
@Composable
fun PreviewFormCountRowV2() {
    FL_DatatrackerTheme {
        formCountRowV2(
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

}

@Composable
fun formCountRowV2(
    data: DataRowState,
    setDataValue: (String) -> Unit,
): String {
    var count = remember { mutableStateOf(0) }
    var unChanged = remember { mutableStateOf(true) }

    val headerColour =
        if (isSystemInDarkTheme()) darkSurfaceHeadingColour else lightSurfaceHeadingColour


    Column(
        modifier = Modifier
            .padding(xSmall)
            .fillMaxWidth()
            .wrapContentSize()
    ) {
        Text(
            modifier = Modifier
                .padding(start = Dimen.small, end = Dimen.small, top = Dimen.xxSmall)
                .fillMaxWidth(),
            text = data.dataItem.fieldName,
            textAlign = TextAlign.Start,
            color = headerColour,
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
                        .padding(start = Dimen.small, end = Dimen.small, top = 5.dp),
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

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                        if (it.toIntOrNull() == null || it.isBlank()) {
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


    return count.value.toString()
}