package com.jorotayo.fl_datatracker.screens.dataFieldsScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.model.Preset
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.DataFieldsViewModel.UiEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components.DataFieldRowV2
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components.NewDataField
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components.NoDataField
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.DataFieldEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.DataFieldEvent.DismissDeleteDataFieldDialog
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.DataFieldEvent.DismissPresetDropdown
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.DataFieldEvent.RestoreDeletedField
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.PresetEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.PresetEvent.ChangePreset
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.PresetEvent.DeletePreset
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.PresetEvent.DismissAddPresetDialog
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.PresetEvent.DismissDeletePresetDialog
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.PresetEvent.EditPresetName
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.PresetEvent.SaveNewPreset
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.PresetEvent.ShowDeletePresetDialog
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.RowEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.states.DataFieldScreenState
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.DefaultSnackbar
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.ui.theme.subtitleTextColour
import com.jorotayo.fl_datatracker.util.Dimen.bottomBarPadding
import com.jorotayo.fl_datatracker.util.Dimen.iconSize
import com.jorotayo.fl_datatracker.util.Dimen.large
import com.jorotayo.fl_datatracker.util.Dimen.regular
import com.jorotayo.fl_datatracker.util.Dimen.small
import com.jorotayo.fl_datatracker.util.Dimen.xSmall
import com.jorotayo.fl_datatracker.util.Dimen.xxSmall
import com.jorotayo.fl_datatracker.util.Dimen.xxxSmall
import com.jorotayo.fl_datatracker.util.Dimen.zero
import com.jorotayo.fl_datatracker.util.components.AlertDialogLayout
import com.jorotayo.fl_datatracker.util.components.AlertDialogState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@DefaultPreviews
@Composable
fun DataFieldsScreenPreview() {
    val examplePreset = Preset(presetId = 0L, presetName = "Default")

    FL_DatatrackerTheme {
        DataFieldsScreen(
//            uiState = DataFieldScreenState(
//                showAddPresetDialog = true,
//                presetList = listOf(examplePreset),
//                dataFields = exampleDataFieldList,
//                currentPreset = examplePreset
//            ),
            onUiEvent = MutableSharedFlow(),
            onRowEvent = {},
            onDataFieldEvent = {},
            onPresetEvent = {}
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DataFieldsScreen(
    onUiEvent: SharedFlow<UiEvent>,
    onRowEvent: (RowEvent) -> Unit,
    onPresetEvent: (PresetEvent) -> Unit,
    onDataFieldEvent: (DataFieldEvent) -> Unit
) {
    onDataFieldEvent(DataFieldEvent.InitScreen)

    val viewModel = hiltViewModel<DataFieldsViewModel>()
    val uiState by viewModel.state.collectAsState(DataFieldScreenState())

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val fields = uiState.dataFields
    val presets = uiState.presetList
    val listState = rememberLazyListState()

    LaunchedEffect(key1 = true) {
        onUiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

                is UiEvent.SaveDataField -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(top = large)
            ) {
                HeaderRow()
                PresetSelection(
                    onDataFieldEvent,
                    uiState,
                    onPresetEvent,
                    presets
                )
                AddEditRow(scope, onDataFieldEvent, listState)
                if (uiState.dataFields.isNotEmpty()) {
                    DataFieldColumnHeaders(fields, uiState)
                } else {
                    NoDataFieldSection(uiState, fields)
                }
            } // end of top bar
        },
        scaffoldState = scaffoldState,
        snackbarHost = {
            scaffoldState.snackbarHostState
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(colors.background)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = xSmall, bottom = bottomBarPadding + small),
                state = listState
            ) {
                item {
                    NewDataFieldSection(uiState, onDataFieldEvent)
                }

                itemsIndexed(
                    items = fields,
                    key = { _, item -> item.dataFieldId.toInt() },
                    itemContent = { _, item ->
                        DataFieldRowV2(
                            currentDataField = item,
                            onRowEvent = onRowEvent,
                            onDataFieldEvent = onDataFieldEvent
                        )
                    }
                )
            }

            AddPresetDialog(uiState, onPresetEvent)
            DeletePresetDialog(uiState, onPresetEvent)
            DeleteDataFieldDialog(uiState, onDataFieldEvent)

//            uiState.alertDialogState?.let { AlertDialogLayout(alertDialogState = it) }

            DefaultSnackbar(
                modifier = Modifier
                    .align(Alignment.Center),
                snackbarHostState = scaffoldState.snackbarHostState,
                onDismiss = {
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                    if (scaffoldState.snackbarHostState.currentSnackbarData?.actionLabel?.contains(
                            "Restore"
                        ) == true
                    ) {
                        onDataFieldEvent(RestoreDeletedField)
                    }
                }
            )
        }
    }
}

