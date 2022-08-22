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
import com.jorotayo.fl_datatracker.domain.util.DataFieldType
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.*
import com.jorotayo.fl_datatracker.viewModels.DataEntryScreenViewModel
import formNameHeader
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun PreviewDataEntryScreen() {
    DataEntryScreen(
        viewModel = hiltViewModel(),
        navController = rememberNavController()
    )
}

@Composable
fun DataEntryScreen(
    viewModel: DataEntryScreenViewModel,
    navController: NavController
) {
    val systemUiController = rememberSystemUiController()

    val scaffoldState = rememberScaffoldState()

    val scope = rememberCoroutineScope()

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
                        //Spacer(modifier = Modifier.height(20.dp))
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

/*
                            val shortText =
                                formShortTextRowV2(
                                    fieldName = "Short Text DataField",
                                    rowHint = "Short Text row example..."
                                )
                            formCountRowV2(fieldName = "Count DataField")
                            formDateRowV2(
                                fieldName = "Date Row DataField",
                                viewModel = DataEntryScreenViewModel()
                            )
                            formTimeRowV2(
                                fieldName = "Time Row  DataField",
                                viewModel = DataEntryScreenViewModel()
                            )
                            formRadioRowV2(
                                fieldName = "Radio Row DataField",
                                options = listOf("No", "N/A", "Yes")
                            )
                            formLongTextRowV2(
                                fieldName = "Data Field for Long Text Example",
                                fieldHint = "Data capture long text row example..."
                            )*/

                            formNameHeader()

                            for (data in dataFields) {
                                when (data.dataFieldType) {
                                    0 -> {
                                        formShortTextRowV2(
                                            fieldName = data.fieldName,
                                            rowHint = data.fieldHint
                                        )
                                    }
                                    1 -> {
                                        formLongTextRowV2(
                                            fieldName = data.fieldName,
                                            fieldHint = data.fieldHint
                                        )
                                    }
                                    2 -> {
                                        formRadioRowV2(
                                            options = listOf(data.first, data.second),
                                            fieldName = data.fieldName
                                        )
                                    }
                                    3 -> {
                                        formDateRowV2(
                                            viewModel = DataEntryScreenViewModel(),
                                            fieldName = data.fieldName
                                        )
                                    }
                                    4 -> {
                                        formTimeRowV2(
                                            viewModel = DataEntryScreenViewModel(),
                                            fieldName = data.fieldName
                                        )
                                    }
                                    5 -> {
                                        formCountRowV2(fieldName = data.fieldName)

                                    }
                                    6 -> {
                                        formRadioRowV2(
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
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                message = "Data Saved",
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
        }
    }

    fun initFakeData(): List<DataField> {
        return listOf(

            DataField(
                id = 1,
                fieldName = "SERVICE_NAME",
                dataFieldType = DataFieldType.SHORTSTRING.ordinal,
                dataValue = "",
                isEnabled = true
            ),
            DataField(
                id = 2,
                fieldName = "PREACHER",
                dataFieldType = DataFieldType.SHORTSTRING.ordinal,
                dataValue = "",
                isEnabled = true
            ),
            DataField(
                id = 3,
                fieldName = "DATE",
                dataFieldType = DataFieldType.DATE.ordinal,
                dataValue = "",
                isEnabled = true
            ),
            DataField(
                id = 4,
                fieldName = "TIME",
                dataFieldType = DataFieldType.TIME.ordinal,
                dataValue = "",
                isEnabled = true
            ),
            DataField(
                id = 5,
                fieldName = "ATTENDANCE",
                dataFieldType = DataFieldType.COUNT.ordinal,
                dataValue = "",
                isEnabled = true
            ),
            DataField(
                id = 6,
                fieldName = "TITHE_PAYERS",
                dataFieldType = DataFieldType.COUNT.ordinal,
                dataValue = "",
                isEnabled = true
            ),
            DataField(
                id = 7,
                fieldName = "COMMUNION",
                dataFieldType = DataFieldType.BOOLEAN.ordinal,
                dataValue = "",
                isEnabled = true
            ),
            DataField(
                id = 8,
                fieldName = "J-SCHOOL",
                dataFieldType = DataFieldType.TRISTATE.ordinal,
                dataValue = "",
                isEnabled = true
            ),
            DataField(
                id = 8,
                fieldName = "PREACHING_NOTES",
                dataFieldType = DataFieldType.LONGSTRING.ordinal,
                dataValue = "",
                isEnabled = true
            )
        )
    }
}
