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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jorotayo.fl_datatracker.R
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
    systemUiController.setStatusBarColor(MaterialTheme.colors.primary)

    val scaffoldState = rememberScaffoldState()

    val scope = rememberCoroutineScope()

    var uiState = remember { mutableStateOf(viewModel.uiState) }

    viewModel.currentDataId.value = dataId

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController, bottomNavigationItems)
        },
        topBar = {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colors.primary)
            ) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colors.background,
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable {
                                navController.navigateUp()
                            }
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Enter Data: " + viewModel.boxState.value.currentPreset?.presetName!!,
                        color = MaterialTheme.colors.background,
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
                            .padding(
                                start = 16.dp,
                                end = 16.dp,
                            ),
                        text = stringResource(id = R.string.data_entry_form_header),
                        color = MaterialTheme.colors.onBackground,
                        style = MaterialTheme.typography.h6.also { FontStyle.Italic },
                        textAlign = TextAlign.Start
                    )
                    Text(
                        modifier = Modifier.padding(start = 16.dp,
                            top = 5.dp,
                            bottom = 10.dp),
                        text = String.format(stringResource(id = R.string.form_fields_description),
                            uiState.value.value.dataRows.size),
                        color = MaterialTheme.colors.onBackground,
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
                .background(color = MaterialTheme.colors.primary)
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(topEnd = 40.dp, topStart = 40.dp))
                    .background(color = MaterialTheme.colors.background)
            ) {
                item {
                    // Contents of data entry form
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier
                            .fillMaxWidth()
                            .size(16.dp))
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
                        7 -> {
                            data.dataItem.dataValue = FormImageRowV2(
                                data = data,
                                setDataValue = {
                                    viewModel.onEvent(DataEvent.SetDataValue(
                                        value = it,
                                        rowIndex = index))
                                }
                            )
                        }
                        8 -> {
                            data.dataItem.dataValue = FormListRowV2(
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
                    TextButton(modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(12f)),
                        colors = ButtonDefaults.textButtonColors(
                            backgroundColor = MaterialTheme.colors.primary,
                            contentColor = MaterialTheme.colors.surface
                        ),
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
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.save_data_btn),
                            color = MaterialTheme.colors.onSurface
                        )
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

