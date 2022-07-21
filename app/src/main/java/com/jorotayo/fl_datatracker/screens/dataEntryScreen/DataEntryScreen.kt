package com.jorotayo.fl_datatracker.screens.dataEntryScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.util.DataFieldType
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.*
import com.jorotayo.fl_datatracker.viewModels.DataEntryScreenViewModel

@Preview(showBackground = true)
@Composable
fun PreviewDataEntryScreen() {
    DataEntryScreen()
}

@Composable
fun DataEntryScreen(
) {
    val meetingName by remember { mutableStateOf("Fill in the information below:") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.primary)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(vertical = 20.dp, horizontal = 8.dp)
                .clip(shape = RoundedCornerShape(20.dp))
                .background(MaterialTheme.colors.primaryVariant.copy(alpha = 0.3f))
        )
        {
            // Contents of data entry form
            Column(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, bottom = 20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = meetingName,
                        color = MaterialTheme.colors.surface,
                        style = MaterialTheme.typography.h6.also { FontStyle.Italic },
                        textAlign = TextAlign.Start
                    )
                }

                FormDateRow(
                    viewModel = DataEntryScreenViewModel()
                )
                FormCountRow()
                FormRadioRow(options = listOf("No", "N/A", "Yes"))
                FormShortTextRow(rowHint = "Short String Hint")
                FormTimeRow()
            }
        }
    }
}

fun initFakeData(): List<DataField> {
    val dataField = listOf(

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
    return dataField
}
