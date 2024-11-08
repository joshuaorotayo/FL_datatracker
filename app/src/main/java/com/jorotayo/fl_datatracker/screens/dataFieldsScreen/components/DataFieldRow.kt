package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.util.DataFieldType
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.RowEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.states.DataFieldRowState
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.util.Dimen.optionsMaxChars
import com.jorotayo.fl_datatracker.util.Dimen.small
import com.jorotayo.fl_datatracker.util.Dimen.xSmall
import com.jorotayo.fl_datatracker.util.Dimen.xxSmall
import com.jorotayo.fl_datatracker.util.Dimen.xxxSmall
import com.jorotayo.fl_datatracker.util.TransparentTextField
import com.jorotayo.fl_datatracker.util.ofMaxLength

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Preview(showBackground = true, name = "Light Mode")
@Composable
fun PreviewDataFieldRow() {
    val currentDataField = DataField(
        dataFieldId = 0,
        fieldName = "",
        dataFieldType = 0,
        presetId = 0,
        first = "1st Value",
        second = "2nd Value",
        third = "3rd Value",
        isEnabled = true,
        fieldHint = "Example Hint"
    )
    FL_DatatrackerTheme {
        DataFieldRow(
            currentDataField = currentDataField,
            onRowEvent = {},
            rowIndex = 0,
            deleteIcon = {}
        )
    }
}

@Composable
fun DataFieldRow(
    currentDataField: DataField,
    onRowEvent: (RowEvent) -> Unit,
    rowIndex: Long,
    deleteIcon: () -> Unit,
) {
    val textColour = if (isSystemInDarkTheme()) Color.Gray else MaterialTheme.colors.primary
    var expanded by remember { mutableStateOf(false) }
    val items = DataFieldType.values().map { dataFieldType -> dataFieldType.type }
    val icons = DataFieldType.values().map { dataFieldImage -> dataFieldImage.image }
    val currentRowState =
        remember { mutableStateOf(DataFieldRowState(dataField = currentDataField)) }
    val isHintVisible = remember { mutableStateOf(true) }
    val isEditOptionsVisible = remember { mutableStateOf(false) }
    val isRowEnabled = remember { mutableStateOf(currentRowState.value.dataField.isEnabled) }
    val (text, setText) = remember { mutableStateOf(TextFieldValue(currentRowState.value.dataField.fieldName)) }
    val (hintText, setHintText) = remember { mutableStateOf(TextFieldValue("")) }
    val firstText = remember { mutableStateOf("") }
    val secondText = remember { mutableStateOf("") }
    val thirdText = remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(xSmall),
        shape = RoundedCornerShape(xSmall),
        elevation = small,
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .background(
                    if (isSystemInDarkTheme()) {
                        if (isRowEnabled.value) MaterialTheme.colors.surface
                        else MaterialTheme.colors.primary.copy(0.3f)
                    } else if (isRowEnabled.value) MaterialTheme.colors.surface
                    else MaterialTheme.colors.primary.copy(0.3f)
                )
        ) {
            AnimatedVisibility(true) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = xSmall)
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        modifier = Modifier
                            .weight(4f),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = textColour,
                            backgroundColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        value = text,
                        placeholder = {
                            Text(
                                text = currentRowState.value.dataField.fieldName.ifBlank {
                                    stringResource(
                                        R.string.dataFieldScreen_addFieldName
                                    )
                                },
                                color = if (text.text.isBlank()) textColour else Color.Black,
                                textAlign = TextAlign.Center
                            )
                        },
                        onValueChange =
                        { newText ->
                            setText(newText.ofMaxLength(60))
                            onRowEvent(RowEvent.EditFieldName(rowIndex, newText.text))
                        }
                    )
                    Row(
                        modifier = Modifier
                            .weight(3f)
                            .wrapContentHeight()
                            .clickable { expanded = true },
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier,
                            text = items[currentDataField.dataFieldType],
                            color = textColour,
                            textAlign = TextAlign.Center
                        )
                        Icon(
                            imageVector = Default.ArrowDropDown,
                            contentDescription = stringResource(R.string.dataField_type_dropdown),
                            tint = textColour.copy(alpha = 0.5f)
                        )
                    }
                    DataFieldTypeDropDown(
                        expanded,
                        items,
                        onRowEvent,
                        rowIndex,
                        isEditOptionsVisible,
                        isHintVisible,
                        textColour,
                        icons
                    )
                    Checkbox(
                        modifier = Modifier
                            .padding(end = small)
                            .weight(2f),
                        checked = isRowEnabled.value,
                        enabled = true,
                        onCheckedChange = {
                            onRowEvent(RowEvent.EditIsEnabled(rowIndex))
                            isRowEnabled.value = it
                        },
                        colors = CheckboxDefaults.colors(
                            checkmarkColor = if (isSystemInDarkTheme()) MaterialTheme.colors.primary else MaterialTheme.colors.onPrimary,
                            uncheckedColor = textColour,
                            checkedColor = textColour,
                        )
                    )
                    IconButton(
                        modifier = Modifier
                            .weight(1f),
                        onClick =
                        deleteIcon
                    ) {
                        Icon(
                            imageVector = Default.Delete,
                            contentDescription = stringResource(R.string.delete_row_header),
                            tint = textColour
                        )
                    }
                }
            }
            AnimatedVisibility((currentRowState.value.dataField.dataFieldType <= 1 || currentRowState.value.dataField.dataFieldType >= 7) && isHintVisible.value && !isEditOptionsVisible.value) {

                VisibleHintRow(textColour, currentDataField, isHintVisible, isEditOptionsVisible)
            }
            AnimatedVisibility(visible = (currentRowState.value.dataField.dataFieldType <= 1 || currentRowState.value.dataField.dataFieldType >= 7) && !isHintVisible.value && isEditOptionsVisible.value) {
                HiddenHintRow(
                    currentDataField,
                    textColour,
                    hintText,
                    currentRowState,
                    setHintText,
                    onRowEvent,
                    rowIndex
                )
            }
            AnimatedVisibility(currentRowState.value.dataField.dataFieldType == 2 && isHintVisible.value && !isEditOptionsVisible.value) {
                BooleanHintRow(textColour, currentDataField, isHintVisible, isEditOptionsVisible)
            }
            AnimatedVisibility(currentRowState.value.dataField.dataFieldType == 6 && isHintVisible.value && !isEditOptionsVisible.value) {
                TriStateHintRow(textColour, currentDataField, isHintVisible, isEditOptionsVisible)
            }
            AnimatedVisibility(currentRowState.value.dataField.dataFieldType == 2 && isEditOptionsVisible.value) {
                BooleanHintRow(firstText, currentRowState, onRowEvent, secondText)
            }
            AnimatedVisibility(currentRowState.value.dataField.dataFieldType == 6 && isEditOptionsVisible.value && !isHintVisible.value) {
                TriStateEditHintRow(currentRowState, onRowEvent, firstText, secondText, thirdText)
            }
            AnimatedVisibility(isEditOptionsVisible.value) {
                HideEditRow(isHintVisible, isEditOptionsVisible, textColour)
            }
        }
    }
}

