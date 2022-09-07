package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.domain.model.DataItem

@Preview(showBackground = true)
@Composable
fun PreviewFormCountRowV2() {
    val data = DataItem(
        dataItemId = 0,
        presetId = 0,
        fieldName = "Data Field for Count Row",
        dataId = 1
    )
    formCountRowV2(data = data)
}

@Composable
fun formCountRowV2(
    data: DataItem,
): String {
    var count = remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(shape = RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.surface)
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 8.dp)
                    .fillMaxWidth(),
                text = data.fieldName,
                textAlign = TextAlign.Start,
                color = Color.Gray,
            )
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
                        }
                    })
                {
                    Icon(
                        imageVector = Icons.Default.Remove,
                        contentDescription = "Decrement Count",
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
                /* Text(
                     text = "" + count.value,
                     color = if (count.value <= 0) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface,
                     modifier = Modifier
                         .weight(1f, fill = false),
                     textAlign = TextAlign.Center,
                     style = MaterialTheme.typography.h6

                 )*/
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
                            count.value = count.value
                        } else if (it.isEmpty()) {
                            count.value = 0
                        } else {
                            count.value = it.toInt()
                        }
                    },
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
                        .also { MaterialTheme.typography.subtitle1 },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        textColor = Color.Black
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
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Increment Count",
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