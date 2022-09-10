package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.domain.model.DataItem
import java.util.*


@Preview
@Composable
fun PreviewFormDateRowV2() {
    FormDateRowV2(
        data = DataRowState(
            DataItem(
                dataItemId = 0,
                fieldName = "Data Field for Date Row Example",
                first = "No",
                second = "N/A",
                third = "Yes",
                presetId = 0,
                dataId = 1
            )
        ),
        setDataValue = {}
    )
}

@Composable
fun FormDateRowV2(
    data: DataRowState,
    setDataValue: (String) -> Unit,
): String {
    //var hasError by remember{ mutableStateOf(data.hasError)}

    // Fetching the Local Context
    val mContext = LocalContext.current

    // Declaring integer values for year, month and day
    val mYear: Int
    val mMonth: Int
    val mDay: Int

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month, day and day of the week
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    // Declaring a string value to store date in string format
    val mDate = remember { mutableStateOf("") }

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, year: Int, month: Int, mDayOfMonth: Int ->
            mDate.value = formattedDateString(mDayOfMonth, month, year)
            setDataValue(mDate.value)
        }, mYear, mMonth, mDay
    )


    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(shape = RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.surface)
    ) {

        Text(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 5.dp)
                .fillMaxWidth(),
            text = data.dataItem.fieldName,
            textAlign = TextAlign.Start,
            color = Color.Gray,
        )

        AnimatedVisibility(visible = data.hasError && data.dataItem.dataValue.isBlank()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 5.dp),
                    text = stringResource(id = R.string.date_row_error),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.caption,
                    color = Color.Red,
                )
                Icon(
                    modifier = Modifier
                        .padding(end = 10.dp),
                    imageVector = Icons.Default.Warning,
                    contentDescription = stringResource(id = R.string.row_error_description),
                    tint = MaterialTheme.colors.primary
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (mDate.value.isBlank()) "DDnd Month, Year" else data.dataItem.dataValue,
                color = if (mDate.value.isBlank()) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .clickable(
                        onClick = {
                            mDatePickerDialog.show()
                        }
                    )
                    .padding(end = 10.dp),
                textAlign = TextAlign.Center,
                style = if (mDate.value.isBlank()) MaterialTheme.typography.body1 else MaterialTheme.typography.body1
            )
            IconButton(
                onClick = {
                    mDatePickerDialog.show()
                }) {
                Icon(
                    modifier = Modifier,
                    imageVector = Icons.Default.EditCalendar,
                    contentDescription = "Select Date from Calendar",
                    tint = MaterialTheme.colors.primary
                )
            }
        }
    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(5.dp)
    )

    return mDate.value
}


private val days = arrayOf("Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat")

private val suffix = arrayOf("th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th")

private val months = arrayOf(
    "January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December"
)

private fun formattedDateString(day: Int, month: Int, year: Int): String {

    val mCalendar = Calendar.getInstance()

    val day2 = day % 100
    val suffixStr = day.toString() + suffix[if (day2 in 4..20) 0 else day2 % 10]
    val monthStr = months[month]

    mCalendar.set(year, month, day)
    val dayOfWeek = days[mCalendar.get(Calendar.DAY_OF_WEEK) - 1]
    return "$dayOfWeek, $suffixStr $monthStr, $year"
}
