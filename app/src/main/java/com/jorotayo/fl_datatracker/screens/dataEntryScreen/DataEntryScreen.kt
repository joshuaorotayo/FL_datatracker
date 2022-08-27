package com.jorotayo.fl_datatracker.screens.dataEntryScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.*
import com.jorotayo.fl_datatracker.ui.DefaultSnackbar
import com.jorotayo.fl_datatracker.util.validateData
import com.jorotayo.fl_datatracker.viewModels.DataEntryScreenViewModel
import formNameHeader
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun PreviewDataEntryScreen() {
    DataEntryScreen(
        viewModel = hiltViewModel(),
        navController = rememberNavController(),
        dataId = -1
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DataEntryScreen(
    viewModel: DataEntryScreenViewModel,
    navController: NavController,
    dataId: Long,
) {
    val systemUiController = rememberSystemUiController()

    val scaffoldState = rememberScaffoldState()

    val scope = rememberCoroutineScope()

    // Field/Service/Meeting Name
    val name = remember { mutableStateOf("") }

    //Gets All data fields
/*    val allDataFields = ObjectBox.get().boxFor(DataField::class.java)
    val dataFields =
        allDataFields.query().equal(DataField_.isEnabled, true).build().use { it.find() }*/

    var uiState = remember { mutableStateOf(viewModel.uiState) }
    viewModel.currentDataId.value = dataId

    systemUiController.setStatusBarColor(MaterialTheme.colors.background)

    Scaffold(
        topBar = {

            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable {
                                // Implement back action here
                                navController.navigateUp()
                            }
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Enter Data",
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.h5.also { FontStyle.Italic },
                        textAlign = TextAlign.Start
                    )
                }
                val testRun = false
                if (uiState.value.value.dataRows.isEmpty() && !testRun) {
                    Spacer(modifier = Modifier.weight(1f))

                    //No Data Form Message
                    NoDataForm()

                    Spacer(modifier = Modifier.weight(1f))
                } else {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        text = "Fill in the information below:",
                        color = MaterialTheme.colors.onSurface,
                        style = MaterialTheme.typography.h6.also { FontStyle.Italic },
                        textAlign = TextAlign.Start
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 5.dp,
                                bottom = 5.dp,
                                start = 16.dp,
                                end = 16.dp
                            ),
                        text = "The form below has been created from the ${uiState.value.value.dataRows.size} DataField(s) in the preset",
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.body1.also { FontStyle.Italic },
                        textAlign = TextAlign.Start
                    )
                }
            }

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
                .background(color = MaterialTheme.colors.background)
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(bottom = 16.dp, top = 5.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
            ) {
                item {
                    // Contents of data entry form
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {

                        name.value = formNameHeader(
                            setName = {
                                viewModel.onEvent(DataEvent.SetName(value = it))
                                uiState.value.value.dataName = it
                            },
                            dataEntryScreenState = uiState.value.value
                        )
                    }
                }

                items(uiState.value.value.dataRows) { data ->
                    /* var found = viewModel.currentDataFields.first {
                         it.id == data.dataField.id
                     }*/
                    when (data.dataField.dataFieldType) {
                        /*    items(viewModel.currentDataFields.value) { data ->
                              *//*  var found = viewModel.currentDataFields.first {
                        it.id == data.dataField.id
                    }*//*
                    when (data.dataFieldType) {*/
                        0 -> {
                            data.dataField.dataValue = formShortTextRowV2(
                                data = data.dataField,
                                hasError = data.hasError
                            )
                        }
                        1 -> {
                            data.dataField.dataValue = formLongTextRowV2(
                                data = data.dataField,
                            )
                        }
                        2 -> {
                            data.dataField.dataValue = formRadioRowV2(
                                data = data.dataField
                            )
                        }
                        3 -> {
                            data.dataField.dataValue = formDateRowV2(
                                viewModel = DataEntryScreenViewModel(),
                                fieldName = data.dataField.fieldName
                            )
                        }
                        4 -> {
                            data.dataField.dataValue = formTimeRowV2(
                                viewModel = DataEntryScreenViewModel(),
                                fieldName = data.dataField.fieldName
                            )
                        }
                        5 -> {
                            data.dataField.dataValue =
                                formCountRowV2(data = data.dataField)
                        }
                        6 -> {
                            data.dataField.dataValue = formRadioRowV2(
                                data = data.dataField
                            )
                        }
                    }
                }

                item {
                    // Save Button Footer
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(end = 10.dp, bottom = 40.dp),
                            onClick = {
                                scope.launch {

                                    val returnedValue: Pair<Boolean, DataEntryScreenState> =
                                        validateData(uiState.value.value)


                                    if (returnedValue.first) { //means there were no errors
                                        viewModel.onEvent(DataEvent.SaveData(dataEntryScreenState = uiState.value.value,
                                            dataFieldList = returnDataFieldList(uiState.value.value)))
                                        scope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                message = "${uiState.value.value.dataName} Added!",
                                                actionLabel = "Hide"
                                            )
                                        }
                                    } else {
                                        scope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                message = "Data not Saved! Please Check errors"
                                            )
                                            viewModel.onEvent(DataEvent.UpdateUiState(
                                                returnedValue.second))
                                            uiState.value.value.dataRows =
                                                returnedValue.second.dataRows
                                        }
                                    }
                                }
                            }) {
                            Text(
                                text = "Save Data",
                                style = MaterialTheme.typography.h6,
                                color = MaterialTheme.colors.onPrimary
                            )
                        }
                    }
                }
            }

            DefaultSnackbar(
                snackbarHostState = scaffoldState.snackbarHostState,
                onDismiss = {
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                },
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}

fun returnDataFieldList(uiState: DataEntryScreenState): List<DataField> {
    val list = ArrayList<DataField>()

    for (dataRow in uiState.dataRows) {
        val newDataField = DataField(
            id = dataRow.dataField.id,
            fieldName = dataRow.dataField.fieldName,
            dataFieldType = dataRow.dataField.dataFieldType,
            dataValue = dataRow.dataField.dataValue,
            first = dataRow.dataField.first,
            second = dataRow.dataField.second,
            third = dataRow.dataField.third,
            isEnabled = dataRow.dataField.isEnabled,
            fieldHint = dataRow.dataField.fieldHint
        )
        list.add(newDataField)
    }
    return list
}

