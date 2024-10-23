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
import androidx.compose.ui.unit.dp
import com.dsc.form_builder.TextFieldState
import com.dsc.form_builder.Validators
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.theme.AppTheme
import com.jorotayo.fl_datatracker.ui.theme.AppTheme.dimens
import com.jorotayo.fl_datatracker.ui.theme.isDarkMode


@DefaultPreviews
@Composable
fun PreviewMinimalShortTextField() {
    AppTheme {
        Column {
            minimalShortTextField(
                rowHeader = "Header text for textfield",
                rowHint = "Please enter text for row",
                textFieldState = TextFieldState(
                    name = "",
                    initial = "",
                    transform = null,
                    validators = listOf(Validators.Required())
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun minimalShortTextField(
    rowHeader: String,
    rowHint: String,
    textFieldState: TextFieldState
): String {

    val (text, setText) = remember { mutableStateOf(textFieldState.initial) }
    var cardElevation by remember { mutableStateOf(12.dp) }
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    Column(modifier = Modifier.wrapContentSize()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(
                    start = dimens.xxSmall,
                    top = dimens.xSmall,
                    bottom = dimens.zero,
                    end = dimens.xxSmall
                )
                .onFocusChanged {
                    cardElevation = if (it.isFocused) 32.dp else 12.dp
                },
            shape = RoundedCornerShape(dimens.xSmall),
            backgroundColor = if (!isDarkMode() && (cardElevation == dimens.medium)) colors.surface.copy(
                alpha = 0.5f
            ) else colors.surface,
            elevation = if (isDarkMode()) cardElevation else dimens.zero
        ) {
            Column(modifier = Modifier.padding(dimens.xxSmall)) {
                Text(
                    text = rowHeader,
                    style = typography.body1,
                    color = DarkGray
                )

                BasicTextField(
                    modifier = Modifier
                        .padding(end = dimens.medium)
                        .fillMaxWidth(),
                    value = text,
                    textStyle = TextStyle(color = colors.onSurface),
                    onValueChange = { editedText ->
                        if (editedText.length <= 15) {
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
                            contentPadding = PaddingValues(dimens.zero)
                        )
                    }
                )
                if (textFieldState.hasError) {
                    Text(text = "Please enter in a value", color = Color.Red)
                }
            }
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = dimens.xSmall),
            text = "${text.length} / ${15}",
            textAlign = TextAlign.End,
            style = typography.caption,
            color = if (text.length < 15) Color.Gray else Color.Red,
        )
    }
    return text
}
