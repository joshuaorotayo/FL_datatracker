package com.jorotayo.fl_datatracker.viewModels

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class DataEntryScreenViewModel @Inject constructor() : ViewModel() {

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