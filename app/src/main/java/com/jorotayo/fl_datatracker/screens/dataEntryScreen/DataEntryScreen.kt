package com.jorotayo.fl_datatracker.screens.dataEntryScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jorotayo.fl_datatracker.navigation.Screen
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.*
import com.jorotayo.fl_datatracker.screens.homeScreen.components.BottomNavigationBar
import com.jorotayo.fl_datatracker.ui.DefaultSnackbar
import com.jorotayo.fl_datatracker.util.validateData
import com.jorotayo.fl_datatracker.viewModels.DataEntryScreenViewModel
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun PreviewDataEntryScreen() {
    DataEntryScreen(
        viewModel = hiltViewModel(),
        navController = rememberNavController(),
        dataId = -1L
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DataEntryScreen(
    viewModel: DataEntryScreenViewModel,
    navController: NavController,
    dataId: Long,
) {
    val bottomNavigationItems = listOf(
        Screen.DataFieldsScreen,
        Screen.HomeScreen,
        Screen.DataEntry
    )

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(MaterialTheme.colors.background)

    val scaffoldState = rememberScaffoldState()

    val scope = rememberCoroutineScope()

    var uiState = remember { mutableStateOf(viewModel.uiState) }

    viewModel.currentDataId.value = dataId

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController, bottomNavigationItems)
        },
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
                        modifier = Modifier.padding(start = 16.dp, top = 5.dp, bottom = 5.dp),
                        text = "The form below has been created from the ${uiState.value.value.dataRows.size} DataField(s) in the preset: ",
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.body1.also { FontStyle.Italic },
                        textAlign = TextAlign.Start
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 16.dp, top = 5.dp, bottom = 5.dp)
                            .clip(shape = RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colors.surface)
                            .padding(10.dp),
                        text = viewModel.boxState.value.currentPreset?.presetName!!,
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.body1.also { FontStyle.Italic }
                            .also { FontWeight.Bold },
                        textAlign = TextAlign.End
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
                    .clip(shape = RoundedCornerShape(10.dp))
            ) {
                item {
                    // Contents of data entry form
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {

                        FormNameHeader(
                            setName = {
                                viewModel.onEvent(DataEvent.SetName(value = it))
                                uiState.value.value.dataName = it
                            },
                            dataEntryScreenState = uiState.value.value
                        )
                    }
                }

                itemsIndexed(items = uiState.value.value.dataRows) { index, data ->
                    when (data.dataItem.dataFieldType) {
                        0 -> {
                            data.dataItem.dataValue = FormShortTextRowV2(
                                data = data,
                                setDataValue = {
                                    viewModel.onEvent(DataEvent.SetDataValue(
                                        value = it,
                                        rowIndex = index))
                                    //uiState.value.value.dataRows[index].dataItem.dataValue = it
                                }
                            )

                        }
                        1 -> {
                            data.dataItem.dataValue = FormLongTextRowV2(
                                data = data.dataItem,
                                setDataValue = {
                                    viewModel.onEvent(DataEvent.SetDataValue(
                                        value = it,
                                        rowIndex = index))
                                }
                            )
                        }
                        2 -> {
                            data.dataItem.dataValue = FormRadioRowV2(
                                data = data,
                                setDataValue = {
                                    viewModel.onEvent(DataEvent.SetDataValue(
                                        value = it,
                                        rowIndex = index))
                                }
                            )
                        }
                        3 -> {
                            data.dataItem.dataValue = FormDateRowV2(
                                data = data,
                                setDataValue = {
                                    viewModel.onEvent(DataEvent.SetDataValue(
                                        value = it,
                                        rowIndex = index))
                                }
                            )
                        }
                        4 -> {
                            data.dataItem.dataValue = FormTimeRowV2(
                                data = data,
                                setDataValue = {
                                    viewModel.onEvent(DataEvent.SetDataValue(
                                        value = it,
                                        rowIndex = index))
                                }
                            )
                        }
                        5 -> {
                            data.dataItem.dataValue = FormCountRowV2(
                                data = data,
                                setDataValue = {
                                    viewModel.onEvent(DataEvent.SetDataValue(
                                        value = it,
                                        rowIndex = index))
                                }
                            )
                        }
                        6 -> {
                            data.dataItem.dataValue = FormRadioRowV2(
                                data = data,
                                setDataValue = {
                                    viewModel.onEvent(DataEvent.SetDataValue(
                                        value = it,
                                        rowIndex = index))
                                }
                            )
                        }
                        6 -> {
                            data.dataItem.dataValue = FormRadioRowV2(
                                data = data,
                                setDataValue = {
                                    viewModel.onEvent(DataEvent.SetDataValue(
                                        value = it,
                                        rowIndex = index))
                                }
                            )
                        }
                        7 -> {
                            data.dataItem.dataValue = FormRadioRowV2(
                                data = data,
                                setDataValue = {
                                    viewModel.onEvent(DataEvent.SetDataValue(
                                        value = it,
                                        rowIndex = index))
                                }
                            )
                        }
                        8 -> {
                            data.dataItem.dataValue = FormRadioRowV2(
                                data = data,
                                setDataValue = {
                                    viewModel.onEvent(DataEvent.SetDataValue(
                                        value = it,
                                        rowIndex = index))
                                }
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
                                        viewModel.onEvent(DataEvent.SaveData(dataEntryScreenState = uiState.value.value))

                                        navController.popBackStack()

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

