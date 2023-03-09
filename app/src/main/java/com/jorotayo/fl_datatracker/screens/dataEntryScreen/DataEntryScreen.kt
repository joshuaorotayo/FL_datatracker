package com.jorotayo.fl_datatracker.screens.dataEntryScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.navigation.Screen
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.*
import com.jorotayo.fl_datatracker.screens.homeScreen.components.BottomNavigationBar
import com.jorotayo.fl_datatracker.ui.DefaultSnackbar
import com.jorotayo.fl_datatracker.viewModels.DataEntryScreenViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(showBackground = true)
@Composable
fun PreviewDataEntryScreen() {
    DataEntryScreen(
        viewModel = hiltViewModel(),
        navController = rememberNavController()
    )
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DataEntryScreen(
    viewModel: DataEntryScreenViewModel,
    navController: NavController,
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

    val uiState = remember { mutableStateOf(viewModel.uiState) }

    val onTakeImage = remember { mutableStateOf(true) }

    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )

    val listState = rememberLazyListState()

    var currentImageIndex = remember { mutableStateOf(viewModel.currentImageIndex) }

    var formSubmitted = false


//    viewModel.currentDataId.value = dataId

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is DataEntryScreenViewModel.UiEvent.ShowSnackbar -> {
                    listState.animateScrollToItem(index = 0)
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

                DataEntryScreenViewModel.UiEvent.SaveDataForm -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "Data Form Saved!"
                    )
                    navController.navigate(Screen.HomeScreen.route)
                }

                DataEntryScreenViewModel.UiEvent.UpdateDataForm -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "Data Form Updated!"
                    )
                    navController.navigate(Screen.HomeScreen.route)
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {//box for ModalBottomSheet
        Scaffold(
            bottomBar = {
                BottomNavigationBar(navController, bottomNavigationItems)
            },
            topBar = {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colors.primary)
                        .fillMaxHeight(0.2f)
                ) {
                    if (uiState.value.value.dataRows.isNotEmpty()) {
                        Row(
                            modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = MaterialTheme.colors.onPrimary,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .clickable {
                                        navController.navigateUp()
                                    }
                            )
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = String.format(
                                    stringResource(id = R.string.enter_data_header),
                                    viewModel.presetSetting.presetName
                                ),
                                color = MaterialTheme.colors.onPrimary,
                                style = MaterialTheme.typography.h5.also { FontStyle.Italic },
                                textAlign = TextAlign.Start
                            )
                        }

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp,
                                    end = 16.dp,
                                ),
                            text = stringResource(id = R.string.data_entry_form_header),
                            color = MaterialTheme.colors.onPrimary,
                            style = MaterialTheme.typography.h6.also { FontStyle.Italic },
                            textAlign = TextAlign.Start
                        )
                        Text(
                            modifier = Modifier.padding(
                                start = 16.dp,
                                top = 5.dp,
                                bottom = 10.dp
                            ),
                            text = String.format(
                                stringResource(id = R.string.form_fields_description),
                                uiState.value.value.dataRows.size
                            ),
                            color = MaterialTheme.colors.onPrimary,
                            style = MaterialTheme.typography.body1.also { FontStyle.Italic },
                            textAlign = TextAlign.Start
                        )
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colors.primary)
                        ) {

                        }
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
                    state = listState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxSize()
                        .clip(shape = RoundedCornerShape(topEnd = 40.dp, topStart = 40.dp))
                        .background(color = MaterialTheme.colors.background)
                ) {
                    if (uiState.value.value.dataRows.isEmpty()) {
                        item {
                            NoDataForm()
                        }
                    } else {
                        item {
                            // Contents of data entry form
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Spacer(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .size(16.dp)
                                )
                                formNameHeader(
                                    setName = {
                                        viewModel.onEvent(DataEvent.SetName(value = it))
                                        uiState.value.value.dataName = it
                                    },
                                    data = uiState.value.value,
                                    formSubmitted = formSubmitted
                                )
                            }
                        }

                        itemsIndexed(items = uiState.value.value.dataRows) { index, data ->
                            when (data.dataItem.dataFieldType) {
                                0 -> {
                                    data.dataItem.dataValue = formShortTextRowV2(
                                        data = data,
                                        setDataValue = {
                                            viewModel.onEvent(
                                                DataEvent.SetDataValue(
                                                    value = it,
                                                    rowIndex = index
                                                )
                                            )
                                        }
                                    )
                                }
                                1 -> {
                                    data.dataItem.dataValue = formLongTextRowV2(
                                        data = data.dataItem,
                                        setDataValue = {
                                            viewModel.onEvent(
                                                DataEvent.SetDataValue(
                                                    value = it,
                                                    rowIndex = index
                                                )
                                            )
                                        }
                                    )
                                }
                                2 -> {
                                    data.dataItem.dataValue = formRadioRowV2(
                                        data = data,
                                        setDataValue = {
                                            viewModel.onEvent(
                                                DataEvent.SetDataValue(
                                                    value = it,
                                                    rowIndex = index
                                                )
                                            )
                                        }
                                    )
                                }
                                3 -> {
                                    data.dataItem.dataValue = formDateRowV2(
                                        data = data,
                                        setDataValue = {
                                            viewModel.onEvent(
                                                DataEvent.SetDataValue(
                                                    value = it,
                                                    rowIndex = index
                                                )
                                            )
                                        }
                                    )
                                }
                                4 -> {
                                    data.dataItem.dataValue = formTimeRowV2(
                                        data = data,
                                        setDataValue = {
                                            viewModel.onEvent(
                                                DataEvent.SetDataValue(
                                                    value = it,
                                                    rowIndex = index
                                                )
                                            )
                                        }
                                    )
                                }
                                5 -> {
                                    data.dataItem.dataValue = formCountRowV2(
                                        data = data,
                                        setDataValue = {
                                            viewModel.onEvent(
                                                DataEvent.SetDataValue(
                                                    value = it,
                                                    rowIndex = index
                                                )
                                            )
                                        }
                                    )
                                }
                                6 -> {
                                    data.dataItem.dataValue = formRadioRowV2(
                                        data = data,
                                        setDataValue = {
                                            viewModel.onEvent(
                                                DataEvent.SetDataValue(
                                                    value = it,
                                                    rowIndex = index
                                                )
                                            )
                                        }
                                    )
                                }
                                /*7 -> {
                                    data.dataItem.dataValue = formImageRowV3(
                                        data = data,
                                        onClick = {
                                            viewModel.currentImageIndex.value = index
                                        },
                                        showBottomSheet = {
                                            scope.launch {
                                                modalBottomSheetState.show()
                                            }
                                        }
                                    )
                                }*/
                                7 -> {
                                    data.dataItem.dataValue = formImageRowV4(
                                        data = data,
                                        onClick = {
                                            viewModel.currentImageIndex.value = index
                                        },
                                        showBottomSheet = {
                                            scope.launch {
                                                modalBottomSheetState.show()
                                            }
                                        }
                                    )
                                }

                                8 -> {
                                    data.dataItem.dataValue = formListRowV4(
                                        data = data,
                                        setDataValue = {
                                            viewModel.onEvent(
                                                DataEvent.SetDataValue(
                                                    value = it,
                                                    rowIndex = index
                                                )
                                            )
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
                                    viewModel.onEvent(DataEvent.ValidateInsertDataForm(uiState.value.value))
                                    formSubmitted = true
                                }
                            ) {
                                Text(
                                    text = stringResource(id = R.string.save_data_btn),
                                    color = MaterialTheme.colors.onPrimary
                                )
                            }
                        }
                    }
                    //close Box
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
        ImageBottomActionSheet(
            state = modalBottomSheetState,
            scope = scope,
            onTakeImage = {
                onTakeImage.value
            },
            setDataValue = {
                viewModel.onEvent(
                    DataEvent.SetDataValue(value = it, rowIndex = currentImageIndex.value.value)
                )
            }
        )
    }
}

