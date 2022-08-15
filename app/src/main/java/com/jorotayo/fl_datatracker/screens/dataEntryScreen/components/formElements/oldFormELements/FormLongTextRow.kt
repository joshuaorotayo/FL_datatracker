package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.oldFormELements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun PreviewFormLongTextRow() {
    FormLongTextRow(rowHint = "Row Hint")
}

@Composable
fun FormLongTextRow(
    rowHint: String?
    //insert any import variables
) {
    //define any local variables

    val maxChar = 250
    val (text, setText) = remember { mutableStateOf(TextFieldValue("")) }

    Box(
        modifier = Modifier
            .wrapContentSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .shadow(elevation = 4.dp)
                    .background(MaterialTheme.colors.primary),
                text = "Long Text Row",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onPrimary
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(0.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colors.onPrimary)
                    .shadow(elevation = 8.dp),
                value = text, onValueChange = {
                        newText ->
                    setText(newText.ofMaxLength(maxLength = maxChar))
                },
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    backgroundColor = MaterialTheme.colors.onPrimary,
                    textColor = Color.Black
                ),
                maxLines = 4,
                placeholder = {
                    Text(
                        text = rowHint ?: "placeholder text",
                        color = if (text.text.isBlank()) MaterialTheme.colors.primary else Color.Black,
                        textAlign = TextAlign.Center
                    )
                }
            )
        }
    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
    )
}