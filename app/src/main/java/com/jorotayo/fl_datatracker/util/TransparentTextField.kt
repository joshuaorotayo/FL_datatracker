package com.jorotayo.fl_datatracker.util

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.util.Dimen.xxSmall


@Preview(showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode")
@Preview(showBackground = true, name = "Light Mode")
@Composable
fun PreviewTransparentTextField() {
    FL_DatatrackerTheme {
        TransparentTextField(text = "",
            placeholder = "Value",
            label = "Option",
            modifier = Modifier,
            onValueChange = {}
        )
    }
}

@Composable
fun TransparentTextField(
    text: String,
    placeholder: String,
    label: String,
    modifier: Modifier,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = false,
) {


    val textColour = if (isSystemInDarkTheme()) Color.Gray else MaterialTheme.colors.primary

    Box(
        modifier = modifier
            .padding(xxSmall)
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = onValueChange,
            label = {
                Text(
                    text = label,
                    color = textColour
                )
            },
            singleLine = singleLine,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.primary,
                unfocusedBorderColor = textColour,
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
                    color = if (text.isBlank()) textColour else Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        )
    }

}
