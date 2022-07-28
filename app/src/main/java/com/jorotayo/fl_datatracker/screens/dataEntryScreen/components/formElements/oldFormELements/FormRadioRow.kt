package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.oldFormELements

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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.floor

@Preview(showBackground = true)
@Composable
fun PreviewFormRadioRow() {
    val testOptions = listOf(
        "No",
        "N/A",
        "Yes"
    )

    FormRadioRow(options = testOptions)
}

@Composable
fun FormRadioRow(
    options: List<String>
) {

    val defaultSelected = floor(options.size.toDouble() / 2)

    var selectedOption by remember {
        mutableStateOf(options[defaultSelected.toInt()])
    }

    val onSelectionChange = { text: String ->
        selectedOption = text
    }
    Box(
        modifier = Modifier.wrapContentSize(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(10.dp))
                .shadow(elevation = 4.dp)
                .background(MaterialTheme.colors.primary),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                text = "TEST",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onPrimary
            )
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.onPrimary)
            ) {
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .wrapContentSize()
                        .clip(shape = RoundedCornerShape(40.dp))
                        .background(Color.LightGray)
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
    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
    )
}