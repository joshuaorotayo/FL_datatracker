package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun PreviewFormNameHeader() {
    FormNameHeader(
        setName = { },
        dataEntryScreenState = DataEntryScreenState(
            dataName = "Test",
            dataRows = listOf<DataRowState>() as MutableList<DataRowState>,
            nameError = true,
        )
    )
}

@Composable
fun FormNameHeader(
    setName: (String) -> Unit,
    dataEntryScreenState: DataEntryScreenState,
) {

    val focusManager = LocalFocusManager.current
    val (text, setText) = remember { mutableStateOf(TextFieldValue(dataEntryScreenState.dataName)) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 16.dp, end = 16.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.surface)
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            isError = dataEntryScreenState.nameError,
            trailingIcon = {
                if (dataEntryScreenState.nameError) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Errored ${dataEntryScreenState.dataName}field",
                    )
                }
            },
            placeholder = {
                Text(
                    text = if (dataEntryScreenState.nameError) "Name Missing" else "Please enter a meeting or Service Name...",
                    color = if (dataEntryScreenState.nameError) Color.Red else MaterialTheme.colors.primary,
                    textAlign = TextAlign.Center
                )
            },
            singleLine = true,
            onValueChange = { newText ->
                setText(newText)
                setName(newText.text)
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                textColor = MaterialTheme.colors.onSurface
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
        )
    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(5.dp)
    )
}