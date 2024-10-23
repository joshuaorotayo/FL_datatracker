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
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.model.Preset
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.DataFieldsViewModel.UiEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components.DataFieldRowV2
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components.NewDataField
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components.NoDataField
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.DataFieldEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.DataFieldEvent.HidePresetDropdown
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.DataFieldEvent.RestoreDeletedField
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.PresetEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.PresetEvent.ChangePreset
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.PresetEvent.ShowDeletePresetDialog
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.RowEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.states.DataFieldScreenState
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.DefaultSnackbar
import com.jorotayo.fl_datatracker.ui.theme.AppTheme
import com.jorotayo.fl_datatracker.ui.theme.AppTheme.dimens
import com.jorotayo.fl_datatracker.util.components.AlertDialogLayout
import com.jorotayo.fl_datatracker.util.exampleDataFieldList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@DefaultPreviews
@Composable
fun DataFieldsScreenPreview() {
    val examplePreset = Preset(presetId = 0L, presetName = "Default")

    AppTheme {
        DataFieldsScreen(
            uiState = DataFieldScreenState(
                presetList = listOf(examplePreset),
                dataFields = exampleDataFieldList,
                currentPreset = examplePreset
            ),
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
    uiState: DataFieldScreenState,
    onUiEvent: SharedFlow<UiEvent>,
    onRowEvent: (RowEvent) -> Unit,
    onPresetEvent: (PresetEvent) -> Unit,
    onDataFieldEvent: (DataFieldEvent) -> Unit
) {
    onDataFieldEvent(DataFieldEvent.InitScreen)

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val fields = uiState.dataFields
    val presets = uiState.presetList
    val currentPreset = uiState.currentPreset
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
                    .background(AppTheme.colors.background)
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(top = dimens.large)
            ) {
                HeaderRow()
                PresetSelection(
                    onDataFieldEvent,
                    currentPreset,
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
                .background(AppTheme.colors.background)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = dimens.xSmall, bottom = dimens.bottomBarPadding),
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

            uiState.alertDialogState?.let { AlertDialogLayout(alertDialogState = it) }

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
            .padding(start = dimens.small),
        text = "Data Fields",
        color = AppTheme.colors.onBackground,
        style = AppTheme.typography.titleLarge,
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
            .padding(horizontal = dimens.small, vertical = dimens.zero),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier,
            text = stringResource(id = R.string.add_edit_fields_label),
            style = AppTheme.typography.titleSmall,
            textAlign = TextAlign.Start,
            color = AppTheme.colors.onSurface,
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
                    .size(dimens.regular),
                imageVector = Icons.Default.AddBox,
                contentDescription = stringResource(id = R.string.add_field_description),
                tint = AppTheme.colors.onBackground
            )
        }
    }
}

@Composable
private fun PresetSelection(
    onDataFieldEvent: (DataFieldEvent) -> Unit,
    currentPreset: Preset,
    state: DataFieldScreenState,
    onPresetEvent: (PresetEvent) -> Unit,
    presets: List<Preset>
) {
    Row( // Heading row
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = dimens.small, vertical = dimens.zero),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier,
            text = stringResource(id = R.string.presetShowing),
            style = AppTheme.typography.titleSmall,
            textAlign = TextAlign.Start,
            color = AppTheme.colors.onSurface,
        )
        Row(
            modifier = Modifier
                .wrapContentSize()
                .padding(horizontal = dimens.xxSmall)
                .clickable(onClick = { onDataFieldEvent(DataFieldEvent.ExpandPresetDropdown) })
                .clip(shape = RoundedCornerShape(10.dp))
                .padding(5.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 5.dp),
                text = currentPreset.presetName,
                color = AppTheme.colors.onBackground,
                style = AppTheme.typography.titleSmall,
                textAlign = TextAlign.Center
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Drop down arrow for Preset Dropdown",
                tint = AppTheme.colors.onBackground.copy(alpha = 0.5f)
            )
            if (state.isPresetDropDownMenuExpanded) {
                PresetDropDownMenu(
                    onDataFieldEvent = { onDataFieldEvent(HidePresetDropdown) },
                    onPresetEvent = onPresetEvent,
                    presets = presets,
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
                .padding(horizontal = dimens.small),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(start = dimens.regular)
                    .weight(0.4f),
                text = "Name",
                textAlign = TextAlign.Start,
                style = typography.body1,
                color = AppTheme.colors.onSurface
            )
            Text(
                modifier = Modifier
                    .padding(start = dimens.xxSmall)
                    .weight(0.3f),
                text = "Type",
                textAlign = TextAlign.Start,
                style = typography.body1,
                color = AppTheme.colors.onSurface
            )
            Text(
                modifier = Modifier
                    .weight(0.3f),
                text = "Enabled?",
                textAlign = TextAlign.Center,
                style = typography.body1,
                color = AppTheme.colors.onSurface
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
                .padding(bottom = dimens.bottomBarPadding)
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
                    currentPresetId = state.currentPreset.presetId,
                    onDataFieldEvent = onDataFieldEvent
                )
            }
        } else {
            NewDataField(
                currentPresetId = state.currentPreset.presetId,
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
    onDataFieldEvent: (DataFieldEvent) -> Unit,
    presets: List<Preset>,
    onPresetEvent: (PresetEvent) -> Unit,
) {
    DropdownMenu(
        expanded = true,
        onDismissRequest = { onDataFieldEvent(HidePresetDropdown) },
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
                        color = AppTheme.colors.onSurface,
                        overflow = TextOverflow.Ellipsis
                    )
                    // Default shouldn't show the 'x'
                    if (index > 0) {
                        Icon(
                            modifier = Modifier
                                .size(dimens.icon)
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