@Composable
private fun HeaderRow() {
    Text(
        modifier = Modifier
            .padding(start = small),
        text = "Data Fields",
        color = colors.primary,
        style = typography.h1,
        textAlign = TextAlign.Start
    )
}

@Composable
private fun AddEditRow(
    scope: CoroutineScope,
    onDataFieldEvent: (DataFieldEvent) -> Unit,
    listState: LazyListState
) {
    Row( // Add/Edit row
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = small, vertical = zero),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier,
            text = stringResource(id = R.string.add_edit_fields_label),
            style = typography.h3,
            textAlign = TextAlign.Start,
            color = colors.subtitleTextColour,
        )
        IconButton(
            modifier = Modifier,
            onClick = {
                scope.launch {
                    onDataFieldEvent(DataFieldEvent.ToggleAddNewDataField)
                    scrollUp(scope, listState)
                }
            }
        ) {
            Icon(
                modifier = Modifier
                    .size(regular),
                imageVector = Icons.Default.AddBox,
                contentDescription = stringResource(id = R.string.add_field_description),
                tint = colors.primary
            )
        }
    }
}

@Composable
private fun PresetSelection(
    onDataFieldEvent: (DataFieldEvent) -> Unit,
    state: DataFieldScreenState,
    onPresetEvent: (PresetEvent) -> Unit,
    presets: List<Preset>
) {
    Row( // Heading row
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = small, vertical = zero),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier,
            text = stringResource(id = R.string.presetShowing),
            style = typography.h3,
            textAlign = TextAlign.Start,
            color = colors.subtitleTextColour,
        )
        Row(
            modifier = Modifier
                .wrapContentSize()
                .padding(horizontal = xxxSmall)
                .clickable(onClick = { onDataFieldEvent(DataFieldEvent.ExpandPresetDropdown) })
                .clip(shape = RoundedCornerShape(10.dp))
                .padding(5.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 5.dp),
                text = state.currentPreset?.presetName ?: "",
                color = colors.primary,
                style = typography.h3,
                textAlign = TextAlign.Center
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Drop down arrow for Preset Dropdown",
                tint = colors.primary.copy(alpha = 0.5f)
            )
            if (state.isPresetDropDownMenuExpanded) {
                PresetDropDownMenu(
                    onDataFieldEvent = { onDataFieldEvent(DismissPresetDropdown) },
                    onPresetEvent = onPresetEvent,
                    presets = presets,
                    state = state
                )
            }
        }
    }
}

@Composable
private fun ColumnScope.DataFieldColumnHeaders(
    fields: List<DataField>,
    state: DataFieldScreenState
) {
    AnimatedVisibility(visible = fields.isNotEmpty() && !state.isAddDataFieldVisible) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .padding(horizontal = small),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(start = regular)
                    .weight(0.4f),
                text = "Name",
                textAlign = TextAlign.Start,
                style = typography.body1,
                color = colors.subtitleTextColour
            )
            Text(
                modifier = Modifier
                    .padding(start = xxSmall)
                    .weight(0.3f),
                text = "Type",
                textAlign = TextAlign.Start,
                style = typography.body1,
                color = colors.subtitleTextColour
            )
            Text(
                modifier = Modifier
                    .weight(0.3f),
                text = "Enabled?",
                textAlign = TextAlign.Center,
                style = typography.body1,
                color = colors.subtitleTextColour
            )
        }
    }
}

@Composable
private fun NoDataFieldSection(
    state: DataFieldScreenState,
    fields: List<DataField>
) {
    AnimatedVisibility(visible = !state.isAddDataFieldVisible && fields.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = bottomBarPadding)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                NoDataField(modifier = Modifier.align(alignment = Alignment.Center))
            }
        }
    }
}

@Composable
private fun NewDataFieldSection(
    state: DataFieldScreenState,
    onDataFieldEvent: (DataFieldEvent) -> Unit
) {
    AnimatedVisibility(
        modifier = Modifier.fillMaxSize(),
        visible = state.isAddDataFieldVisible
    ) {
        if (state.dataFields.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                NewDataField(
                    currentPresetId = state.currentPreset!!.presetId,
                    onDataFieldEvent = onDataFieldEvent
                )
            }
        } else {
            NewDataField(
                currentPresetId = state.currentPreset!!.presetId,
                onDataFieldEvent = onDataFieldEvent
            )
        }
    }
}