@Composable
private fun DataFieldTypeDropDown(
    expandedMenu: Boolean,
    items: List<String>,
    editRowType: (RowEvent) -> Unit,
    rowIndex: Long,
    isEditOptionsVisible: MutableState<Boolean>,
    isHintVisible: MutableState<Boolean>,
    textColour: Color,
    icons: List<ImageVector>
) {
    var expandedMenu2 = expandedMenu
    DropdownMenu(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colors.background),
        expanded = expandedMenu2,
        onDismissRequest = { expandedMenu2 = false },
    ) {
        items.forEachIndexed { index, s ->
            DropdownMenuItem(onClick = {
                editRowType(RowEvent.EditRowType(rowIndex, index))
                expandedMenu2 = false
                isEditOptionsVisible.value = true
                isEditOptionsVisible.value = false
                isHintVisible.value = false
                isHintVisible.value = true
//                currentRowState.value.fieldType = index
            })
            {
                Text(
                    text = s,
                    modifier = Modifier.weight(3f),
                    textAlign = TextAlign.Center,
                    color = textColour
                )
                Icon(
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .weight(1f),
                    imageVector = icons[index],
                    contentDescription = String.format(
                        stringResource(
                            id = R.string.dropdown_icon_description,
                            items[index]
                        )
                    ),
                    tint = textColour
                )
            }
        }
    }
}

@Composable
private fun VisibleHintRow(
    textColour: Color,
    currentDataField: DataField,
    isHintVisible: MutableState<Boolean>,
    isEditOptionsVisible: MutableState<Boolean>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = small, end = xxxSmall),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Hint: ",
            color = textColour
        )
        Text(
            text = "${currentDataField.fieldHint}",
            fontWeight = FontWeight.Bold,
            color = textColour
        )
        Spacer(modifier = Modifier.weight(1.0f))
        IconButton(
            modifier = Modifier,
            onClick = {
                isHintVisible.value = !isHintVisible.value
                isEditOptionsVisible.value = !isEditOptionsVisible.value
            })
        {
            Icon(
                modifier = Modifier,
                imageVector = Default.Edit,
                contentDescription = stringResource(R.string.amend_row_hint),
                tint = textColour,
            )
        }
    }
}

@Composable
private fun HideEditRow(
    isHintVisible: MutableState<Boolean>,
    isEditOptionsVisible: MutableState<Boolean>,
    textColour: Color
) {
    Row(
        modifier = Modifier
            .clickable(onClick = {
                isHintVisible.value = !isHintVisible.value
                isEditOptionsVisible.value = !isEditOptionsVisible.value
            })
            .fillMaxWidth()
            .padding(vertical = xxSmall),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .padding(end = xxSmall),
            text = stringResource(R.string.hideEditRowText),
            color = textColour.copy(alpha = 0.5f)
        )
        Icon(
            modifier = Modifier,
            imageVector = Default.ArrowUpward,
            tint = textColour.copy(alpha = 0.7f),
            contentDescription = stringResource(R.string.hideEditRowText) + " Icon"
        )
    }
}

