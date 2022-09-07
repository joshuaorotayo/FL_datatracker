package com.jorotayo.fl_datatracker.screens.dataFieldsScreen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.PresetEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.RowEvent
import com.jorotayo.fl_datatracker.screens.homeScreen.components.BottomNavigationBar
import com.jorotayo.fl_datatracker.ui.DefaultSnackbar
import com.jorotayo.fl_datatracker.viewModels.DataFieldsViewModel
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun PreviewDataFieldsScreen() {
    DataFieldsScreen(
        navController = rememberNavController(),
        viewModel = hiltViewModel()
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DataFieldsScreen(
    navController: NavController,
    viewModel: DataFieldsViewModel = hiltViewModel(),
) {

    val bottomNavigationItems = listOf(
        Screen.DataFieldsScreen,
        Screen.HomeScreen,
    )

    var presetExpanded by remember { mutableStateOf(false) }
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val isAddDataFieldVisible = viewModel.dataFieldScreenState
    val presets = viewModel.boxState.value.presetBox
    val currentPreset = viewModel.boxState.value.currentPreset
    val fields = viewModel.filteredFields

    Scaffold(
        topBar = {

            Column(modifier = Modifier.fillMaxWidth()) {
                Row( //Heading row
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier,
                        text = stringResource(id = R.string.presetShowing),
                        color = MaterialTheme.colors.onSurface,
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
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier,
                            text = (if (presets.isEmpty()) "No Presets" else currentPreset?.presetName)!!,
                            color = MaterialTheme.colors.primary,
                            textAlign = TextAlign.Center
                        )

                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Drop down arrow for Preset Dropdown",
                            tint = MaterialTheme.colors.primary.copy(alpha = 0.5f)
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
                                        horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text(
                                            text = preset.presetName,
                                            textAlign = TextAlign.Center,
                                            color = MaterialTheme.colors.onSurface,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Icon(
                                            modifier = Modifier.clickable(
                                                onClick = {
                                                    if (viewModel.boxState.value.presetBox.size >= 2) {
                                                        viewModel.onPresetEvent(PresetEvent.TogglePresetDeleteDialog(
                                                            value = preset))
                                                    } else {
                                                        scope.launch {
                                                            scaffoldState.snackbarHostState.showSnackbar(
                                                                message = "At least one preset is needed"
                                                            )
                                                        }

                                                    }
                                                }
                                            ),
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Delete icon for ${preset.presetName}",
                                            tint = MaterialTheme.colors.onSurface
                                        )
                                    }
                                }
                            }
                            DropdownMenuItem(onClick = {
                                viewModel.onPresetEvent(PresetEvent.TogglePresetDialog)
                            }) {
                                Text(
                                    modifier = Modifier,
                                    text = "+ Add Preset",
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colors.onSurface
                                )
                            }
                        }
                    }
                }
                Row( //Heading row
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier,
                        text = "Add/Edit Data Fields:",
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.h6.also { FontStyle.Italic },
                        textAlign = TextAlign.Start
                    )
                    IconButton(
                        modifier = Modifier,
                        onClick = {
                            viewModel.onDataEvent(DataFieldEvent.ToggleAddNewDataField)
                        }) {
                        Icon(
                            modifier = Modifier
                                .size(48.dp),
                            imageVector = Icons.Default.AddBox,
                            contentDescription = "Add New Data Field",
                            tint = MaterialTheme.colors.primary
                        )
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
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.TopStart),
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 16.dp, horizontal = 10.dp)
                        .clip(shape = RoundedCornerShape(10.dp))
                ) {
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
                        AnimatedVisibility(visible = isAddDataFieldVisible.value.isAddDataFieldVisible) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(start = 10.dp, end = 10.dp, bottom = 20.dp)
                                    .clip(shape = RoundedCornerShape(10.dp))
                                    .background(MaterialTheme.colors.onBackground),
                                verticalArrangement = Arrangement.SpaceBetween,
                            ) {
                                // Show New Data Field Message
                                NewDataField(
                                    viewModel = DataFieldsViewModel(),
                                    onClick = {
                                        scope.launch {
                                            val msg = Validate().validateDataField(dataField = it,
                                                viewModel = DataFieldsViewModel())
                                            if (currentPreset != null) {
                                                it.presetId = currentPreset.presetId
                                            } else {
                                                it.presetId = (1).toLong()
                                            }
                                            if (!msg.first) {
                                                viewModel.onDataEvent(DataFieldEvent.SaveDataField(
                                                    it))
                                            }
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                message = msg.second,
                                                actionLabel = "",
                                                duration = SnackbarDuration.Short
                                            )
                                        }
                                    }
                                )
                            }
                        }
                    }
                    itemsIndexed(items = fields, key = { index, item -> item.dataFieldId.toInt() },
                        itemContent = { index, item ->
                            DataFieldRow(
                                viewModel = DataFieldsViewModel(),
                                itemIndex = item.dataFieldId,
                                currentDataField = item,
                                editName = {
                                    viewModel.onRowEvent(
                                        RowEvent.EditFieldName(
                                            index = item.dataFieldId,
                                            value = it
                                        ))
                                    item.fieldName = it
                                },
                                editType = {
                                    viewModel.onRowEvent((RowEvent.EditRowType(
                                        index = item.dataFieldId,
                                        value = it
                                    )
                                            )
                                    )
                                    item.dataFieldType = it
                                },
                                checkedChange = {
                                    viewModel.onRowEvent(RowEvent.EditIsEnabled(
                                        index = item.dataFieldId
                                    )
                                    )
                                    item.isEnabled = !item.isEnabled
                                },
                                editHintText = {
                                    viewModel.onRowEvent(
                                        RowEvent.EditHintText(
                                            index = item.dataFieldId,
                                            value = it
                                        )
                                    )
                                    item.fieldHint = it
                                },
                                deleteIcon = {
                                    viewModel.onDataEvent(
                                        DataFieldEvent.OpenDeleteDialog(
                                            dataField = item
                                        )
                                    )
                                    Log.i(TAG, "DataFieldsScreen: delete icon")
                                }
                            )
                        }
                    )
                }
            }


            AddPresetDialog(
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
                DeletePresetDialog(
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

            DeleteRowDialog(
                modifier = Modifier
                    .align(Alignment.Center),
                state = viewModel.dataFieldScreenState.value.isDeleteDialogVisible,
                dataField = viewModel.dataFieldScreenState.value.deletedDataField,
                scaffold = scaffoldState,
                confirmDelete = {
                    viewModel.onDataEvent(DataFieldEvent.DeleteDataField(value = it))
                }
            )

            DefaultSnackbar(
                modifier = Modifier
                    .align(Alignment.Center),
                snackbarHostState = scaffoldState.snackbarHostState,
                onDismiss = {
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                    if (scaffoldState.snackbarHostState.currentSnackbarData?.actionLabel?.contains("Restore") == true) {
                        viewModel.onDataEvent(DataFieldEvent.RestoreDeletedField)
                    }
                }
            )
        }
    }
}
