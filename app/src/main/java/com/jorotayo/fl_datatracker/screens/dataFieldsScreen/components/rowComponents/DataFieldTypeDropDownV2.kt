package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components.rowComponents

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.domain.util.DataFieldType
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.RowEvent
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Preview(showBackground = true, name = "Light Mode")
@Composable
fun PreviewDataFieldTypeDropDown() {
    FL_DatatrackerTheme {
        var expanded by remember { mutableStateOf(true) }
        DataFieldTypeDropDownV2(
            isExpanded = expanded,
            onRowEvent = {},
            rowIndex = 0,
            dismissDropdown = {}
        )
    }
}

@Composable
fun DataFieldTypeDropDownV2(
    isExpanded: Boolean,
    onRowEvent: (RowEvent) -> Unit,
    dismissDropdown: () -> Unit,
    rowIndex: Long
) {

    var expanded = isExpanded

    val items = DataFieldType.values().map { dataFieldType -> dataFieldType.type }
    val icons = DataFieldType.values().map { dataFieldImage -> dataFieldImage.image }

    DropdownMenu(
        modifier = Modifier
            .wrapContentSize(),
        expanded = expanded,
        onDismissRequest = dismissDropdown,
    ) {
        items.forEachIndexed { index, iconText ->
            DropdownMenuItem(onClick = {
                onRowEvent(RowEvent.EditRowType(rowIndex, index))
                dismissDropdown()
            })
            {
                Text(
                    modifier = Modifier.weight(3f),
                    text = iconText,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.primary
                )
                Icon(
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .weight(1f),
                    imageVector = icons[index],
                    contentDescription = String.format(
                        stringResource(id = R.string.dropdown_icon_description, items[index])
                    ),
                    tint = MaterialTheme.colors.primary
                )
            }
        }
    }
}
