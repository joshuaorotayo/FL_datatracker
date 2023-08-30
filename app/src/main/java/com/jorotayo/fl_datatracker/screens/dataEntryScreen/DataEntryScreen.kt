package com.jorotayo.fl_datatracker.screens.dataEntryScreen

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.navigation.MainScreens
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.*
import com.jorotayo.fl_datatracker.ui.DefaultSnackbar
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.ui.theme.subtitleTextColour
import com.jorotayo.fl_datatracker.util.Dimen.bottomBarPadding
import com.jorotayo.fl_datatracker.util.Dimen.large
import com.jorotayo.fl_datatracker.util.Dimen.medium
import com.jorotayo.fl_datatracker.util.Dimen.small
import com.jorotayo.fl_datatracker.util.Dimen.xSmall
import com.jorotayo.fl_datatracker.util.Dimen.xxxSmall
import com.jorotayo.fl_datatracker.viewModels.DataEntryScreenViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "Light Mode"
)
@Composable
fun PreviewDataEntryScreen() {
    FL_DatatrackerTheme {
        DataEntryScreen(
            navController = rememberNavController(),
            uiState = DataEntryScreenState(),
            onUiEvent = MutableSharedFlow(),
            onDataEvent = {}
        )
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DataEntryScreen(
    navController: NavController,
    uiState: DataEntryScreenState,
    onUiEvent: SharedFlow<DataEntryScreenViewModel.UiEvent>,
    onDataEvent: (DataEvent) -> Unit
) {

    val scaffoldState = rememberScaffoldState()

    val scope = rememberCoroutineScope()

    val onTakeImage = remember { mutableStateOf(true) }

    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )

    val listState = rememberLazyListState()

    LaunchedEffect(key1 = true) {
        onUiEvent.collectLatest { event ->
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
                    navController.navigate(MainScreens.HomeMainScreens.route)
                }

                DataEntryScreenViewModel.UiEvent.UpdateDataForm -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "Data Form Updated!"
                    )
                    navController.navigate(MainScreens.HomeMainScreens.route)
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {//box for ModalBottomSheet
        Scaffold(
            topBar = {
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(top = large)
                ) {
                    HeaderRow()
                    if (uiState.dataRows.isNotEmpty()) {
                        DataFormHeadings(uiState)
                    } else {
                        NoDataFormSection()
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
                    .padding(bottom = bottomBarPadding)
            ) {
                Card(
                    modifier = Modifier
                        .padding(xSmall)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    shape = RoundedCornerShape(medium),
                    elevation = xSmall
                ) {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .background(color = colors.surface)
                    ) {
                        item {
                            // Contents of data entry form
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                FormNameHeader(
                                    setName = {
                                        onDataEvent(DataEvent.SetName(value = it))
                                        onDataEvent(DataEvent.FormSubmitted)
                                    },
                                    data = uiState
                                )
                            }
                        }

                        itemsIndexed(items = uiState.dataRows) { index, data ->
                            when (data.dataItem.dataFieldType) {
                                0 -> {
                                    data.dataItem.dataValue = formShortTextRowV2(
                                        data = data,
                                        setDataValue = {
                                            onDataEvent(
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
                                            onDataEvent(
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
                                            onDataEvent(
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
                                            onDataEvent(
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
                                            onDataEvent(
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
                                            onDataEvent(
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
                                            onDataEvent(
                                                DataEvent.SetDataValue(
                                                    value = it,
                                                    rowIndex = index
                                                )
                                            )
                                        }
                                    )
                                }

                                7 -> {
                                    data.dataItem.dataValue = formImageRowV4(
                                        data = data,
                                        onClick = {
                                            onDataEvent(DataEvent.UpdateImageIndex(index))
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
                                            onDataEvent(
                                                DataEvent.SetDataValue(
                                                    value = it,
                                                    rowIndex = index
                                                )
                                            )
                                        }
                                    )
                                }
                            }
                            if (index < uiState.dataRows.size) {
                                Divider(
                                    modifier = Modifier.padding(
                                        horizontal = xSmall,
                                        vertical = xxxSmall
                                    )
                                )
                            }
                        }

                        item {
                            // Save Button Footer
                            TextButton(modifier = Modifier
                                .padding(small)
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(medium)),
                                colors = ButtonDefaults.textButtonColors(
                                    backgroundColor = colors.primary,
                                    contentColor = colors.surface
                                ),
                                onClick = {
                                    onDataEvent(DataEvent.ValidateInsertDataForm(uiState))
                                    onDataEvent(DataEvent.FormSubmitted)
                                }
                            ) {
                                Text(
                                    text = stringResource(id = R.string.save_data_btn),
                                    color = colors.onPrimary
                                )
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
                            .align(Center)
                    )
                }
            }
        }
        ImageBottomActionSheet(
            state = modalBottomSheetState,
            scope = scope,
            onTakeImage = {
                onTakeImage.value
            },
            setDataValue = {
                onDataEvent(
                    DataEvent.SetDataValue(value = it, rowIndex = uiState.currentImageIndex)
                )
            }
        )
    }
}

@Composable
private fun HeaderRow() {
    Row(
        modifier = Modifier
            .padding(start = xSmall)
            .fillMaxWidth(),
        verticalAlignment = CenterVertically
    ) {
        Text(
            modifier = Modifier,
            text = "Data Entry",
            color = colors.primary,
            style = typography.h1,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
private fun DataFormHeadings(
    uiState: DataEntryScreenState,
) {
    Row(
        modifier = Modifier,
        verticalAlignment = CenterVertically
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = small, top = small),
            text = String.format(
                stringResource(id = R.string.enter_data_header),
                uiState.presetSetting.presetName
            ),
            color = colors.subtitleTextColour,
            style = typography.h3,
            textAlign = TextAlign.Start
        )
    }
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = small,
                end = small,
            ),
        text = stringResource(id = R.string.data_entry_form_header),
        color = colors.subtitleTextColour,
        style = typography.h3,
        textAlign = TextAlign.Start
    )
    Text(
        modifier = Modifier.padding(
            start = small,
            top = small,
            bottom = small
        ),
        text = pluralStringResource(
            id = R.plurals.numberOfDatafields,
            count = uiState.dataRows.size,
            uiState.dataRows.size
        ),
        style = typography.h3,
        color = colors.subtitleTextColour
    )
}

@Composable
private fun NoDataFormSection() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = bottomBarPadding)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            NoDataForm(modifier = Modifier.align(alignment = Center))
        }
    }
}
