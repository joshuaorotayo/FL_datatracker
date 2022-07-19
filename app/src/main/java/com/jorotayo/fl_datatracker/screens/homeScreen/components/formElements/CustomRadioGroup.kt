package com.jorotayo.fl_datatracker.screens.homeScreen.components.formElements

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun PreviewCustomRadioGroup() {
    CustomRadioGroup(modifier = Modifier)
}

@Composable
fun CustomRadioGroup(
    modifier: Modifier
) {
    val options = listOf(
        "No",
        "N/A",
        "Yes"
    )

    val defaultSelected = Math.floor(options.size.toDouble() / 2)

    var selectedOption by remember {
        mutableStateOf(options[defaultSelected.toInt()])
    }

    val onSelectionChange = { text: String ->
        selectedOption = text
    }
    Row(
        modifier = modifier
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
                    style = typography.body1.merge(),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            onSelectionChange(text)
                        }
                        .background(
                            if (text == selectedOption) {
                                MaterialTheme.colors.primary
                            } else {
                                Color.LightGray
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