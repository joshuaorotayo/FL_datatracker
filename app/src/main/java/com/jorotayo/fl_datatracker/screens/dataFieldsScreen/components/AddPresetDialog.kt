package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.R

@Preview
@Composable
fun PreviewAddPresetDialog() {
    val state = remember { mutableStateOf(true) }
    AddPresetDialog(
        state = state,
        modifier = Modifier,
        addPreset = {}
    )
}


@Composable
fun AddPresetDialog(
    state: MutableState<Boolean>,
    modifier: Modifier,
    addPreset: (String) -> Unit,
) {

    val presetText = remember { mutableStateOf("") }

    if (state.value) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
            elevation = 8.dp
        ) {
            Column(
                modifier
                    .background(MaterialTheme.colors.background),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.addPresetHeader),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.h6
                )
                Row(
                    Modifier
                        .fillMaxWidth(0.9f)
                        .padding(vertical = 10.dp)
                ) {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        textStyle = MaterialTheme.typography.h6,
                        value = presetText.value,
                        maxLines = 1,
                        placeholder = {
                            Text(
                                modifier = Modifier
                                    .wrapContentHeight(),
                                text = stringResource(R.string.enterPresetPlaceholder),
                                style = MaterialTheme.typography.h6,
                                color = MaterialTheme.colors.primary,
                                textAlign = TextAlign.Center
                            )
                        },
                        onValueChange = {
                            presetText.value = it
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = MaterialTheme.colors.surface,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            textColor = Color.Gray
                        )
                    )
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .background(MaterialTheme.colors.surface),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {

                    TextButton(onClick = {
                        state.value = false
                    }) {
                        Text(
                            text = stringResource(R.string.cancelText),
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray,
                            modifier = Modifier.padding(vertical = 5.dp)
                        )
                    }
                    TextButton(onClick = {
                        state.value = false
                        addPreset(presetText.value)
                        // confirmDelete(dataField)
                    }) {
                        Text(
                            text = stringResource(R.string.addPresetBtn),
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colors.primary,
                            modifier = Modifier.padding(vertical = 5.dp)
                        )
                    }
                }
            }
        }
    }
}