package com.jorotayo.fl_datatracker.screens.dataFieldsScreenRework.components

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.formattedDateString
import com.jorotayo.fl_datatracker.ui.DefaultDualPreview
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.ui.theme.isDarkMode
import com.jorotayo.fl_datatracker.util.Dimen.iconSize
import com.jorotayo.fl_datatracker.util.Dimen.medium
import com.jorotayo.fl_datatracker.util.Dimen.xSmall
import com.jorotayo.fl_datatracker.util.Dimen.xxSmall
import com.jorotayo.fl_datatracker.util.Dimen.zero
import java.util.Calendar
import java.util.Date


@DefaultDualPreview
@Composable
fun PreviewMinimalDateField() {
    FL_DatatrackerTheme {
        Column {
            minimalDateField(
                rowHeader = "this is a sample row",
                isError = false,
                rowHint = ""
            )
        }
    }
}

@Composable
fun minimalDateField(
    rowHeader: String,
    rowHint: String,
    isError: Boolean
): String {

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

        },
        mYear,
        mMonth,
        mDay
    )

    var cardElevation by remember { mutableStateOf(xSmall) }
    val focusManager = LocalFocusManager.current


    Column(
        modifier = Modifier
            .wrapContentSize()
    )
    {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(xxSmall)
                .onFocusChanged {
                    cardElevation = if (it.isFocused) medium else xSmall
                },
            shape = RoundedCornerShape(xSmall),
            backgroundColor = if (!isDarkMode() && (cardElevation == medium)) colors.surface.copy(
                alpha = 0.5f
            ) else colors.surface,
            elevation = if (isDarkMode()) cardElevation else zero
        ) {
            Column(
                modifier = Modifier
                    .padding(xxSmall)
                    .fillMaxWidth()
                    .clickable(
                        onClick = {
                            mDatePickerDialog.show()
                        }
                    )
            ) {

                Text(
                    text = rowHeader,
                    style = typography.body1,
                    color = DarkGray
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        modifier = Modifier
                            .size(iconSize)
                            .padding(end = xxSmall),
                        imageVector = Icons.Default.EditCalendar,
                        contentDescription = "Select Date from Calendar",
                        tint = colors.primary
                    )
                    Text(
                        modifier = Modifier
                            .wrapContentWidth(),
                        text = mDate.value.ifBlank { "Please Select DDnd Month, Year" },
                        color = if (mDate.value.isBlank()) DarkGray else colors.onSurface,
                        textAlign = TextAlign.Center,
                        style = typography.body1
                    )
                }
                if (isError) {
                    Text(text = "Please enter in a value", color = Color.Red)
                }
            }
        }
    }
    return mDate.value
}
