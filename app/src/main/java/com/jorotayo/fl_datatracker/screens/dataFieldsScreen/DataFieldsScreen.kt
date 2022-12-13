package com.jorotayo.fl_datatracker.screens.dataFieldsScreen

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.navigation.Screen
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components.*
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.DataFieldEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.PresetEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.RowEvent
import com.jorotayo.fl_datatracker.screens.homeScreen.components.BottomNavigationBar
import com.jorotayo.fl_datatracker.ui.DefaultSnackbar
import com.jorotayo.fl_datatracker.viewModels.DataFieldsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Preview
@Composable
fun PreviewPageTemplate() {
    DataFieldsScreen(
        navController = rememberNavController(),
        viewModel = hiltViewModel(),
        context = LocalContext.current
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DataFieldsScreen(
    navController: NavController,
    viewModel: DataFieldsViewModel = hiltViewModel(),
    context: Context,
    ) {

    val bottomNavigationItems = listOf(
        Screen.DataFieldsScreen,
        Screen.HomeScreen,
        Screen.DataEntry
    )

    val headerColours =
        if (isSystemInDarkTheme()) MaterialTheme.colors.onPrimary else MaterialTheme.colors.primary

    var presetExpanded by remember { mutableStateOf(false) }
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val isAddDataFieldVisible = viewModel.dataFieldScreenState
    val fields = viewModel.dataFieldScreenState.value.dataFields
    val presets = viewModel.dataFieldScreenState.value.presetList
    val currentPreset = viewModel.dataFieldScreenState.value.currentPreset

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is DataFieldsViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                DataFieldsViewModel.UiEvent.SaveDataField -> {
                    viewModel.onDataEvent(DataFieldEvent.ToggleAddNewDataField)
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
                    .fillMaxWidth()
                    .fillMaxHeight(0.2f)
                    .background(MaterialTheme.colors.primary))
            {
                Row( //Heading row
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 0.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier,
                        text = stringResource(id = R.string.presetShowing),
                        color = MaterialTheme.colors.onPrimary,
                        style = MaterialTheme.typography.h6.also { FontStyle.Italic },
                        textAlign = TextAlign.Start
                    )
                    Row(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(horizontal = 5.dp)
                            .clickable(onClick = { presetExpanded = true })
                            .clip(shape = RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colors.onBackground)
                            .padding(5.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(end = 5.dp),
                            text = currentPreset!!.presetName,
                            color = headerColours,
                            textAlign = TextAlign.Center
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Drop down arrow for Preset Dropdown",
                            tint = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
                        )
                        DropdownMenu(
                            expanded = presetExpanded,
                            onDismissRequest = { presetExpanded = false },
                            modifier = Modifier
                                .wrapContentWidth()
                                .background(
                                    MaterialTheme.colors.background
                                )
                        ) {
                            presets.forEachIndexed { index, preset ->
                                DropdownMenuItem(onClick = {
                                    viewModel.onPresetEvent(PresetEvent.ChangePreset(preset.presetName))
                                    presetExpanded = false
                                },
                                    modifier = Modifier)
                                {
                                    Row(modifier = Modifier
                                        .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween)
                                    {
                                        Text(
                                            text = preset.presetName,
                                            textAlign = TextAlign.Center,
                                            color = MaterialTheme.colors.onSurface,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        // Default shouldn't show the 'x'
                                        if (index > 0) {
                                            Icon(
                                                modifier = Modifier.clickable(
                                                    onClick = {
                                                        viewModel.onPresetEvent(PresetEvent.TogglePresetDeleteDialog(
                                                            value = preset))
                                                        /*
                                                        if (presets.size >= 2) {
                                                            viewModel.onPresetEvent(PresetEvent.TogglePresetDeleteDialog(
                                                                value = preset))
                                                        } else {
                                                            scope.launch {
                                                                scaffoldState.snackbarHostState.showSnackbar(
                                                                    message = "At least one preset is needed"
                                                                )
                                                            }
                                                        }*/
                                                    }
                                                ),
                                                imageVector = Icons.Default.Close,
                                                contentDescription = "Delete icon for ${preset.presetName}",
                                                tint = MaterialTheme.colors.onSurface
                                            )
                                        }
                                    }
                                }
                            }
                            DropdownMenuItem(onClick = {
                                viewModel.onPresetEvent(PresetEvent.TogglePresetDialog)
                            }) {
                                Text(
                                    modifier = Modifier,
                                    text = stringResource(R.string.add_preset_text_btn),
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colors.onSurface
                                )
                            }
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
                .background(MaterialTheme.colors.primary)

        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                    .background(MaterialTheme.colors.background)
            ) {
                item {

                    Row( //Add/Edit row
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(top = 24.dp, bottom = 16.dp, end = 16.dp, start = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier,
                            text = stringResource(id = R.string.add_edit_fields_label),
                            color = headerColours,
                            style = MaterialTheme.typography.h6.also { FontStyle.Italic },
                            textAlign = TextAlign.Start
                        )
                        IconButton(
                            modifier = Modifier,
                            onClick = {
                                scope.launch {
                                    viewModel.onDataEvent(DataFieldEvent.ToggleAddNewDataField)
                                }
                            }) {
                            Icon(
                                modifier = Modifier
                                    .size(48.dp),
                                imageVector = Icons.Default.AddBox,
                                contentDescription = stringResource(id = R.string.add_field_description),
                                tint = MaterialTheme.colors.primary
                            )
                        }
                    }
                }
                item {
                    AnimatedVisibility(visible = fields.isNotEmpty() && !isAddDataFieldVisible.value.isAddDataFieldVisible) {
                        Row(
                            modifier = Modifier
                                .wrapContentHeight()
                                .padding(bottom = 8.dp, start = 10.dp, end = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier
                                    .weight(3f),
                                text = "Field Name",
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colors.onSurface
                            )
                            Text(
                                modifier = Modifier
                                    .weight(3f),
                                text = "Field Type",
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colors.onSurface
                            )
                            Text(
                                modifier = Modifier
                                    .weight(1.5f),
                                text = "Enabled?",
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colors.onSurface
                            )
                            Text(
                                modifier = Modifier
                                    .weight(0.5f),
                                text = "",
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colors.onSurface
                            )
                        }
                    }
                }
                item {

                    AnimatedVisibility(visible = !isAddDataFieldVisible.value.isAddDataFieldVisible && fields.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 10.dp, end = 10.dp, bottom = 20.dp)
                                .clip(shape = RoundedCornerShape(10.dp)),
                            verticalArrangement = Arrangement.SpaceAround,
                        ) {
                            //Show No Data Field Message
                            NoDataField()
                        }
                    }
                }
                item {
                    AnimatedVisibility(
                        modifier = Modifier
                            .wrapContentHeight(),
                        visible = isAddDataFieldVisible.value.isAddDataFieldVisible) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp, end = 10.dp, bottom = 20.dp)
                                .shadow(5.dp, RoundedCornerShape(10.dp))
                                .clip(shape = RoundedCornerShape(10.dp))
                                .background(MaterialTheme.colors.onBackground)
                                .wrapContentHeight(),
                        ) {
                            // Show New Data Field Message
                            NewDataField(
                                viewModel = hiltViewModel(),
                                onClick = {
                                    if (currentPreset != null) {
                                        it.presetId = currentPreset.presetId
                                    }
                                        viewModel.onDataEvent(DataFieldEvent.SaveDataField(it))
                                }
                            )
                        }
                    }
                }
                itemsIndexed(items = fields, key = { index, item -> item.dataFieldId.toInt() },
                    itemContent = { index, item ->
                        DataFieldRow(
                            viewModel = hiltViewModel(),
                            currentDataField = item,
                            editName = {
                                viewModel.onRowEvent(
                                    RowEvent.EditFieldName(
                                        index = item.dataFieldId,
                                        value = it
                                    ))
                                item.fieldName = it
                            },
                            editRowType = {
                                viewModel.onRowEvent(
                                    RowEvent.EditRowType(
                                        index = item.dataFieldId,
                                        value = it
                                    ))
                                item.dataFieldType = it
                            },
                            checkedChange = {
                                viewModel.onRowEvent(RowEvent.EditIsEnabled(
                                    index = item.dataFieldId
                                ))
                                item.isEnabled = !item.isEnabled
                            },
                            editHintText = {
                                viewModel.onRowEvent(
                                    RowEvent.EditHintText(
                                        index = item.dataFieldId,
                                        value = it
                                    ))
                                item.fieldHint = it
                            },
                            deleteIcon = {
                                viewModel.onDataEvent(
                                    DataFieldEvent.OpenDeleteDialog(
                                        dataField = item
                                    ))
                            }
                        )
                    }
                )
            }

            BasicAddPresetDialog(
                modifier = Modifier
                    .align(Alignment.Center),
                state = viewModel.dataFieldScreenState.value.isAddPresetDialogVisible,
                addPreset = {
                    val toastMessage = if (Validate().validatePreset(it)) {
                        viewModel.onPresetEvent(PresetEvent.AddPreset(it))
                        "Preset: $it added!"
                    } else {
                        "Preset not added. Name already exists!"
                    }
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = toastMessage
                        )
                    }
                }
            )

            if (currentPreset != null) {
                BasicDeletePresetDialog(
                    modifier = Modifier
                        .align(Alignment.Center),
                    state = viewModel.dataFieldScreenState.value.isPresetDeleteDialogVisible,
                    preset = viewModel.dataFieldScreenState.value.deletedPreset,
                    scaffold = scaffoldState,
                    confirmDelete = {
                        viewModel.onPresetEvent(PresetEvent.DeletePreset(value = it))
                    }
                )
            }

            BasicDeleteRowDialog(
                modifier = Modifier
                    .align(Alignment.Center),
                state = viewModel.dataFieldScreenState.value.isDeleteDialogVisible,
                dataField = viewModel.dataFieldScreenState.value.deletedDataField,
                scaffold = scaffoldState,
                confirmDelete = {
                    scope.launch {
                        viewModel.onDataEvent(DataFieldEvent.DeleteDataField(value = it))
                    }
                }
            )

            DefaultSnackbar(
                modifier = Modifier
                    .align(Alignment.Center),
                snackbarHostState = scaffoldState.snackbarHostState,
                onDismiss = {
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                    if (scaffoldState.snackbarHostState.currentSnackbarData?.actionLabel?.contains("Restore") == true) {
//                            viewModel.onDataEvent(DataFieldEvent.RestoreDeletedField)
                        // TODO: default snackbar to show on top
                    }
                }
            )
        }
    }
}