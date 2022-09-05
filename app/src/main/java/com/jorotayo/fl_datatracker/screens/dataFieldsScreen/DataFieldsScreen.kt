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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.model.Preset
import com.jorotayo.fl_datatracker.navigation.Screen
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components.*
import com.jorotayo.fl_datatracker.screens.homeScreen.components.BottomNavigationBar
import com.jorotayo.fl_datatracker.ui.DefaultSnackbar
import com.jorotayo.fl_datatracker.viewModels.DataFieldsViewModel
import io.objectbox.Box
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun PreviewDataFieldsScreen() {
    DataFieldsScreen(
        navController = rememberNavController(),
        viewModel = hiltViewModel()
    )
}

sealed class DataFieldsChannel {
    object DeletedField : DataFieldsChannel()
    object SavedDataField : DataFieldsChannel()
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DataFieldsScreen(
    navController: NavController,
    viewModel: DataFieldsViewModel = hiltViewModel(),
) {

    val _dataFieldsBox: Box<DataField> = ObjectBox.get().boxFor(DataField::class.java)
    val dataFieldsBox = remember { mutableStateOf(_dataFieldsBox.all.toList()) }
    var presetExpanded by remember { mutableStateOf(false) }

    val bottomNavigationItems = listOf(
        Screen.DataFieldsScreen,
        Screen.HomeScreen,
    )

    val isAddDataFieldVisible = viewModel.dataFieldScreenState

    val allDataFields = viewModel.dataFieldsBox
    //val fields: MutableList<List<DataField>> = mutableStateOf(allDataFields.value.filter { it.presetId.toString() == viewModel.currentPreset.value.settingStringValue })

    var fields by remember { mutableStateOf(allDataFields.value.filter { it.presetId.toString() == viewModel.currentPreset.value?.settingStringValue }) }
    /*for(field in allDataFields.value){
       if(viewModel.currentPreset.value?.settingStringValue == field.presetId.toString()){
           fields += field
       }
    }*/

    //var fields = allDataFields.value.filter { it.presetId == (1).toLong() }
    Log.i(TAG, "DataFieldsScreen: ${viewModel.currentPreset.value?.settingStringValue}")

    // Current Preset is a string
    val scaffoldState = rememberScaffoldState()

    val scope = rememberCoroutineScope()

    val presets = remember { mutableStateOf(viewModel.presetBox.value) }

/*    LaunchedEffect(key1 = Unit) {
        viewModel.channel.collect { channel ->
            when (channel) {
                DataFieldsChannel.DeletedField -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "Deleted DataField: ${viewModel.deletedDataField.value.fieldName}",
                        actionLabel = "Restore?"
                    )
                }
                DataFieldsChannel.SavedDataField -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "DataField: ${fields.value.last().fieldName}"
                    )
                }
            }
        }
    }*/

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
                        (if (viewModel.currentPreset.value?.settingStringValue.isNullOrEmpty()) "No Presets" else viewModel.currentPreset.value?.settingStringValue)?.let {
                            Text(
                                modifier = Modifier,
                                text = it,
                                color = MaterialTheme.colors.primary,
                                textAlign = TextAlign.Center
                            )
                        }
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
                            presets.value.forEachIndexed { index, s ->
                                DropdownMenuItem(onClick = {
                                    viewModel.onEvent(DataFieldEvent.ChangePreset(s.presetId.toString()))
                                    presetExpanded = false
                                },
                                    modifier = Modifier)
                                {
                                    Text(
                                        text = s.presetName,
                                        textAlign = TextAlign.Center,
                                        color = MaterialTheme.colors.onSurface
                                    )
                                    Icon(
                                        modifier = Modifier.clickable(
                                            onClick = {
                                                // TODO: delete clickable
                                                // DeletePresetDialog
                                                // viewModel.onEvent(DataFieldEvent.DeletePreset
                                            }
                                        ),
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Delete icon for ${s.presetName}",
                                        tint = MaterialTheme.colors.onSurface
                                    )
                                }
                            }
                            DropdownMenuItem(onClick = {
                                viewModel.onEvent(DataFieldEvent.TogglePresetDialog)
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
                            viewModel.onEvent(DataFieldEvent.ToggleAddNewDataField)
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

                AnimatedVisibility(visible = !isAddDataFieldVisible.value.isAddDataFieldVisible && fields.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 10.dp, end = 10.dp, bottom = 20.dp)
                            .clip(shape = RoundedCornerShape(10.dp)),
                        verticalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Spacer(modifier = Modifier.weight(1f))

                        //Show No Data Field Message
                        NoDataField()

                        Spacer(modifier = Modifier.weight(1f))
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
                                            it.presetId = viewModel.currentPreset.value?.Id!!
                                            if (!msg.first) {
                                                fields += it
                                                viewModel.onEvent(DataFieldEvent.SaveDataField(
                                                    fields))
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
                    item {
                        AnimatedVisibility(visible = fields.isEmpty() && !isAddDataFieldVisible.value.isAddDataFieldVisible) {
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
                    itemsIndexed(fields,
                        itemContent = { index, item ->
                            DataFieldRow(
                                viewModel = DataFieldsViewModel(),
                                itemIndex = item.dataFieldId,
                                editName = {
                                    viewModel.onEvent(
                                        DataFieldEvent.EditFieldName(
                                            index = item.dataFieldId,
                                            value = it
                                        )
                                    )
                                    item.fieldName = it
                                },
                                editType = {
                                    viewModel.onEvent(
                                        (DataFieldEvent.EditRowType(
                                            index = item.dataFieldId,
                                            value = it
                                        ))
                                    )
                                    item.dataFieldType = it
                                },
                                checkedChange = {
                                    viewModel.onEvent(DataFieldEvent.EditIsEnabled(index = item.dataFieldId))
                                    item.isEnabled = !item.isEnabled
                                },
                                editHintText = {
                                    viewModel.onEvent(
                                        DataFieldEvent.EditHintText(
                                            index = item.dataFieldId,
                                            value = it
                                        )
                                    )
                                    item.fieldHint = it
                                },
                                deleteIcon = {
                                    viewModel.onEvent(
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
                    val preset = Preset(
                        presetId = 0,
                        presetName = it)
                    presets.value += preset
                    viewModel.onEvent(DataFieldEvent.AddPreset(it))
                }
            )
            DeleteRowDialog(
                modifier = Modifier
                    .align(Alignment.Center),
                state = viewModel.dataFieldScreenState.value.isDeleteDialogVisible,
                dataField = viewModel.dataFieldScreenState.value.deletedDataField,
                scaffold = scaffoldState,
                confirmDelete = {
                    // fields -= it
                    _dataFieldsBox.remove(it)
                    val newList = dataFieldsBox.value
                    viewModel.onEvent(DataFieldEvent.ConfirmDelete(value = newList))
                }
            )

            DefaultSnackbar(
                modifier = Modifier
                    .align(Alignment.Center),
                snackbarHostState = scaffoldState.snackbarHostState,
                onDismiss = {
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                    if (scaffoldState.snackbarHostState.currentSnackbarData?.actionLabel?.contains("Restore") == true) {
                        viewModel.onEvent(DataFieldEvent.RestoreDeletedField)
                    }
                }
            )
        }
    }
}
