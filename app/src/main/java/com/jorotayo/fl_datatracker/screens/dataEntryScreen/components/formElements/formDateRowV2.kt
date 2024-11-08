package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import android.app.DatePickerDialog
import android.content.res.Configuration
import android.widget.DatePicker
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.domain.model.DataItem
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.util.Dimen.small
import java.util.Calendar
import java.util.Date


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
fun PreviewFormDateRowV2() {
    FL_DatatrackerTheme {
        formDateRowV2(
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
}

@Composable
fun formDateRowV2(
    data: DataRowState,
    setDataValue: (String) -> Unit,
): String {
    val textColour = if (isSystemInDarkTheme()) Color.DarkGray else MaterialTheme.colors.primary

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
    val mDate = remember { mutableStateOf(data.dataItem.dataValue) }

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
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colors.surface)
            .padding(small)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = data.dataItem.fieldName,
            textAlign = TextAlign.Start,
            color = MaterialTheme.colors.onSurface,
        )

        AnimatedVisibility(visible = data.hasError && data.dataItem.dataValue.isBlank()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier,
                    text = stringResource(id = R.string.date_row_error),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.caption,
                    color = Color.Red,
                )
                Icon(
                    modifier = Modifier,
                    imageVector = Icons.Default.Warning,
                    contentDescription = stringResource(id = R.string.row_error_description),
                    tint = MaterialTheme.colors.primary
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    mDatePickerDialog.show()
                })
            {
                Icon(
                    modifier = Modifier,
                    imageVector = Icons.Default.EditCalendar,
                    contentDescription = "Select Date from Calendar",
                    tint = textColour
                )
            }
            Text(
                modifier = Modifier
                    .clickable(
                        onClick = {
                            mDatePickerDialog.show()
                        }
                    )
                    .fillMaxWidth(),
                text = mDate.value.ifBlank { "DDnd Month, Year" },
                color = if (mDate.value.isBlank()) textColour else MaterialTheme.colors.onSurface,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1
            )

        }
    }

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
