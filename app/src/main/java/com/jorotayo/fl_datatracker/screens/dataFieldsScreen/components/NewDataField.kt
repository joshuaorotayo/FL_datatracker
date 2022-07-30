package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.oldFormELements.ofMaxLength
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.DataFieldEvent
import com.jorotayo.fl_datatracker.util.TransparentTextField
import com.jorotayo.fl_datatracker.viewModels.DataFieldsViewModel

@Preview
@Composable
fun PreviewNewDataField() {
    NewDataField(viewModel = hiltViewModel())
}

@Composable
fun NewDataField(
    viewModel: DataFieldsViewModel
) {
    val maxChar = 30
    val (text, setText) = remember { mutableStateOf(TextFieldValue("")) }

    val focusManager = LocalFocusManager.current

    // Dropdown Menu
    var expanded by remember { mutableStateOf(false) }
    val items =
        listOf("Short String", "Long String ", "Boolean", "Date", "Time", "Count", "Tri-state")
    // val disabledValue = "B"
    var selectedIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(shape = RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.surface)
    ) {

        Text(
            modifier = Modifier
                .padding(vertical = 5.dp, horizontal = 10.dp)
                .fillMaxWidth(),
            text = "New Data Field",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onSurface,
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth(),
            text = "Data Field Name",
            textAlign = TextAlign.Start,
            color = Color.Gray
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth(),
            text = "This name will be formatted and capitalised on save",
            textAlign = TextAlign.Start,
            color = Color.Gray,
            style = MaterialTheme.typography.caption
        )
        Column {
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = text, onValueChange = { newText ->
                    setText(newText.ofMaxLength(maxLength = maxChar))
                },
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = MaterialTheme.colors.surface,
                    backgroundColor = Color.Transparent,
                    textColor = Color.Black
                ),
                placeholder = {
                    Text(
                        text = "Add New Data Field Text",
                        color = if (text.text.isBlank()) MaterialTheme.colors.primary else Color.Black,
                        textAlign = TextAlign.Center
                    )
                },
                maxLines = 1
            )
            //Max Chars count
            Text(
                text = "${text.text.length} / $maxChar",
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.caption,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
                    .background(Color.Transparent)
            )
        }
        Row(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth()
        ) {
            Column {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth(),
                    text = "New Data Field Dropdown",
                    textAlign = TextAlign.Start,
                    color = Color.Gray,
                )
                Text(
                    modifier = Modifier
                        .padding(vertical = 5.dp, horizontal = 10.dp)
                        .fillMaxWidth(),
                    text = "Select the field type that will be used for this data field",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.caption,
                    color = Color.Gray,
                )
                // Dropdown Menu
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center),
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .clip(shape = RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colors.background)
                            .clickable(onClick = { expanded = true }),
                        text = items[selectedIndex],
                        color = MaterialTheme.colors.primary,
                        textAlign = TextAlign.Center
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .wrapContentWidth()
                            .background(
                                MaterialTheme.colors.background
                            )
                    ) {
                        items.forEachIndexed { index, s ->
                            DropdownMenuItem(onClick = {
                                selectedIndex = index
                                expanded = false
                            })
                            {
                                Text(
                                    text = s,
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colors.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }

        AnimatedVisibility(visible = selectedIndex == 2) {
            Row(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth()
            ) {
                TransparentTextField(
                    modifier = Modifier.weight(1f),
                    text = viewModel.duo.second,
                    placeholder = "1st Value",
                    onValueChange = { viewModel.onEvent(DataFieldEvent.PairAddFirstValue(it)) })

                TransparentTextField(
                    modifier = Modifier.weight(1f),
                    text = viewModel.duo.first,
                    placeholder = "2nd Value",
                    onValueChange = { viewModel.onEvent(DataFieldEvent.PairAddSecondValue(it)) })
            }
        }

        AnimatedVisibility(visible = selectedIndex == 6) {
            Row(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TransparentTextField(
                    modifier = Modifier.weight(0.3f),
                    text = viewModel.tri.first,
                    placeholder = if (viewModel.tri.first.isNullOrEmpty()) "1st Value" else viewModel.tri.first,
                    onValueChange = { viewModel.onEvent(DataFieldEvent.TriAddFirstValue(it)) })

                TransparentTextField(
                    modifier = Modifier.weight(0.3f),
                    text = viewModel.tri.second,
                    placeholder = if (viewModel.tri.second.isNullOrEmpty()) "2nd Value" else viewModel.tri.second,
                    onValueChange = { viewModel.onEvent(DataFieldEvent.TriAddSecondValue(it)) })

                TransparentTextField(
                    modifier = Modifier.weight(0.3f),
                    text = viewModel.tri.third,
                    placeholder = if (viewModel.tri.third.isNullOrEmpty()) "3rd Value" else viewModel.tri.third,
                    onValueChange = { viewModel.onEvent(DataFieldEvent.TriAddThirdValue(it)) })
            }
        }
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                modifier = Modifier
                    .background(MaterialTheme.colors.primary),
                onClick = {
                    viewModel.onEvent(DataFieldEvent.SaveDataField)
                })
            {
                Text(
                    text = "Save Data Field"
                )
            }
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
        )
    }
}