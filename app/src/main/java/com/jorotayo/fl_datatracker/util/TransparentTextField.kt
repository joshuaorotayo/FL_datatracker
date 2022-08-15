package com.jorotayo.fl_datatracker.util

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview
@Composable
fun PreviewTransparentTextField() {
}

@Composable
fun TransparentTextField(
    text: String,
    placeholder: String,
    label: String,
    modifier: Modifier,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle(),
    singleLine: Boolean = false
) {
    Box(
        modifier = modifier
            .padding(horizontal = 2.dp)
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = onValueChange,
            label = {
                Text(
                    text = label,
                    color = MaterialTheme.colors.primary
                )
            },
            singleLine = singleLine,
            textStyle = textStyle,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.primary,
                unfocusedBorderColor = MaterialTheme.colors.primary,
                backgroundColor = Color.Transparent,
                textColor = MaterialTheme.colors.onSurface
            ).also {
                TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = MaterialTheme.colors.surface
                )
            },
            placeholder = {
                Text(
                    text = placeholder,
                    color = if (text.isBlank()) MaterialTheme.colors.primary else Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        )
    }

}
