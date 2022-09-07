package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.util.DataFieldType
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.ofMaxLength
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.DataFieldEvent
import com.jorotayo.fl_datatracker.util.TransparentTextField
import com.jorotayo.fl_datatracker.viewModels.DataFieldsViewModel

@Preview
@Composable
fun PreviewNewDataField() {
//    NewDataField(viewModel = hiltViewModel())
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NewDataField(
    viewModel: DataFieldsViewModel,
    onClick: (DataField) -> Unit
) {
    val optionsMaxChars = 20
    val maxChar = 30

    // Dropdown Menu
    var expanded by remember { mutableStateOf(false) }

    val items = DataFieldType.values().map { dataFieldType -> dataFieldType.type }

    val maxHintChar = 60
    val (hintText, setHintText) = remember { mutableStateOf(TextFieldValue("")) }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val newDataField = viewModel.newDataField.value

    val newData = DataField(
        dataFieldId = 0,
        presetId = 0,
        fieldName = newDataField.fieldName,
        dataFieldType = newDataField.fieldType,
        fieldHint = newDataField.fieldHint,
        first = newDataField.firstValue,
        second = newDataField.secondValue,
        third = newDataField.thirdValue,
        isEnabled = true
    )

    Column(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(shape = RoundedCornerShape(10.dp))
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
                value = newDataField.fieldName,
                onValueChange = {
                    viewModel.onDataEvent(DataFieldEvent.AddFieldName(it))
                },
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = MaterialTheme.colors.surface,
                    backgroundColor = Color.Transparent,
                    textColor = MaterialTheme.colors.onSurface
                ),
                placeholder = {
                    Text(
                        text = "Add New Data Field Text",
                        color = if (newDataField.fieldName.isBlank()) MaterialTheme.colors.primary else Color.Black,
                        textAlign = TextAlign.Center
                    )
                },
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
            )
            //Max Chars count
            Text(
                text = "${newDataField.fieldName.length} / $maxChar",
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.caption,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp, end = 10.dp)
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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .clickable(onClick = { expanded = true })
                            .clip(shape = RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colors.onBackground),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier,
                            text = items[newDataField.fieldType],
                            color = MaterialTheme.colors.primary,
                            textAlign = TextAlign.Center
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Drop down arrow for Field Type Dropdown",
                            tint = MaterialTheme.colors.primary.copy(alpha = 0.5f)
                        )
                    }
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
                                viewModel.onDataEvent(DataFieldEvent.SelectFieldType(index))
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
        AnimatedVisibility(visible = newDataField.fieldType <= 1) { // ShortString = int 0, LongString = int 1
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp, start = 5.dp, end = 5.dp)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, bottom = 5.dp),
                    text = "Data Field Hint",
                    textAlign = TextAlign.Start,
                    color = Color.Gray
                )
                //Button Data capture
                TextField(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    value = hintText,
                    onValueChange = { newText ->
                        setHintText(newText.ofMaxLength(maxLength = maxHintChar))
                        viewModel.onDataEvent(DataFieldEvent.AddHintText(newText.text))
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = MaterialTheme.colors.surface,
                        backgroundColor = Color.Transparent,
                        textColor = MaterialTheme.colors.onSurface
                    ),
                    singleLine = true,
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            keyboardController?.hide()
                            onClick(newData)
                        }
                    ),
                    placeholder = {
                        Text(
                            text = "Please enter hint text for Data Field",
                            color = if (hintText.text.isBlank()) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface,
                            textAlign = TextAlign.Start
                        )
                    }
                )
                //Max Chars count
                Text(
                    text = "${hintText.text.length} / $maxHintChar",
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.caption,
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 10.dp)
                        .background(Color.Transparent)
                )
            }
        }
        AnimatedVisibility(visible = newDataField.fieldType == 2) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp, start = 5.dp, end = 5.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp)
                ) {
                    Text(
                        text = "Enter in the values for the boolean e.g. Yes and No",
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.caption,
                        color = Color.Gray
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TransparentTextField(
                        modifier = Modifier.weight(1f),
                        text = newDataField.firstValue,
                        label = "1st Value",
                        placeholder = newDataField.firstValue.ifBlank { "1st Value" },
                        onValueChange = {
                            if (it.length <= optionsMaxChars) viewModel.onDataEvent(
                                DataFieldEvent.AddFirstValue(it)
                            )
                        }
                    )
                    TransparentTextField(
                        modifier = Modifier.weight(1f),
                        text = newDataField.secondValue,
                        label = "2nd Value",
                        placeholder = newDataField.secondValue.ifBlank { "2nd Value" },
                        onValueChange = {
                            if (it.length <= optionsMaxChars) viewModel.onDataEvent(
                                DataFieldEvent.AddSecondValue(it)
                            )
                        }
                    )
                }
            }
        }

        AnimatedVisibility(visible = newDataField.fieldType == 6) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp, start = 5.dp, end = 5.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp)
                ) {
                    Text(
                        text = "Enter in the values for the Tri-state e.g. No, N/A and Yes",
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.caption,
                        color = Color.Gray
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TransparentTextField(
                        modifier = Modifier.weight(1f),
                        text = newDataField.firstValue,
                        label = "1st Value",
                        placeholder = newDataField.firstValue.ifBlank { "1st Value" },
                        onValueChange = {
                            if (it.length <= optionsMaxChars) viewModel.onDataEvent(
                                DataFieldEvent.AddFirstValue(it)
                            )
                        }
                    )
                    TransparentTextField(
                        modifier = Modifier.weight(1f),
                        text = newDataField.secondValue,
                        label = "2nd Value",
                        placeholder = "2nd Value",
                        onValueChange = {
                            if (it.length <= optionsMaxChars) viewModel.onDataEvent(
                                DataFieldEvent.AddSecondValue(it)
                            )
                        }
                    )
                    TransparentTextField(
                        modifier = Modifier.weight(1f),
                        text = newDataField.thirdValue,
                        label = "3rd Value",
                        placeholder = "3rd Value",
                        onValueChange = {
                            if (it.length <= optionsMaxChars) viewModel.onDataEvent(
                                DataFieldEvent.AddThirdValue(it)
                            )
                        }
                    )
                }
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
                    onClick(newData)
                }
            )
            {
                Text(
                    modifier = Modifier,
                    text = "Save Data Field"
                )
            }
        }
    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
    )
}
