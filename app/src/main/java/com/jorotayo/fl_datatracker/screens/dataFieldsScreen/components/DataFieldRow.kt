package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.util.DataFieldType
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.oldFormELements.ofMaxLength

//@Preview
//@Composable
//fun PreviewEnabledDataFieldRow() {
//    val data = DataField(
//        id = 0,
//        fieldName = "Field Name",
//        dataFieldType = 2,
//        dataValue = "",
//        dataList = listOf("Yes,No,NA"),
//        isEnabled = true
//    )
//    DataFieldRow(
//        dataField = data,
//        editName = { data.fieldName = it },
//        editType = { data.dataFieldType = it },
//        checkedChange = { data.isEnabled = !data.isEnabled },
//        editStateValues = { Pair(it.first, it.second) },
//    )
//}
//
//@Preview
//@Composable
//fun PreviewDisabledDataFieldRow() {
//    val data = DataField(
//        id = 0,
//        fieldName = "Field Name",
//        dataFieldType = 2,
//        dataValue = "",
//        dataList = listOf("Yes,No,NA"),
//        isEnabled = false
//    )
//    DataFieldRow(
//        dataField = data,
//        editName = { data.fieldName = it },
//        editType = { data.dataFieldType = it },
//        checkedChange = { data.isEnabled = !data.isEnabled },
//        editStateValues = { Pair(it.first, it.second) },
//        editOptions = false,
//        editHint = false,
//        amendHint = {}
//    )
//}

@Composable
fun DataFieldRow(
    dataField: DataField,
    editName: (String) -> Unit,
    editHintText: (String) -> Unit,
    editType: (Int) -> Unit,
    checkedChange: (Boolean) -> Unit,
    editStateValues: (Pair<Int, String>) -> Unit, //int is the field number, string is edited value
    editHint: Boolean,
    editOptions: Boolean,
    toggleHint: () -> Unit //toggles view
) {
    var expanded by remember { mutableStateOf(false) }

    val items = DataFieldType.values().map { dataFieldType -> dataFieldType.type }

    val enabled = remember { mutableStateOf(dataField.isEnabled) }

    val fieldName = dataField.fieldName
    val (text, setText) = remember { mutableStateOf(TextFieldValue("")) }

    val fieldHint = dataField.fieldHint
    val (editHintText, setHintText) = remember { mutableStateOf(TextFieldValue("")) }

    val optionsMaxChars = 20

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 8.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(if (!enabled.value) MaterialTheme.colors.primary.copy(0.1f) else MaterialTheme.colors.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
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
                        text = fieldName.ifBlank { "Add New Data Field Text" },
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
                    text = items[dataField.dataFieldType],
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
                modifier = Modifier
                    .wrapContentWidth()
                    .background(MaterialTheme.colors.background),
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                items.forEachIndexed { index, s ->
                    DropdownMenuItem(onClick = {
                        editType(index)
                        expanded = false
                    })
                    {
                        Text(
                            text = s,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
            }

            Checkbox(
                modifier = Modifier
                    .weight(1.5f),
                checked = enabled.value,
                enabled = true,
                onCheckedChange = {
                    checkedChange(it)
                    enabled.value = it
                },
                colors = CheckboxDefaults.colors(
                    checkmarkColor = MaterialTheme.colors.onPrimary,
                    uncheckedColor = MaterialTheme.colors.primary,
                    checkedColor = MaterialTheme.colors.primary,
                )
            )
        }
        AnimatedVisibility(dataField.dataFieldType <= 1 && !editHint) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.weight(7f),
                    text = "'${dataField.fieldHint}'",
                    color = MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    modifier = Modifier
                        .weight(2f),
                    onClick = toggleHint,
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Amend row Hint",
                        tint = MaterialTheme.colors.onSurface,
                    )
                }
            }
        }
        AnimatedVisibility(visible = dataField.dataFieldType <= 1 && editHint) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                (if (dataField.fieldHint?.isBlank() == true) "Edit the Hint Text" else dataField!!.fieldHint)?.let { hint ->
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = MaterialTheme.colors.primary,
                            backgroundColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        value = editHintText,
                        placeholder = {
                            Text(
                                text = if (fieldHint.isNullOrEmpty()) "Please enter Hint for field: $fieldName" else fieldHint,
                                color = if (editHintText.text.isBlank()) MaterialTheme.colors.primary else Color.Black,
                                textAlign = TextAlign.Center
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
        AnimatedVisibility(dataField.dataFieldType == 2 && !editOptions) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            ) {
                Text(
                    text = "Boolean: ",
                    color = MaterialTheme.colors.onSurface
                )
                Text(
                    text = "${dataField.dataList?.get(0)?.uppercase()}/${
                        dataField.dataList?.get(1)?.uppercase()
                    }",
                    color = MaterialTheme.colors.primary
                )
            }
        }
        AnimatedVisibility(dataField.dataFieldType == 6 && !editOptions) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            ) {
                Text(
                    text = "Tristate: ",
                    color = MaterialTheme.colors.onSurface
                )
                Text(
                    text = "${dataField.dataList?.get(0)?.uppercase()}/${
                        dataField.dataList?.get(1)?.uppercase()
                    }/${dataField.dataList?.get(2)?.uppercase()}",
                    color = MaterialTheme.colors.primary
                )
            }
        }

        AnimatedVisibility(dataField.dataFieldType == 2 && editOptions) {
            EditBoolValues(
                dataField = dataField,
                optionsMaxChars = optionsMaxChars,
                editStateValues = editStateValues
            )
        }
        AnimatedVisibility(dataField.dataFieldType == 6 && editOptions) {
            EditTriValues(
                dataField = dataField,
                optionsMaxChars = optionsMaxChars,
                editStateValues = editStateValues
            )
        }
    }
}


