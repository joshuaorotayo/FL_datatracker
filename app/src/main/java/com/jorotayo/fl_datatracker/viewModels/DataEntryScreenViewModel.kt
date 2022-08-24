package com.jorotayo.fl_datatracker.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.DataEvent
import com.jorotayo.fl_datatracker.util.validateData
import io.objectbox.Box
import java.util.*
import javax.inject.Inject

class DataEntryScreenViewModel @Inject constructor() : ViewModel() {

    private val _dataFieldsBox2 = mutableStateOf(ObjectBox.get().boxFor(DataField::class.java))
    var dataFieldsBox2: State<Box<DataField>> = _dataFieldsBox2

    init {
        loadDataFields()
    }

    private fun loadDataFields() {
        _dataFieldsBox2.value = ObjectBox.get().boxFor(DataField::class.java)
    }

    private val days = arrayOf("Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat")

    private val suffix = arrayOf("th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th")

    private val months = arrayOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    fun formattedDateString(day: Int, month: Int, year: Int): String {

        val mCalendar = Calendar.getInstance()

        val day2 = day % 100
        val suffixStr = day.toString() + suffix[if (day2 in 4..20) 0 else day2 % 10]
        val monthStr = months[month]

        mCalendar.set(year, month, day)
        val dayOfWeek = days[mCalendar.get(Calendar.DAY_OF_WEEK) - 1]
        return "$dayOfWeek, $suffixStr $monthStr, $year"
    }

    // Time Functions
    fun formattedTimeString(hour: Int, minute: Int): String {
        val mTime = Calendar.getInstance()
        var amPm = ""

        mTime.set(Calendar.HOUR_OF_DAY, hour)
        mTime.set(Calendar.MINUTE, minute)

        amPm = if (mTime.get(Calendar.AM_PM) == Calendar.AM) "AM" else "PM"

        val formattedHrs =
            if (mTime.get(Calendar.HOUR) == 0) "12" else mTime.get(Calendar.HOUR).toString()

        val formattedMinute = mTime.get(Calendar.MINUTE)

        return "$formattedHrs:$formattedMinute $amPm"

    }

    fun onEvent(event: DataEvent) {
        when (event) {
            is DataEvent.SaveData -> {
                validateData(event.value)
            }
        }
    }
}