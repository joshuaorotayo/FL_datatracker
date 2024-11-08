package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components.rowComponents

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
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.util.DataFieldType
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.DataFieldEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.RowEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.states.DataFieldRowState
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.util.Dimen
import com.jorotayo.fl_datatracker.util.Dimen.xSmall
import com.jorotayo.fl_datatracker.util.Dimen.xxSmall
import com.jorotayo.fl_datatracker.util.TransparentTextField
import com.jorotayo.fl_datatracker.util.exampleShortDataRowState
import com.jorotayo.fl_datatracker.util.getHeaderColour
import com.jorotayo.fl_datatracker.util.ofMaxLength

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Preview(showBackground = true, name = "Light Mode")
@Composable
private fun PreviewRowDetails() {
    val example = remember {
        mutableStateOf(exampleShortDataRowState)
    }
    FL_DatatrackerTheme {
        RowDetails(
            rowData = example,
            onRowEvent = {}
        ) {}
    }
}

@Composable
fun RowDetails(
    rowData: MutableState<DataFieldRowState>,
    onRowEvent: (RowEvent) -> Unit,
    onDataFieldEvent: (DataFieldEvent) -> Unit
) {
    val textColour = if (isSystemInDarkTheme()) Color.Gray else MaterialTheme.colors.primary
    val isRowEnabled = remember { mutableStateOf(rowData.value.dataField.isEnabled) }
    var expanded by remember { mutableStateOf(false) }
    val items = DataFieldType.values().map { dataFieldType -> dataFieldType.type }
    val icons = DataFieldType.values().map { dataFieldImage -> dataFieldImage.image }
    val isHintVisible = remember { mutableStateOf(true) }
    val isEditOptionsVisible = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .background(
                if (isSystemInDarkTheme()) {
                    if (isRowEnabled.value) MaterialTheme.colors.surface
                    else MaterialTheme.colors.primary.copy(0.3f)
                } else if (isRowEnabled.value) MaterialTheme.colors.surface
                else MaterialTheme.colors.primary.copy(0.3f)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(xSmall),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(end = xxSmall),
                imageVector = Icons.Default.Edit,
                tint = MaterialTheme.colors.primary,
                contentDescription = "Edit Field Name icon"
            )
            Text(
                modifier = Modifier
                    .weight(0.30f),
                text = rowData.value.dataField.fieldName,
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.body2,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                modifier = Modifier
                    .weight(0.35f)
                    .clickable(
                        onClick = {
                            expanded = true
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .padding(end = xSmall),
                    imageVector = icons[rowData.value.dataField.dataFieldType],
                    contentDescription = stringResource(R.string.dataField_type_dropdown),
                    tint = MaterialTheme.colors.primary
                )
                Text(
                    modifier = Modifier,
                    text = items[rowData.value.dataField.dataFieldType],
                    color = MaterialTheme.colors.onSurface,
                    style = MaterialTheme.typography.body2
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = stringResource(R.string.dataField_type_dropdown),
                    tint = MaterialTheme.colors.onSurface
                )
                DataFieldTypeDropDownV2(
                    isExpanded = expanded,
                    onRowEvent = onRowEvent,
                    rowIndex = rowData.value.dataField.dataFieldId,
                    dismissDropdown = { expanded = false }
                )
            }
            Row(
                modifier = Modifier
                    .weight(0.2f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Checkbox(
                    modifier = Modifier
                        .fillMaxWidth(0.5f),
                    checked = isRowEnabled.value,
                    enabled = true,
                    onCheckedChange = {
                        isRowEnabled.value = !isRowEnabled.value
                        onRowEvent(RowEvent.ToggleRow(rowData.value.dataField.dataFieldId))
                    },
                    colors = CheckboxDefaults.colors(
                        checkmarkColor = if (isSystemInDarkTheme()) MaterialTheme.colors.primary else MaterialTheme.colors.onPrimary,
                        uncheckedColor = textColour,
                        checkedColor = textColour,
                    )
                )
                IconButton(
                    modifier = Modifier
                        .fillMaxWidth(0.5f),
                    onClick = {
                        onDataFieldEvent(DataFieldEvent.ShowDeleteRowDialog(rowData.value.dataField))
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.delete_row_header),
                        tint = textColour
                    )
                }
            }
        }
        AnimatedVisibility(visible = isHintVisible.value) {
            SelectHintType(rowData.value, textColour, isHintVisible, isEditOptionsVisible)
        }
        AnimatedVisibility(visible = !isHintVisible.value && isEditOptionsVisible.value) {
            SelectEditType(rowData.value, onRowEvent, textColour)
        }
        AnimatedVisibility(visible = !isHintVisible.value && isEditOptionsVisible.value) {
            HideEditRow(isHintVisible, isEditOptionsVisible)
        }
    }
}


