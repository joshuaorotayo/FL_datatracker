package com.jorotayo.fl_datatracker.screens.dataEntryScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.model.DataField_
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.*
import com.jorotayo.fl_datatracker.ui.DefaultSnackbar
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

    var name = remember { mutableStateOf("") }
    val allDataFields = ObjectBox.get().boxFor(DataField::class.java)
    val dataFields =
        allDataFields.query().equal(DataField_.isEnabled, true).build().use { it.find() }

    systemUiController.setStatusBarColor(MaterialTheme.colors.background)
    Scaffold(
        topBar = {
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
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .wrapContentHeight()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(vertical = 10.dp)
                ) {
                    // Contents of data entry form
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        val testRun = true
                        if (viewModel.dataFieldsBox2.value.isEmpty && !testRun) {
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
                                        bottom = 10.dp,
                                        start = 16.dp,
                                        end = 16.dp
                                    ),
                                text = "The form below has been created from the ${dataFields.size} DataField(s) in the preset",
                                color = MaterialTheme.colors.primary,
                                style = MaterialTheme.typography.body1.also { FontStyle.Italic },
                                textAlign = TextAlign.Start
                            )

                            name.value = formNameHeader()

                            for (data in dataFields) {
                                when (data.dataFieldType) {
                                    0 -> {
                                        data.dataValue = formShortTextRowV2(
                                            fieldName = data.fieldName,
                                            rowHint = data.fieldHint,
                                            hasError = false
                                        )
                                    }
                                    1 -> {
                                        data.dataValue = formLongTextRowV2(
                                            fieldName = data.fieldName,
                                            fieldHint = data.fieldHint
                                        )
                                    }
                                    2 -> {
                                        data.dataValue = formRadioRowV2(
                                            options = listOf(
                                                data.first,
                                                data.second
                                            ),
                                            fieldName = data.fieldName
                                        )
                                    }
                                    3 -> {
                                        data.dataValue = formDateRowV2(
                                            viewModel = DataEntryScreenViewModel(),
                                            fieldName = data.fieldName
                                        )
                                    }
                                    4 -> {
                                        data.dataValue = formTimeRowV2(
                                            viewModel = DataEntryScreenViewModel(),
                                            fieldName = data.fieldName
                                        )
                                    }
                                    5 -> {
                                        data.dataValue = formCountRowV2(fieldName = data.fieldName)
                                    }
                                    6 -> {
                                        data.dataValue = formRadioRowV2(
                                            options = listOf(
                                                data.first,
                                                data.second,
                                                data.third
                                            ),
                                            fieldName = data.fieldName
                                        )
                                    }
                                }
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                            ) {
                                Button(
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                        .padding(end = 10.dp, bottom = 40.dp),
                                    onClick = {
                                        scope.launch {

                                            val list = ArrayList<DataRowState>()
                                            for (df in dataFields) {
                                                list.add(DataRowState(
                                                    dataField = df,
                                                    hasError = false,
                                                    errorMsg = ""
                                                ))

                                            }

                                            val newDataEntryScreenState = DataEntryScreenState(
                                                dataName = name.value,
                                                dataRows = list
                                            )

                                            viewModel.onEvent(DataEvent.SaveData(value = newDataEntryScreenState))
                                            val sb = StringBuilder()
                                            dataFields.forEach { sb.append(it.dataValue + ",") }
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                message = "${name.value} - $sb",
                                                actionLabel = "Hide"
                                            )

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
