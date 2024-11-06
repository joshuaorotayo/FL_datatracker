package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import android.app.TimePickerDialog
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
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
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.domain.model.DataItem
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.ui.theme.bodyTextColour
import com.jorotayo.fl_datatracker.ui.theme.subtitleTextColour
import com.jorotayo.fl_datatracker.util.Dimen.xSmall
import java.util.Calendar

@DefaultPreviews
@Composable
fun PreviewFormTimeRowV2() {
    FL_DatatrackerTheme {
        formTimeRowV2(
            data = DataRowState(
                DataItem(
                    dataItemId = 0,
                    fieldName = "Data Field for Time Example",
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
fun formTimeRowV2(
    data: DataRowState,
    setDataValue: (String) -> Unit,
): String {
    // Fetching local context
    val mContext = LocalContext.current

    // Declaring and initializing a calendar
    val mCalendar = Calendar.getInstance()
    val mHour: Int = mCalendar.get(Calendar.HOUR)
    val mMinute = mCalendar[Calendar.MINUTE]

    // Value for storing time as a string
    val mTime = remember { mutableStateOf(data.dataItem.dataValue) }

    // Creating a TimePicker dialog
    val mTimePickerDialog = TimePickerDialog(
        mContext,
        { _, hour: Int, minute: Int ->
            mTime.value = formattedTimeString(hour, minute)
            setDataValue(mTime.value)
        },
        mHour,
        mMinute,
        true
    )

    val textColour = if (isSystemInDarkTheme()) Color.DarkGray else MaterialTheme.colors.primary

    Column(
        modifier = Modifier
            .padding(xSmall)
            .fillMaxWidth()
            .wrapContentSize()
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = data.dataItem.fieldName,
            textAlign = TextAlign.Start,
            color = MaterialTheme.colors.onSurface
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
                    text = stringResource(id = R.string.time_row_error),
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
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(
                onClick = {
                    mTimePickerDialog.show()
                }
            ) {
                Icon(
                    modifier = Modifier,
                    imageVector = Icons.Default.Timer,
                    contentDescription = "Select Time from Clock",
                    tint = MaterialTheme.colors.primary
                )
            }
            Text(
                modifier = Modifier
                    .clickable(
                        onClick = {
                            mTimePickerDialog.show()
                        }
                    )
                    .wrapContentWidth(),
                text = mTime.value.ifBlank { "HH:MM" },
                color = if (mTime.value.isBlank())
                    MaterialTheme.colors.bodyTextColour else MaterialTheme.colors.subtitleTextColour,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1
            )
        }
    }

    return mTime.value
}

private fun formattedTimeString(hour: Int, minute: Int): String {
    val mTime = Calendar.getInstance()

    mTime.set(Calendar.HOUR_OF_DAY, hour)
    mTime.set(Calendar.MINUTE, minute)

    val amPm: String = if (mTime.get(Calendar.AM_PM) == Calendar.AM) "AM" else "PM"

    val formattedHrs =
        if (mTime.get(Calendar.HOUR) == 0) "12" else mTime.get(Calendar.HOUR).toString()

    val formattedMinute =
        if (mTime.get(Calendar.MINUTE) < 10) {
            "0" + mTime.get(Calendar.MINUTE)
        } else {
            "" + mTime.get(
                Calendar.MINUTE
            )
        }

    return "$formattedHrs:$formattedMinute $amPm"
}
