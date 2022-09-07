package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Preview
@Composable
fun PreviewFormNameHeader() {
    formNameHeader(
        setName = hiltViewModel(),
        dataEntryScreenState = DataEntryScreenState(
            dataName = "Test",
            dataRows = listOf<DataRowState>() as MutableList<DataRowState>,
            nameError = true,
        )
    )
}

@Composable
fun formNameHeader(
    setName: (String) -> Unit,
    dataEntryScreenState: DataEntryScreenState,
): String {
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
            placeholder = {

                if (dataEntryScreenState.nameError) {
                    AnimatedVisibility(visible = true) {
                        Row(modifier = Modifier,
                            horizontalArrangement = Arrangement.Center) {
                            Text(
                                text = "Name Missing",
                                color = Color.Red,
                                textAlign = TextAlign.Center
                            )
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "Errored ${dataEntryScreenState.dataName}field",
                                tint = MaterialTheme.colors.primary
                            )
                        }
                    }
                } else {

                    AnimatedVisibility(visible = true) {
                        Text(
                            text = "Please enter a meeting or Service Name...",
                            color = MaterialTheme.colors.primary
                        )
                    }
                }

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
                disabledIndicatorColor = Color.Transparent,
                textColor = MaterialTheme.colors.onSurface
            )
        )
    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(5.dp)
    )

    return text.text
}