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
import kotlin.math.floor

@Preview(showBackground = true)
@Composable
fun PreviewFormRadioRowV2() {
    val testOptions = listOf(
        "No",
        "N/A",
        "Yes"
    )

    FormRadioRowV2(options = testOptions)
}

@Composable
fun FormRadioRowV2(
    options: List<String>
) {
    val defaultSelected = floor(options.size.toDouble() / 2)

    var selectedOption by remember {
        mutableStateOf(options[defaultSelected.toInt()])
    }

    val onSelectionChange = { text: String ->
        selectedOption = text
    }

    Column(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(shape = RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.surface)
    ) {
        Row(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 10.dp)
                    .fillMaxWidth(),
                text = "Data Field for Radio Row Text",
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

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
    )
}