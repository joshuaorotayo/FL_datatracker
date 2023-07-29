package com.jorotayo.fl_datatracker.screens.dataFieldsScreen

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.domain.model.Preset
import com.jorotayo.fl_datatracker.navigation.Screen
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components.*
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.DataFieldEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.DataFieldEvent.HidePresetDropdown
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.DataFieldEvent.RestoreDeletedField
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.PresetEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.PresetEvent.ChangePreset
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.PresetEvent.ShowDeletePresetDialog
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.RowEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.states.DataFieldScreenState
import com.jorotayo.fl_datatracker.screens.homeScreen.components.BottomNavigationBar
import com.jorotayo.fl_datatracker.ui.DefaultSnackbar
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.util.Dimen
import com.jorotayo.fl_datatracker.util.Dimen.medium
import com.jorotayo.fl_datatracker.util.Dimen.small
import com.jorotayo.fl_datatracker.util.Dimen.xxSmall
import com.jorotayo.fl_datatracker.util.components.AlertDialog
import com.jorotayo.fl_datatracker.util.exampleDataFieldList
import com.jorotayo.fl_datatracker.viewModels.DataFieldsViewModel.UiEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Preview(showBackground = true, name = "Light Mode")
@Composable
fun DataFieldsScreenPreview() {
    val examplePreset = Preset(presetId = 0L, presetName = "Default")

    FL_DatatrackerTheme {
        DataFieldsScreen(
            navController = rememberNavController(),
            state = DataFieldScreenState(
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
    navController: NavController,
    state: DataFieldScreenState,
    onUiEvent: SharedFlow<UiEvent>,
    onRowEvent: (RowEvent) -> Unit,
    onPresetEvent: (PresetEvent) -> Unit,
    onDataFieldEvent: (DataFieldEvent) -> Unit
) {

    val bottomNavigationItems = listOf(
        Screen.DataFieldsScreen,
        Screen.HomeScreen,
        Screen.DataEntry
    )

    onDataFieldEvent(DataFieldEvent.InitScreen)

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val fields = state.dataFields
    val presets = state.presetList
    val currentPreset = state.currentPreset

    LaunchedEffect(key1 = true) {
        onUiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

                UiEvent.SaveDataField -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "Data Field Saved!"
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
                    .padding(top = Dimen.large)
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = small),
                    text = "Data Fields",
                    color = colors.primary,
                    style = typography.h4.also { SemiBold },
                    textAlign = TextAlign.Start
                )
                Row( //Heading row
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = small, end = small, top = 24.dp, bottom = 0.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier,
                        text = stringResource(id = R.string.presetShowing),
                        color = colors.onBackground,
                        style = typography.h6,
                        textAlign = TextAlign.Start
                    )
                    Row(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(horizontal = 5.dp)
                            .clickable(onClick = { onDataFieldEvent(DataFieldEvent.ExpandPresetDropdown) })
                            .clip(shape = RoundedCornerShape(10.dp))
                            .padding(5.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 5.dp),
                            text = currentPreset.presetName,
                            color = colors.primary,
                            style = typography.h6,
                            textAlign = TextAlign.Center
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Drop down arrow for Preset Dropdown",
                            tint = colors.onSurface.copy(alpha = 0.5f)
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
        },
        scaffoldState = scaffoldState,
        snackbarHost = {
            scaffoldState.snackbarHostState
        },
        bottomBar = {
            BottomNavigationBar(navController, bottomNavigationItems)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                    .background(colors.background)
            ) {
                item {

                    Row( //Add/Edit row
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(top = 24.dp, bottom = small, end = small, start = small),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier,
                            text = stringResource(id = R.string.add_edit_fields_label),
                            color = colors.onBackground,
                            style = typography.h6.also { FontStyle.Italic },
                            textAlign = TextAlign.Start
                        )
                        IconButton(
                            modifier = Modifier,
                            onClick = {
                                scope.launch {
                                    onDataFieldEvent(DataFieldEvent.ToggleAddNewDataField)
                                }
                            }) {
                            Icon(
                                modifier = Modifier
                                    .size(48.dp),
                                imageVector = Icons.Default.AddBox,
                                contentDescription = stringResource(id = R.string.add_field_description),
                                tint = colors.primary
                            )
                        }
                    }
                }
                item {
                    AnimatedVisibility(visible = fields.isNotEmpty() && !state.isAddDataFieldVisible) {
                        Row(
                            modifier = Modifier
                                .wrapContentHeight()
                                .padding(bottom = xxSmall, start = medium, end = small),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier
                                    .weight(0.35f),
                                text = "Field Name",
                                textAlign = TextAlign.Start,
                                color = colors.onSurface,
                                style = typography.h6
                            )
                            Text(
                                modifier = Modifier
                                    .weight(0.35f),
                                text = "Field Type",
                                textAlign = TextAlign.Start,
                                color = colors.onSurface,
                                style = typography.h6
                            )
                            Text(
                                modifier = Modifier
                                    .weight(0.2f),
                                text = "Enabled?",
                                textAlign = TextAlign.Start,
                                color = colors.onSurface,
                                style = typography.h6
                            )
                        }
                    }
                }
                item {

                    AnimatedVisibility(visible = !state.isAddDataFieldVisible && fields.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = Dimen.large)
                        ) {
                            Spacer(modifier = Modifier.fillMaxSize(1F))
                            NoDataField()
                            Spacer(modifier = Modifier.fillMaxSize(1F))
                        }
                    }
                }
                item {
                    AnimatedVisibility(
                        modifier = Modifier
                            .fillMaxSize(),
                        visible = state.isAddDataFieldVisible
                    ) {

                        Spacer(modifier = Modifier.fillMaxSize(1F))
                        NewDataField(
                            currentPresetId = state.currentPreset.presetId,
                            onDataFieldEvent = onDataFieldEvent
                        )
                        Spacer(modifier = Modifier.fillMaxSize(1F))
                    }
                }

                itemsIndexed(items = fields, key = { index, item -> item.dataFieldId.toInt() },
                    itemContent = { index, item ->
                        DataFieldRowV2(
                            currentDataField = item,
                            onRowEvent = onRowEvent,
                            onDataFieldEvent = onDataFieldEvent
                        )
                    }
                )
            }

            state.alertDialogState?.let { AlertDialog(alertDialogState = it) }

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
            )
            {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    Text(
                        text = preset.presetName,
                        textAlign = TextAlign.Center,
                        color = colors.onSurface,
                        overflow = TextOverflow.Ellipsis
                    )
                    // Default shouldn't show the 'x'
                    if (index > 0) {
                        Icon(modifier = Modifier
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
