package com.jorotayo.fl_datatracker.screens.dataFieldsScreenRework.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.jorotayo.fl_datatracker.screens.dataFieldsScreenRework.DataFieldsReworkState
import com.jorotayo.fl_datatracker.ui.DefaultDualPreview
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.ui.theme.isDarkMode
import com.jorotayo.fl_datatracker.util.Dimen.medium
import com.jorotayo.fl_datatracker.util.Dimen.xSmall
import com.jorotayo.fl_datatracker.util.Dimen.xxSmall
import com.jorotayo.fl_datatracker.util.Dimen.zero


@DefaultDualPreview
@Composable
fun PreviewMinimalSelectionField() {
    FL_DatatrackerTheme {
        Column {
            minimalSelectionField(
                rowHeader = "this is a sample row",
                isError = false,
                state = DataFieldsReworkState(),
                selectionList = listOf("hi", "hello", "test")
            )
        }
    }
}

@Composable
fun minimalSelectionField(
    rowHeader: String,
    isError: Boolean,
    state: DataFieldsReworkState,
    selectionList: List<String>
): String {

    var expanded by remember { mutableStateOf(state.isDropdownExpanded) }
    var cardElevation by remember { mutableStateOf(xSmall) }
    val focusManager = LocalFocusManager.current
    var isExpanded by remember { mutableStateOf(state.isDropdownExpanded) }
    var selectedItem by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .wrapContentSize()
    )
    {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(xxSmall)
                .onFocusChanged {
                    cardElevation = if (it.isFocused || state.isDropdownExpanded) medium else xSmall
                },
            shape = RoundedCornerShape(xSmall),
            backgroundColor = if (!isDarkMode() && cardElevation == medium) colors.surface.copy(
                alpha = 0.5f
            ) else colors.surface,
            elevation = if (isDarkMode()) cardElevation else zero
        ) {
            Column(
                modifier = Modifier.wrapContentWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clickable(onClick = {
                            expanded = !expanded
                        }),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .padding(xxSmall)
                            .fillMaxWidth(0.9f)
                    ) {
                        Text(
                            text = rowHeader,
                            style = typography.body1,
                            color = DarkGray
                        )
                        Text(
                            modifier = Modifier,
                            text = selectedItem.ifBlank { "Please select an option" },
                            style = typography.body1,
                            color = if (selectedItem.isBlank()) DarkGray else colors.onSurface
                        )
                    }
                    Column(
                        modifier = Modifier
                            .padding(vertical = xxSmall)
                            .fillMaxWidth()
                    ) {

                        Icon(
                            modifier = Modifier
                                .size(medium),
                            imageVector = Icons.Default.ArrowDropDown,
                            tint = DarkGray,
                            contentDescription = "Dropdown arrow"
                        )
                    }
                    if (expanded) {
                        DropdownMenu(
                            expanded = true,
                            onDismissRequest = {
                                expanded = !expanded
                            },
                            modifier = Modifier
                                .wrapContentWidth()
                        ) {
                            selectionList.forEachIndexed { index, item ->
                                DropdownMenuItem(
                                    onClick = {
                                        selectedItem = item
                                        expanded = !expanded
                                        focusManager.moveFocus(FocusDirection.Down)
                                    },
                                    modifier = Modifier
                                ) {
                                    Text(
                                        text = item,
                                        textAlign = TextAlign.Center,
                                        color = Color.White,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }
                }
            }
            if (isError) {
                Text(text = "Please enter in a value", color = Color.Red)
            }
        }
    }
    return selectedItem
}

@Composable
fun SelectionDropDown(
    items: List<String>,
    expandDropdown: (String?) -> Unit
) {

}
