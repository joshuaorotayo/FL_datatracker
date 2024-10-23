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
import androidx.compose.ui.unit.dp
import com.dsc.form_builder.SelectState
import com.dsc.form_builder.Validators
import com.jorotayo.fl_datatracker.screens.dataFieldsScreenRework.DataFieldsReworkState
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.theme.AppTheme
import com.jorotayo.fl_datatracker.ui.theme.AppTheme.dimens
import com.jorotayo.fl_datatracker.ui.theme.isDarkMode


@DefaultPreviews
@Composable
fun PreviewMinimalSelectionField() {
    AppTheme {
        Column {
            minimalSelectionField(
                rowHeader = "this is a sample row",
                state = DataFieldsReworkState(),
                selectionState = SelectState(
                    name = "gender",
                    initial = mutableListOf("Male, Female"),
                    validators = listOf(
                        Validators.Required()
                    )
                ),
            )
        }
    }
}

@Composable
fun minimalSelectionField(
    rowHeader: String,
    selectionState: SelectState,
    state: DataFieldsReworkState
): String {

    var expanded by remember { mutableStateOf(state.isDropdownExpanded) }
    var cardElevation by remember { mutableStateOf(12.dp) }
    val focusManager = LocalFocusManager.current
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
                .padding(dimens.xxSmall)
                .onFocusChanged {
                    cardElevation = if (it.isFocused || state.isDropdownExpanded) 32.dp else 12.dp
                },
            shape = RoundedCornerShape(dimens.xSmall),
            backgroundColor = if (!isDarkMode() && cardElevation == dimens.medium) colors.surface.copy(
                alpha = 0.5f
            ) else colors.surface,
            elevation = if (isDarkMode()) cardElevation else dimens.zero
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
                            .padding(dimens.xxSmall)
                            .fillMaxWidth(0.9f)
                    ) {
                        Text(
                            text = rowHeader,
                            style = typography.body1,
                            color = DarkGray
                        )
                        Text(
                            modifier = Modifier,
                            text = selectionState.name.ifEmpty { "Please select an option" },
                            style = typography.body1,
                            color = if (selectedItem.isBlank()) DarkGray else colors.onSurface
                        )
                    }
                    Column(
                        modifier = Modifier
                            .padding(vertical = dimens.xxSmall)
                            .fillMaxWidth()
                    ) {

                        Icon(
                            modifier = Modifier
                                .size(dimens.medium),
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
                            selectionState.initial.forEachIndexed { index, item ->
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
            if (selectionState.hasError) {
                Text(text = "Please enter in a value", color = Color.Red)
            }
        }
    }
    return selectedItem
}