@Composable
fun SelectHintType(
    rowData: DataFieldRowState,
    textColour: Color,
    isHintVisible: MutableState<Boolean>,
    isEditOptionsVisible: MutableState<Boolean>
) {
    when (rowData.dataField.dataFieldType) {
        DataFieldType.SHORT_TEXT.ordinal -> {
            BasicVisibleHint(
                rowData.dataField.fieldHint,
                textColour,
                isHintVisible,
                isEditOptionsVisible
            )
        }

        DataFieldType.LONG_TEXT.ordinal -> {
            BasicVisibleHint(
                rowData.dataField.fieldHint,
                textColour,
                isHintVisible,
                isEditOptionsVisible
            )
        }

        DataFieldType.LIST.ordinal -> {
            BasicVisibleHint(
                rowData.dataField.fieldHint,
                textColour,
                isHintVisible,
                isEditOptionsVisible
            )
        }

        DataFieldType.BOOLEAN.ordinal -> {
            BooleanHintRow(rowData.dataField, isHintVisible, isEditOptionsVisible)
        }

        DataFieldType.TRISTATE.ordinal -> {
            TriStateHintRow(rowData.dataField, isHintVisible, isEditOptionsVisible)
        }
    }
}

@Composable
fun BasicVisibleHint(
    fieldHint: String?,
    textColour: Color,
    isHintVisible: MutableState<Boolean>,
    isEditOptionsVisible: MutableState<Boolean>
//    onDataFieldEvent: (DataFieldEvent) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = xSmall, vertical = Dimen.zero),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Hint: ",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.body2
        )
        Text(
            text = "$fieldHint",
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.body2
        )
        Spacer(modifier = Modifier.weight(1.0f))
        IconButton(
            modifier = Modifier,
            onClick = {
                isHintVisible.value = false
                isEditOptionsVisible.value = true
            })
        {
            Icon(
                modifier = Modifier,
                imageVector = Icons.Default.Edit,
                contentDescription = stringResource(R.string.amend_row_hint),
                tint = textColour,
            )
        }
    }
}

@Composable
private fun BooleanHintRow(
    currentDataField: DataField,
    isHintVisible: MutableState<Boolean>,
    isEditOptionsVisible: MutableState<Boolean>
) {
    val textColour = getHeaderColour(isSystemInDarkTheme())

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = xSmall, vertical = Dimen.zero),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.bool_placeholder),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.body2
        )
        Text(
            text = "${currentDataField.first.uppercase()}/${currentDataField.second.uppercase()}",
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.body2
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            modifier = Modifier,
            onClick = {
                isHintVisible.value = false
                isEditOptionsVisible.value = true
            },
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = stringResource(R.string.amend_bool_value),
                tint = textColour,
            )
        }
    }
}

@Composable
private fun TriStateHintRow(
    currentDataField: DataField,
    isHintVisible: MutableState<Boolean>,
    isEditOptionsVisible: MutableState<Boolean>
) {
    val textColour = getHeaderColour(isSystemInDarkTheme())

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = xSmall, vertical = Dimen.zero),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.tristate_placeholder),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.body2
        )
        Text(
            text = "${currentDataField.first.uppercase()}/${currentDataField.second.uppercase()}/${currentDataField.third.uppercase()}",
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.body2
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            modifier = Modifier,
            onClick = {
                isHintVisible.value = false
                isEditOptionsVisible.value = true
            })
        {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = stringResource(R.string.amend_tristate_value),
                tint = textColour,
            )
        }
    }
}


@Composable
fun SelectEditType(
    rowData: DataFieldRowState,
    onRowEvent: (RowEvent) -> Unit,
    textColour: Color
) {

    Column(
        modifier = Modifier.wrapContentHeight()
    ) {

        when (rowData.dataField.dataFieldType) {
            DataFieldType.SHORT_TEXT.ordinal -> {
                BasicEditHint(rowData, onRowEvent, textColour)
            }

            DataFieldType.LONG_TEXT.ordinal -> {
                BasicEditHint(rowData, onRowEvent, textColour)
            }

            DataFieldType.LIST.ordinal -> {
                BasicEditHint(rowData, onRowEvent, textColour)
            }

            DataFieldType.BOOLEAN.ordinal -> {
                BooleanEditHint(rowData, onRowEvent)
            }

            DataFieldType.TRISTATE.ordinal -> {
                TriStateEditHint(rowData, onRowEvent)
            }
        }
    }

}