@Composable
private fun BooleanHintRow(
    textColour: Color,
    currentDataField: DataField,
    isHintVisible: MutableState<Boolean>,
    isEditOptionsVisible: MutableState<Boolean>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = xSmall),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier.wrapContentWidth()) {
            Text(
                text = stringResource(R.string.bool_placeholder),
                color = textColour
            )
            Text(
                text = "${currentDataField.first.uppercase()}/${
                    currentDataField.second.uppercase()
                }",
                fontWeight = FontWeight.Bold,
                color = textColour
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
                tint = textColour,
            )
        }
    }
}


@Composable
private fun HiddenHintRow(
    currentDataField: DataField,
    textColour: Color,
    hintText: TextFieldValue,
    currentRowState: MutableState<DataFieldRowState>,
    setHintText: (TextFieldValue) -> Unit,
    editHintText: (RowEvent) -> Unit,
    rowIndex: Long
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = xxxSmall),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        (if (currentDataField.fieldHint?.isBlank() == true) stringResource(R.string.edit_hint_text) else currentDataField.fieldHint)?.let {
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = textColour,
                    backgroundColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                value = hintText,
                placeholder = {
                    Text(
                        text = (if (currentRowState.value.dataField.fieldHint?.isBlank() == true) String.format(
                            stringResource(
                                R.string.hint_prompt,
                                currentRowState.value.dataField.fieldName
                            )
                        ) else currentRowState.value.dataField.fieldHint!!),
                        color = if (hintText.text.isBlank()) textColour else Color.Black,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                },
                onValueChange =
                { newText ->
                    setHintText(newText.ofMaxLength(60))
                    editHintText(RowEvent.EditHintText(rowIndex, newText.text))
                }
            )
        }
    }
}

@Composable
private fun TriStateHintRow(
    textColour: Color,
    currentDataField: DataField,
    isHintVisible: MutableState<Boolean>,
    isEditOptionsVisible: MutableState<Boolean>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = xSmall),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.tristate_placeholder),
            color = textColour
        )
        Text(
            text = "${currentDataField.first.uppercase()}/${currentDataField.second.uppercase()}/${currentDataField.third.uppercase()}",
            fontWeight = FontWeight.Bold,
            color = textColour
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
                tint = textColour,
            )
        }
    }
}

@Composable
private fun BooleanHintRow(
    firstText: MutableState<String>,
    currentRowState: MutableState<DataFieldRowState>,
    onRowEvent: (RowEvent) -> Unit,
    secondText: MutableState<String>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = xxxSmall),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        //boolean text fields for editable
        TransparentTextField(
            modifier = Modifier.weight(1f),
            text = firstText.value,
            label = "1st Value",
            placeholder = firstText.value.ifBlank { currentRowState.value.dataField.first },
            onValueChange = {
                if (it.length <= optionsMaxChars) {
                    onRowEvent(
                        RowEvent.EditFirstValue(
                            currentRowState.value.dataField.dataFieldId,
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
            placeholder = secondText.value.ifBlank { currentRowState.value.dataField.second },
            onValueChange = {
                if (it.length <= optionsMaxChars) {
                    onRowEvent(
                        RowEvent.EditSecondValue(
                            currentRowState.value.dataField.dataFieldId,
                            it
                        )
                    )
                    secondText.value = it
                }
            }
        )
    }
}

@Composable
private fun TriStateEditHintRow(
    currentRowState: MutableState<DataFieldRowState>,
    onRowEvent: (RowEvent) -> Unit,
    firstText: MutableState<String>,
    secondText: MutableState<String>,
    thirdText: MutableState<String>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = xxxSmall),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        //boolean text fields for editable
        TransparentTextField(
            modifier = Modifier.weight(1f),
            text = firstText.value,
            label = "1st Value",
            placeholder = firstText.value.ifBlank { currentRowState.value.dataField.first },
            onValueChange = {
                if (it.length <= optionsMaxChars) {
                    onRowEvent(
                        RowEvent.EditFirstValue(
                            currentRowState.value.dataField.dataFieldId,
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
            placeholder = secondText.value.ifBlank { currentRowState.value.dataField.second },
            onValueChange = {
                if (it.length <= optionsMaxChars) {
                    onRowEvent(
                        RowEvent.EditSecondValue(
                            currentRowState.value.dataField.dataFieldId,
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
            placeholder = thirdText.value.ifBlank { currentRowState.value.dataField.third },
            onValueChange = {
                if (it.length <= optionsMaxChars) {
                    onRowEvent(
                        RowEvent.EditThirdValue(
                            currentRowState.value.dataField.dataFieldId,
                            it
                        )
                    )
                    thirdText.value = it
                }
            }
        )
    }
}
