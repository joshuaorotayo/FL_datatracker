package com.jorotayo.fl_datatracker.viewModels

import androidx.lifecycle.ViewModel
import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.DataField
import io.objectbox.Box
import javax.inject.Inject

class DataEntryScreenViewModel @Inject constructor() : ViewModel() {

    val dataFieldsBox: Box<DataField> = ObjectBox.get().boxFor(DataField::class.java)

    private val suffix = arrayOf("th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th")
    private val months = arrayOf(
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December"
    )
    private val days = arrayOf("Mon", "Tue", "Wed", "Thur", "Fri", "Sat", "Sun")

    fun formattedDateString(dayOfWeek: Int, day: Int, month: Int, year: Int): String {
        val dayStr = days[dayOfWeek]
        val day2 = day % 100
        val suffixStr = day.toString() + suffix[if (day2 in 4..20) 0 else day2 % 10]
        val monthStr = months[month]
        return "$dayStr, $suffixStr $monthStr, $year"
    }
}