fun scrollUp(scope: CoroutineScope, listState: LazyListState) {
    scope.launch {
        listState.animateScrollToItem(0)
        listState.animateScrollBy(-Float.MAX_VALUE)
    }
}

@Composable
private fun PresetDropDownMenu(
    state: DataFieldScreenState,
    onDataFieldEvent: (DataFieldEvent) -> Unit,
    presets: List<Preset>,
    onPresetEvent: (PresetEvent) -> Unit,
) {
    DropdownMenu(
        expanded = state.isPresetDropDownMenuExpanded,
        onDismissRequest = { onDataFieldEvent(DismissPresetDropdown) },
        modifier = Modifier
            .wrapContentWidth()
    ) {
        presets.forEachIndexed { index, preset ->
            DropdownMenuItem(
                onClick = {
                    onPresetEvent(ChangePreset(preset.presetName))
                },
                modifier = Modifier
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = preset.presetName,
                        textAlign = TextAlign.Center,
                        color = colors.onSurface,
                        overflow = TextOverflow.Ellipsis
                    )
                    // Default shouldn't show the 'x'
                    if (index > 0) {
                        Icon(
                            modifier = Modifier
                                .size(iconSize)
                                .padding(start = 5.dp)
                                .clickable(
                                    onClick = {
                                        onPresetEvent(
                                            ShowDeletePresetDialog(preset)
                                        )
                                    }
                                ),
                            imageVector = Icons.Default.Close,
                            contentDescription = "Delete icon for ${preset.presetName}",
                            tint = colors.onSurface
                        )
                    }
                }
            }
        }
        DropdownMenuItem(onClick = {
            onPresetEvent(PresetEvent.ShowAddPresetDialog)
        }) {
            Text(
                modifier = Modifier,
                text = stringResource(R.string.add_preset_text_btn),
                textAlign = TextAlign.Center,
                color = colors.onSurface
            )
        }
    }
}

@Composable
fun AddPresetDialog(
    state: DataFieldScreenState,
    onPresetEvent: (PresetEvent) -> Unit
) {
    if (state.showAddPresetDialog) {
        AlertDialogLayout(
            alertDialogState =
            AlertDialogState(
                title = "Add New Preset",
                body = "Add a new Preset with the name",
                onDismissRequest = { onPresetEvent(DismissAddPresetDialog) },
                confirmButtonLabel = "Add Preset",
                confirmButtonOnClick = {
                    onPresetEvent(SaveNewPreset)
                },
                dismissButtonLabel = "Cancel",
                editFieldFunction = {
                    onPresetEvent(EditPresetName(it))
                },
                dismissButtonOnClick = { onPresetEvent(DismissAddPresetDialog) },
                titleTextAlign = TextAlign.Center,
                dismissible = true,
                textFieldError = state.textFieldError,
                textFieldErrorText = "Enter Preset Name!"
            )
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeletePresetDialog(
    state: DataFieldScreenState,
    onPresetEvent: (PresetEvent) -> Unit
) {
    if (state.showDeletePresetDialog) {
        AlertDialogLayout(
            alertDialogState = AlertDialogState(
                title = String.format("Delete Preset: %s", state.modifiedPreset?.presetName),
                body = "Are you sure you want to delete this Preset?",
                onDismissRequest = { onPresetEvent(DismissDeletePresetDialog) },
                confirmButtonLabel = "Delete",
                confirmButtonOnClick = {
                    onPresetEvent(DeletePreset)
                },
                dismissButtonLabel = "Cancel",
                dismissButtonOnClick = { onPresetEvent(DismissDeletePresetDialog) },
                titleTextAlign = TextAlign.Center,
                dismissible = true
            )
        )
    }
}

@Composable
fun DeleteDataFieldDialog(
    state: DataFieldScreenState,
    onDataFieldEvent: (DataFieldEvent) -> Unit
) {
    if (state.showDeleteDataFieldDialog) {
        AlertDialogLayout(
            AlertDialogState(
                title = String.format("Delete DataField: %s", state.currentDataField!!.fieldName),
                imageIcon = Icons.Default.Delete,
                body = "Are you sure you want to delete this Data Field?",
                onDismissRequest = { onDataFieldEvent(DismissDeleteDataFieldDialog) },
                confirmButtonLabel = "Delete",
                confirmButtonOnClick = {
                    onDataFieldEvent(DataFieldEvent.ConfirmDeleteDataField)
                },
                dismissButtonLabel = "Cancel",
                dismissButtonOnClick = { onDataFieldEvent(DismissDeleteDataFieldDialog) },
                titleTextAlign = TextAlign.Center,
                dismissible = true
            )

        )
    }
}