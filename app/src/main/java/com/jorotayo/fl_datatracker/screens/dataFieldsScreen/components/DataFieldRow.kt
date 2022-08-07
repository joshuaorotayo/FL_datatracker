package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.util.DataFieldType

/*
@Preview
@Composable
fun PreviewEnabledDataFieldRow() {
    val data = DataField(
        id = 0,
        fieldName = "Field Name",
        dataFieldType = 2,
        dataValue = "",
        dataList = listOf("Yes,No"),
        isEnabled = true
    )
    DataFieldRow(
        dataField = data
    )
}

@Preview
@Composable
fun PreviewDiasbledDataFieldRow() {
    val data = DataField(
        id = 0,
        fieldName = "Disabled",
        dataFieldType = 6,
        dataValue = "",
        dataList = listOf("Yes,No"),
        isEnabled = false
    )
    DataFieldRow(
        dataField = data
    )
}
*/

@Composable
fun DataFieldRow(
    dataField: DataField,
    editName: (String) -> Unit,
    editType: (Int) -> Unit,
    checkedChange: (Boolean) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    var selectedIndex = dataField.dataFieldType

    val items = DataFieldType.values().map { dataFieldType -> dataFieldType.type }

    val enabled = dataField.isEnabled

    val fieldName = remember { mutableStateOf(dataField.fieldName) }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(1.dp)
                .background(MaterialTheme.colors.onBackground)
        )
    }
    Row(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .background(if (!enabled) MaterialTheme.colors.primary.copy(0.1f) else MaterialTheme.colors.surface)
            .padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        /*  Text(
              modifier = Modifier
                  .weight(3f),
              text = dataField.fieldName,
              color = MaterialTheme.colors.primary,
              overflow = TextOverflow.Ellipsis,
              textAlign = TextAlign.Center
          )*/
        TextField(
            modifier = Modifier
                .weight(3f),
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colors.primary,
                backgroundColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            //overflow = TextOverflow.Ellipsis,
            value = dataField.fieldName,
            onValueChange = editName
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
            items.forEachIndexed() { index, s ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
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
            checked = dataField.isEnabled,
            enabled = true,
            onCheckedChange = checkedChange,
            colors = CheckboxDefaults.colors(
                checkmarkColor = MaterialTheme.colors.onPrimary,
                uncheckedColor = MaterialTheme.colors.primary,
                checkedColor = MaterialTheme.colors.primary,
            )
        )
    }
}