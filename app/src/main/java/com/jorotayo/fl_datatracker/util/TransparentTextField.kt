package com.jorotayo.fl_datatracker.util

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview


@Preview
@Composable
fun PreviewTransparentTextField() {
}

@Composable
fun TransparentTextField(
    text: String,
    placeholder: String,
    modifier: Modifier,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle(),
    singleLine: Boolean = false
) {
    Box(
        modifier = modifier
    ) {
        TextField(
            value = text,
            onValueChange = onValueChange,
            singleLine = singleLine,
            textStyle = textStyle,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                },
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = MaterialTheme.colors.surface,
                backgroundColor = Color.Transparent,
                textColor = Color.Black
            ),
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
