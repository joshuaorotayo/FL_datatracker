package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.domain.model.DataField
import kotlin.math.floor

@Preview(showBackground = true)
@Composable
fun PreviewFormRadioRowV2() {
    formRadioRowV2(
        data = DataField(
            id = 0,
            fieldName = "Data Field for Radio Row",
            first = "No",
            second = "N/A",
            third = "Yes",
        )
    )
}

@Composable
fun formRadioRowV2(
    data: DataField,
): String {
    val options = listOf(data.first, data.second, data.third)

    val defaultSelected = floor(options.size.toDouble() / 2)

    var selectedOption by remember {
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
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 10.dp)
                    .fillMaxWidth(),
                text = data.fieldName,
                textAlign = TextAlign.Start,
                color = Color.Gray,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            //Button Data capture
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(0.7f)
                    .wrapContentSize()
            ) {
                options.forEach { text ->
                    if (text.isNotBlank()) {
                        Text(
                            text = text,
                            style = MaterialTheme.typography.body1,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    onSelectionChange(text)
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
            .height(5.dp)
    )

    return selectedOption
}