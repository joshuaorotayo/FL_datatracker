package com.jorotayo.fl_datatracker.screens.dataFieldsScreenRework.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import com.jorotayo.fl_datatracker.ui.DefaultDualPreview
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.ui.theme.isDarkMode
import com.jorotayo.fl_datatracker.util.Dimen
import com.jorotayo.fl_datatracker.util.Dimen.medium
import com.jorotayo.fl_datatracker.util.Dimen.xSmall
import com.jorotayo.fl_datatracker.util.Dimen.xxSmall
import com.jorotayo.fl_datatracker.util.Dimen.zero


@DefaultDualPreview
@Composable
fun PreviewMinimalShortTextField() {
    FL_DatatrackerTheme {
        Column {
            MinimalShortTextField(
                rowHeader = "this is a sample row",
                rowHint = "Enter text here",
                isError = false
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MinimalShortTextField(
    rowHeader: String,
    rowHint: String,
    isError: Boolean
) {
    val (text, setText) = remember { mutableStateOf("") }
    var cardElevation by remember { mutableStateOf(xSmall) }
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    Column(modifier = Modifier.wrapContentSize()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = xxSmall, top = xxSmall, bottom = zero, end = xxSmall)
                .onFocusChanged {
                    cardElevation = if (it.isFocused) medium else xSmall
                },
            shape = RoundedCornerShape(xSmall),
            backgroundColor = if (!isDarkMode() && (cardElevation == medium)) colors.surface.copy(
                alpha = 0.5f
            ) else colors.surface,
            elevation = if (isDarkMode()) cardElevation else zero
        ) {
            Column(modifier = Modifier.padding(xxSmall)) {
                Text(
                    text = rowHeader,
                    style = typography.body1,
                    color = DarkGray
                )

                BasicTextField(
                    modifier = Modifier
                        .padding(end = medium)
                        .fillMaxWidth(),
                    value = text,
                    textStyle = TextStyle(color = colors.onSurface),
                    onValueChange = { editedText ->
                        if (editedText.length <= Dimen.shortTextMaxChars) {
                            setText(editedText)
                        }
                    },
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    interactionSource = interactionSource,
                    decorationBox = @Composable { innerTextField ->
                        TextFieldDefaults.TextFieldDecorationBox(
                            value = text,
                            innerTextField = innerTextField,
                            enabled = true,
                            singleLine = true,
                            visualTransformation = VisualTransformation.None,
                            interactionSource = interactionSource,
                            placeholder = { Text(text = rowHint, color = DarkGray) },
                            contentPadding = PaddingValues(zero)
                        )
                    }
                )
                if (isError) {
                    Text(text = "Please enter in a value", color = Color.Red)
                }
            }
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = xSmall),
            text = "${text.length} / ${Dimen.shortTextMaxChars}",
            textAlign = TextAlign.End,
            style = typography.caption,
            color = if (text.length < Dimen.shortTextMaxChars) Color.Gray else Color.Red,
        )
    }
}
