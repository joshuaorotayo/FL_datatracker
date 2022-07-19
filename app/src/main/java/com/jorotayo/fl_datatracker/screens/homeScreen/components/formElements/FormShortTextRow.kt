package com.jorotayo.fl_datatracker.screens.homeScreen.components.formElements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun PreviewFormShortTextRow() {
    FormShortTextRow()
}

@Composable
fun FormShortTextRow() {
    val maxChar = 30
    var text by remember { mutableStateOf(TextFieldValue("")) }

    Box(
        modifier = Modifier.wrapContentSize(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .shadow(elevation = 4.dp)
                .background(MaterialTheme.colors.primary),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(0.5f),
                text = "TEST",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onPrimary
            )
            Column(
                modifier = Modifier
                    .shadow(elevation = ((-4).dp))
            ) {
                TextField(
                    modifier = Modifier
                        .weight(0.5f)
                        .background(MaterialTheme.colors.onPrimary)
                        .padding(0.dp),
                    value = text,
                    onValueChange = {
                        if (text.text.length <= maxChar) text = it
                    },
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "placeholder text",
                            textAlign = TextAlign.Center
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.onPrimary
                    )
                )
                Text(
                    text = "${text.text.length} / $maxChar",
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.onPrimary)
                )
            }
        }
    }


}