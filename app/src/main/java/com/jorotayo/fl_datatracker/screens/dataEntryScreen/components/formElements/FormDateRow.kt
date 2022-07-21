package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import android.app.DatePickerDialog
import android.widget.DatePicker
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jorotayo.fl_datatracker.viewModels.DataEntryScreenViewModel
import java.util.*


@Preview
@Composable
fun PreviewFormDateRow() {
    FormDateRow(
        viewModel = hiltViewModel()
    )
}

@Composable
fun FormDateRow(
    viewModel: DataEntryScreenViewModel
) {

    // Fetching the Local Context
    val mContext = LocalContext.current

    // Declaring integer values for year, month and day
    val mYear: Int
    val mMonth: Int
    val mDay: Int
    val mDayOfWeek: Int

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month, day and day of the week
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
    mDayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK)

    mCalendar.time = Date()

    // Declaring a string value to store date in string format
    val mDate = remember { mutableStateOf("") }

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = viewModel.formattedDateString(mDayOfWeek, mDayOfMonth, mMonth, mYear)
        }, mYear, mMonth, mDay
    )

    Box(
        modifier = Modifier
            .wrapContentSize(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .clip(shape = RoundedCornerShape(10.dp))
                .shadow(elevation = 4.dp)
                .background(MaterialTheme.colors.primary),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(0.5f),
                text = "TEST",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onPrimary
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .fillMaxHeight()
                    .background(MaterialTheme.colors.onPrimary),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            )
            {
                Text(
                    text = mDate.value.ifBlank { "DDnd Month, Year" },
                    color = if (mDate.value.isBlank()) MaterialTheme.colors.primary else Black,
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .weight(1f, fill = false)
                        .clickable(
                            onClick = {
                                mDatePickerDialog.show()
                            }
                        ),
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
    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
    )

}
