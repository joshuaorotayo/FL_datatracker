package com.jorotayo.fl_datatracker.screens.dataEntryScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.jorotayo.fl_datatracker.domain.util.DataFieldType
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.*
import com.jorotayo.fl_datatracker.viewModels.DataEntryScreenViewModel

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
    val dataEntryHeading by remember { mutableStateOf("Fill in the information below:") }

    val systemUiController = rememberSystemUiController()

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
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = MaterialTheme.colors.background)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(vertical = 20.dp, horizontal = 8.dp)
                    .clip(shape = RoundedCornerShape(20.dp))
//                    .background(MaterialTheme.colors.primaryVariant.copy(alpha = 0.3f))
                    .background(MaterialTheme.colors.onBackground)
            ) {
                // Contents of data entry form
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 10.dp, end = 10.dp, bottom = 20.dp)
                        .verticalScroll(rememberScrollState())
                ) {

                    //Spacer(modifier = Modifier.height(20.dp))
                    if (!viewModel.dataFieldsBox.isEmpty) {
                        Spacer(modifier = Modifier.weight(1f))

                        //No Data Form Message
                        NoDataForm()

                        Spacer(modifier = Modifier.weight(1f))
                    } else {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp)
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = dataEntryHeading,
                                color = MaterialTheme.colors.onSurface,
                                style = MaterialTheme.typography.h6.also { FontStyle.Italic },
                                textAlign = TextAlign.Start
                            )
                        }

                        FormShortTextRowV2(rowHint = "Short Text row example...")
                        FormCountRowV2()
                        FormDateRowV2(DataEntryScreenViewModel())
                        FormTimeRowV2()
                        FormRadioRowV2(options = listOf("No", "N/A", "Yes"))
                        FormLongTextRowV2(rowHint = "Data Capture V2 long text")

                        Button(
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(end = 10.dp, bottom = 10.dp),
                            onClick = {
                                /*TODO*/
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

fun initFakeData(): List<DataField> {
    return listOf(

        DataField(
            id = 1,
            fieldName = "SERVICE_NAME",
            niceFieldName = "Service/Meeting Name",
            dataFieldType = DataFieldType.SHORTSTRING.type,
            dataValue = "",
            isEnabled = true
        ),
        DataField(
            id = 2,
            fieldName = "PREACHER",
            niceFieldName = "Preacher/Leader's Name",
            dataFieldType = DataFieldType.SHORTSTRING.type,
            dataValue = "",
            isEnabled = true
        ),
        DataField(
            id = 3,
            fieldName = "DATE",
            niceFieldName = "Date of Service/Meeting",
            dataFieldType = DataFieldType.DATE.type,
            dataValue = "",
            isEnabled = true
        ),
        DataField(
            id = 4,
            fieldName = "TIME",
            niceFieldName = "Time of Service/Meeting",
            dataFieldType = DataFieldType.TIME.type,
            dataValue = "",
            isEnabled = true
        ),
        DataField(
            id = 5,
            fieldName = "ATTENDANCE",
            niceFieldName = "Total Attendance",
            dataFieldType = DataFieldType.COUNT.type,
            dataValue = "",
            isEnabled = true
        ),
        DataField(
            id = 6,
            fieldName = "TITHE_PAYERS",
            niceFieldName = "Number of Tithe Payers",
            dataFieldType = DataFieldType.COUNT.type,
            dataValue = "",
            isEnabled = true
        ),
        DataField(
            id = 7,
            fieldName = "COMMUNION",
            niceFieldName = "Was Communion Taken",
            dataFieldType = DataFieldType.BOOLEAN.type,
            dataValue = "",
            isEnabled = true
        ),
        DataField(
            id = 8,
            fieldName = "J-SCHOOL",
            niceFieldName = "Was J-School Taught?",
            dataFieldType = DataFieldType.TRISTATE.type,
            dataValue = "",
            isEnabled = true
        ),
        DataField(
            id = 8,
            fieldName = "PREACHING_NOTES",
            niceFieldName = "NOTES",
            dataFieldType = DataFieldType.LONGSTRING.type,
            dataValue = "",
            isEnabled = true
        )
    )
}
