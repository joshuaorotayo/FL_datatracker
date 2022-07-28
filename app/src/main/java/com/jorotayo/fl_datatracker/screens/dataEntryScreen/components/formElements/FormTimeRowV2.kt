package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.*

@Preview(showBackground = true)
@Composable
fun PreviewFormTimeRowV2() {
    FormTimeRowV2()
}

@Composable
fun FormTimeRowV2(

) {
    // Fetching local context
    val mContext = LocalContext.current

    // Declaring and initializing a calendar
    val mCalendar = Calendar.getInstance()
    val mHour: Int = mCalendar.get(Calendar.HOUR)
    val mMinute = mCalendar[Calendar.MINUTE]

    // Value for storing time as a string
    val mTime = remember { mutableStateOf("") }

    // Creating a TimePicker dialog
    val mTimePickerDialog = TimePickerDialog(
        mContext,
        { _, mHour: Int, mMinute: Int ->
            mTime.value = "$mHour:$mMinute"
        }, mHour, mMinute, false
    )

    Column(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(shape = RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.surface)
    ) {
        Row(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 10.dp)
                    .fillMaxWidth(),
                text = "Data Field for Time Row",
                textAlign = TextAlign.Start,
                color = Color.Gray,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            //Button Data capture
            Text(
                text = mTime.value.ifBlank { "HH:MM" },
                color = if (mTime.value.isBlank()) MaterialTheme.colors.primary else Color.Black,
                modifier = Modifier
                    .padding(start = 5.dp)
                    .weight(1f, fill = false)
                    .clickable(
                        onClick = {
                            mTimePickerDialog.show()
                        }
                    ),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1
            )
            IconButton(
                onClick = {
                    mTimePickerDialog.show()
                }) {
                Icon(
                    modifier = Modifier,
                    imageVector = Icons.Default.Timer,
                    contentDescription = "Select Time from Clock",
                    tint = MaterialTheme.colors.primary
                )
            }
        }
    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
    )
}