@Composable
fun BasicEditHint(
    currentRowState: DataFieldRowState,
    onRowEvent: (RowEvent) -> Unit,
    textColour: Color,
) {
    val (hintText, setHintText) = remember { mutableStateOf(TextFieldValue("")) }
    val fieldHint =
        if (currentRowState.dataField.fieldHint.isNullOrBlank()) "Enter value for" else currentRowState.dataField.fieldHint
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(bottom = Dimen.xxxSmall),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextField(
            modifier = Modifier
                .wrapContentHeight()
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
                    text = fieldHint!!,
                    color = if (hintText.text.isBlank()) textColour else Color.Black,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            },
            onValueChange = { newText ->
                setHintText(newText.ofMaxLength(60))
                onRowEvent(
                    RowEvent.EditHintText(
                        currentRowState.dataField.dataFieldId,
                        newText.text
                    )
                )
            }
        )
    }
}

@Composable
private fun BooleanEditHint(
    currentRowState: DataFieldRowState,
    onRowEvent: (RowEvent) -> Unit
) {
    val firstText = remember { mutableStateOf(currentRowState.dataField.first) }
    val secondText = remember { mutableStateOf(currentRowState.dataField.second) }
    val optionsMaxChars = 20

    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(start = xSmall, top = Dimen.zero, end = xSmall, bottom = xSmall),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        //boolean text fields for editable
        TransparentTextField(
            modifier = Modifier.weight(1f),
            text = firstText.value,
            label = "1st Value",
            placeholder = firstText.value.ifBlank { "" },
            onValueChange = {
                if (it.length <= optionsMaxChars) {
                    onRowEvent(
                        RowEvent.EditFirstValue(
                            currentRowState.dataField.dataFieldId,
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
            placeholder = secondText.value.ifBlank { "" },
            onValueChange = {
                if (it.length <= optionsMaxChars) {
                    onRowEvent(
                        RowEvent.EditSecondValue(
                            currentRowState.dataField.dataFieldId,
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
private fun TriStateEditHint(
    currentRowState: DataFieldRowState,
    onRowEvent: (RowEvent) -> Unit,
) {
    val firstText = remember { mutableStateOf(currentRowState.dataField.first) }
    val secondText = remember { mutableStateOf(currentRowState.dataField.second) }
    val thirdText = remember { mutableStateOf(currentRowState.dataField.third) }
    val optionsMaxChars = 20

    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(start = xSmall, top = Dimen.zero, end = xSmall, bottom = xSmall),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        //boolean text fields for editable
        TransparentTextField(
            modifier = Modifier.weight(1f),
            text = firstText.value,
            label = "1st Value",
            placeholder = firstText.value.ifBlank { "" },
            onValueChange = {
                if (it.length <= optionsMaxChars) {
                    firstText.value = it
                    onRowEvent(
                        RowEvent.EditFirstValue(
                            currentRowState.dataField.dataFieldId,
                            it
                        )
                    )
                }
            }
        )
        TransparentTextField(
            modifier = Modifier.weight(1f),
            text = secondText.value,
            label = "2nd Value",
            placeholder = secondText.value.ifBlank { "" },
            onValueChange = {
                if (it.length <= optionsMaxChars) {
                    secondText.value = it
                    onRowEvent(
                        RowEvent.EditSecondValue(
                            currentRowState.dataField.dataFieldId,
                            it
                        )
                    )
                }
            }
        )
        TransparentTextField(
            modifier = Modifier.weight(1f),
            text = thirdText.value,
            label = "3rd Value",
            placeholder = thirdText.value.ifBlank { "" },
            onValueChange = {
                if (it.length <= optionsMaxChars) {
                    onRowEvent(
                        RowEvent.EditThirdValue(
                            currentRowState.dataField.dataFieldId,
                            it
                        )
                    )
                    thirdText.value = it
                }
            }
        )
    }
}

@Composable
private fun HideEditRow(
    isHintVisible: MutableState<Boolean>,
    isEditOptionsVisible: MutableState<Boolean>
) {
    Row(
        modifier = Modifier
            .clickable(
                onClick = {
                    isHintVisible.value = true
                    isEditOptionsVisible.value = false
                }
            )
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .padding(end = xxSmall),
            text = stringResource(R.string.hideEditRowText),
            color = MaterialTheme.colors.primary.copy(alpha = 0.7f),
            style = MaterialTheme.typography.body2
        )
        Icon(
            imageVector = Icons.Default.ArrowUpward,
            tint = MaterialTheme.colors.primary.copy(alpha = 0.7f),
            contentDescription = stringResource(R.string.hideEditRowText) + " Icon"
        )
    }
}
