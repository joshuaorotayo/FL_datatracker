package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.util.DataFieldType
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.ofMaxLength
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.RowEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.states.DataFieldRowState
import com.jorotayo.fl_datatracker.util.TransparentTextField
import com.jorotayo.fl_datatracker.viewModels.DataFieldsViewModel


@Composable
fun DataFieldRow(
    viewModel: DataFieldsViewModel,
    currentDataField: DataField,
    editName: (String) -> Unit,
    editHintText: (String) -> Unit,
    editRowType: (Int) -> Unit,
    checkedChange: (Boolean) -> Unit,
    deleteIcon: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    val items = DataFieldType.values().map { dataFieldType -> dataFieldType.type }
    val icons = DataFieldType.values().map { dataFieldImage -> dataFieldImage.image }

    val optionsMaxChars = 20

    val currentRowState = remember {
        mutableStateOf(
            DataFieldRowState(
                id = currentDataField.dataFieldId,
                fieldName = currentDataField.fieldName,
                fieldHint = currentDataField.fieldHint,
                fieldType = currentDataField.dataFieldType,
                firstValue = currentDataField.first,
                secondValue = currentDataField.second,
                thirdValue = currentDataField.third,
                isEnabled = currentDataField.isEnabled,
                presetId = currentDataField.presetId
            )
        )
    }

    val isHintVisible = remember { mutableStateOf(true) }
    val isEditOptionsVisible = remember { mutableStateOf(false) }
    val isRowEnabled = remember { mutableStateOf(currentRowState.value.isEnabled) }

    val (text, setText) = remember { mutableStateOf(TextFieldValue(currentRowState.value.fieldName)) }

    val (hintText, setHintText) = remember { mutableStateOf(TextFieldValue("")) }

    val firstText = remember { mutableStateOf("") }
    val secondText = remember { mutableStateOf("") }
    val thirdText = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 5.dp, start = 8.dp, end = 8.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(
                if (isSystemInDarkTheme()) {
                    if (isRowEnabled.value) MaterialTheme.colors.surface
                    else MaterialTheme.colors.primary.copy(0.1f)
                } else if (isRowEnabled.value) MaterialTheme.colors.surface
                else MaterialTheme.colors.primary.copy(0.1f)
            )
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        AnimatedVisibility(true) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    modifier = Modifier
                        .weight(3f),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = MaterialTheme.colors.primary,
                        backgroundColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    ),
                    value = text,
                    placeholder = {
                        Text(
                            text = currentRowState.value.fieldName.ifBlank { stringResource(R.string.add_new_data_field) },
                            color = if (text.text.isBlank()) MaterialTheme.colors.primary else Color.Black,
                            textAlign = TextAlign.Center
                        )
                    },
                    onValueChange =
                    { newText ->
                        setText(newText.ofMaxLength(60))
                        editName(newText.text)
                    }
                )
                Row(
                    modifier = Modifier
                        .weight(3f)
                        .wrapContentHeight()
                        .clickable(onClick = { expanded = true }),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier,
                        text = items[currentDataField.dataFieldType],
                        color = MaterialTheme.colors.primary,
                        textAlign = TextAlign.Center
                    )
                    Icon(
                        imageVector = Default.ArrowDropDown,
                        contentDescription = stringResource(R.string.dataField_type_dropdown),
                        tint = MaterialTheme.colors.primary.copy(alpha = 0.5f)
                    )
                }
                DropdownMenu(
                    modifier = Modifier
                        .wrapContentWidth()
                        .background(MaterialTheme.colors.background),
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    items.forEachIndexed { index, s ->
                        DropdownMenuItem(onClick = {
                            editRowType(index)
                            expanded = false
                            isEditOptionsVisible.value = true
                            isEditOptionsVisible.value = false
                            isHintVisible.value = false
                            isHintVisible.value = true
                            currentRowState.value.fieldType = index
                        })
                        {
                            Text(
                                text = s,
                                modifier = Modifier.weight(3f),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colors.primary
                            )
                            Icon(
                                modifier = Modifier
                                    .padding(end = 10.dp)
                                    .weight(1f),
                                imageVector = icons[index],
                                contentDescription = String.format(stringResource(id = R.string.dropdown_icon_description,
                                    items[index])),
                                tint = MaterialTheme.colors.primary
                            )
                        }
                    }
                }

                Checkbox(
                    modifier = Modifier
                        .weight(1.5f),
                    checked = isRowEnabled.value,
                    enabled = true,
                    onCheckedChange = {
                        checkedChange(it)
                        isRowEnabled.value = it
                    },
                    colors = CheckboxDefaults.colors(
                        checkmarkColor = MaterialTheme.colors.onPrimary,
                        uncheckedColor = MaterialTheme.colors.primary,
                        checkedColor = MaterialTheme.colors.primary,
                    )
                )

                IconButton(
                    modifier = Modifier
                        .weight(0.5f),
                    onClick =
                    deleteIcon
                ) {
                    Icon(
                        imageVector = Default.Delete,
                        contentDescription = stringResource(R.string.delete_row_header),
                        tint = MaterialTheme.colors.primary
                    )
                }
            }
        }
        AnimatedVisibility((currentRowState.value.fieldType <= 1 || currentRowState.value.fieldType >= 7) && isHintVisible.value && !isEditOptionsVisible.value) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .weight(8f)
                ) {
                    Text(
                        text = "Hint: ",
                        color = MaterialTheme.colors.primary
                    )
                    Text(
                        text = "${currentDataField.fieldHint}",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.primary
                    )
                }
                IconButton(
                    modifier = Modifier
                        .weight(2f),
                    onClick = {
                        isHintVisible.value = !isHintVisible.value
                        isEditOptionsVisible.value = !isEditOptionsVisible.value
                    },
                ) {
                    Icon(
                        imageVector = Default.Edit,
                        contentDescription = stringResource(R.string.amend_row_hint),
                        tint = MaterialTheme.colors.primary,
                    )
                }
            }
        }
        AnimatedVisibility(visible = (currentRowState.value.fieldType <= 1 || currentRowState.value.fieldType >= 7) && !isHintVisible.value && isEditOptionsVisible.value) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                (if (currentDataField.fieldHint?.isBlank() == true) stringResource(R.string.edit_hint_text) else currentDataField.fieldHint)?.let {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = MaterialTheme.colors.primary,
                            backgroundColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        value = hintText,
                        placeholder = {
                            Text(
                                text = (if (currentRowState.value.fieldHint?.isBlank() == true) String.format(
                                    stringResource(R.string.hint_prompt,
                                        currentRowState.value.fieldName)) else currentRowState.value.fieldHint!!),
                                color = if (hintText.text.isBlank()) MaterialTheme.colors.primary else Color.Black,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        onValueChange =
                        { newText ->
                            setHintText(newText.ofMaxLength(60))
                            editHintText(newText.text)
                        }
                    )
                }
            }
        }

        AnimatedVisibility(currentRowState.value.fieldType == 2 && isHintVisible.value && !isEditOptionsVisible.value) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(modifier = Modifier.wrapContentWidth()) {
                    Text(
                        text = stringResource(R.string.bool_placeholder),
                        color = MaterialTheme.colors.primary
                    )
                    Text(
                        text = "${currentDataField.first.uppercase()}/${
                            currentDataField.second.uppercase()
                        }",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.primary
                    )
                }
                IconButton(
                    modifier = Modifier,
                    onClick = {
                        isHintVisible.value = false
                        isEditOptionsVisible.value = true
                    },
                ) {
                    Icon(
                        imageVector = Default.Edit,
                        contentDescription = stringResource(R.string.amend_bool_value),
                        tint = MaterialTheme.colors.primary,
                    )
                }
            }
        }
        AnimatedVisibility(currentRowState.value.fieldType == 6 && isHintVisible.value && !isEditOptionsVisible.value) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.tristate_placeholder),
                    color = MaterialTheme.colors.primary
                )
                Text(
                    text = "${currentDataField.first.uppercase()}/${currentDataField.second.uppercase()}/${currentDataField.third.uppercase()}",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    modifier = Modifier
                        .weight(2f),
                    onClick = {
                        isHintVisible.value = false
                        isEditOptionsVisible.value = true
                    },
                ) {
                    Icon(
                        imageVector = Default.Edit,
                        contentDescription = stringResource(R.string.amend_tristate_value),
                        tint = MaterialTheme.colors.primary,
                    )
                }
            }
        }

        AnimatedVisibility(currentRowState.value.fieldType == 2 && isEditOptionsVisible.value) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                //boolean text fields for editable
                TransparentTextField(
                    modifier = Modifier.weight(1f),
                    text = firstText.value,
                    label = "1st Value",
                    placeholder = firstText.value.ifBlank { currentRowState.value.firstValue },
                    onValueChange = {
                        if (it.length <= optionsMaxChars) {
                            viewModel.onRowEvent(
                                RowEvent.EditFirstValue(
                                    currentRowState.value.id,
                                    it
                                )
                            )
                            firstText.value = it
                        }
                    }
                )
                TransparentTextField(
                    modifier = Modifier.weight(1f),
                    text = secondText.value,
                    label = "2nd Value",
                    placeholder = secondText.value.ifBlank { currentRowState.value.secondValue },
                    onValueChange = {
                        if (it.length <= optionsMaxChars) {
                            viewModel.onRowEvent(
                                RowEvent.EditSecondValue(
                                    currentRowState.value.id,
                                    it
                                )
                            )
                            secondText.value = it
                        }
                    }
                )
            }
        }
        AnimatedVisibility(currentRowState.value.fieldType == 6 && isEditOptionsVisible.value && !isHintVisible.value) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                //boolean text fields for editable
                TransparentTextField(
                    modifier = Modifier.weight(1f),
                    text = firstText.value,
                    label = "1st Value",
                    placeholder = firstText.value.ifBlank { currentRowState.value.firstValue },
                    onValueChange = {
                        if (it.length <= optionsMaxChars) {
                            viewModel.onRowEvent(
                                RowEvent.EditFirstValue(
                                    currentRowState.value.id,
                                    it
                                )
                            )
                            firstText.value = it
                        }
                    }
                )
                TransparentTextField(
                    modifier = Modifier.weight(1f),
                    text = secondText.value,
                    label = "2nd Value",
                    placeholder = secondText.value.ifBlank { currentRowState.value.secondValue },
                    onValueChange = {
                        if (it.length <= optionsMaxChars) {
                            viewModel.onRowEvent(
                                RowEvent.EditSecondValue(
                                    currentRowState.value.id,
                                    it
                                )
                            )
                            secondText.value = it
                        }
                    }
                )
                TransparentTextField(
                    modifier = Modifier.weight(1f),
                    text = thirdText.value,
                    label = "3rd Value",
                    placeholder = thirdText.value.ifBlank { currentRowState.value.thirdValue },
                    onValueChange = {
                        if (it.length <= optionsMaxChars) {
                            viewModel.onRowEvent(
                                RowEvent.EditThirdValue(
                                    currentRowState.value.id,
                                    it
                                )
                            )
                            thirdText.value = it
                        }
                    }
                )
            }
        }

        AnimatedVisibility(isEditOptionsVisible.value) {
            Row(
                modifier = Modifier
                    .clickable(onClick = {
                        isHintVisible.value = !isHintVisible.value
                        isEditOptionsVisible.value = !isEditOptionsVisible.value
                    })
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier,
                    text = stringResource(R.string.hideEditRowText),
                    color = MaterialTheme.colors.primary.copy(alpha = 0.5f)
                )
                Icon(
                    modifier = Modifier,
                    imageVector = Default.ArrowUpward,
                    tint = MaterialTheme.colors.primary.copy(alpha = 0.7f),
                    contentDescription = stringResource(R.string.hideEditRowText) + " Icon"
                )
            }
        }
    }
}





