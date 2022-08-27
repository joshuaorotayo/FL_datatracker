package com.jorotayo.fl_datatracker.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.Data
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.model.DataField_
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.DataEvent
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.DataEntryScreenState
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.DataRowState
import com.jorotayo.fl_datatracker.util.getCurrentDateTime
import com.jorotayo.fl_datatracker.util.toString
import io.objectbox.Box
import java.util.*
import javax.inject.Inject

class DataEntryScreenViewModel @Inject constructor() : ViewModel() {

    private val _dataFieldsBox2 = mutableStateOf(ObjectBox.get().boxFor(DataField::class.java))
    var dataFieldsBox2: State<Box<DataField>> = _dataFieldsBox2

    private var _dataBox = mutableStateOf(ObjectBox.get().boxFor(Data::class.java))
    val dataBox: State<Box<Data>> = _dataBox

    private val _currentDataId = mutableStateOf(0.toLong())
    var currentDataId: MutableState<Long> = _currentDataId

    private val _currentDataFields = mutableStateOf(listOf<DataField>())
    var currentDataFields: MutableState<List<DataField>> = _currentDataFields

    private val _dataName = mutableStateOf("")
    var dataName: MutableState<String> = _dataName

    private var _uiState = mutableStateOf(DataEntryScreenState(
        dataName = dataName.value,
        dataRows = makeDataRows(),
        nameError = false
    ))
    val uiState: State<DataEntryScreenState> = _uiState

/*

    init {
        loadDataFields()
    }

    private fun loadDataFields() {
        _dataFieldsBox2.value = ObjectBox.get().boxFor(DataField::class.java)
    }
*/

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

                val date = getCurrentDateTime()
                val dateInString = date.toString("dd/MM/yyyy HH:mm:ss")

                val newData = Data(
                    id = 0,
                    dataFields = returnDataFieldList(event.dataEntryScreenState),
                    name = event.dataEntryScreenState.dataName,
                    lastEdited = dateInString
                )

                dataBox.value.put(newData)
            }
            is DataEvent.SetName -> {
                _uiState.value = uiState.value.copy(
                    dataName = event.value
                )
            }
            is DataEvent.UpdateUiState -> {
                _uiState.value = uiState.value.copy(
                    dataRows = event.value.dataRows
                )
            }
        }
    }

    private fun makeDataRows(): MutableList<DataRowState> {
        val list: MutableList<DataRowState> = ArrayList()
        if (currentDataId.value != (0).toLong()) {
            //Returns all enabled data fields in the data item
            // val dataFields = dataBox.value.get(currentDataId.value).dataFields.filter { it.isEnabled }

            // All stored fields should be enabled
            currentDataFields.value = dataBox.value.get(currentDataId.value).dataFields.toList()

            //uses the Data fields values to populate the Data Rows
            for (df in currentDataFields.value) {
                list.add(DataRowState(
                    dataField = df,
                    hasError = false,
                    errorMsg = ""
                ))
            }
        } else {
            currentDataFields.value =
                _dataFieldsBox2.value.query().equal(DataField_.isEnabled, true).build()
                    .use { it.find() }
            //uses the Data fields values to populate the Data Rows
            for (df in currentDataFields.value) {
                list.add(DataRowState(
                    dataField = df,
                    hasError = false,
                    errorMsg = ""
                ))
            }
        }

        return list
    }
}

fun returnDataFieldList(uiState: DataEntryScreenState): List<DataField> {
    val list = java.util.ArrayList<DataField>()

    for (dataRow in uiState.dataRows) {
        val newDataField = DataField(
            id = dataRow.dataField.id,
            fieldName = dataRow.dataField.fieldName,
            dataFieldType = dataRow.dataField.dataFieldType,
            dataValue = dataRow.dataField.dataValue,
            first = dataRow.dataField.first,
            second = dataRow.dataField.second,
            third = dataRow.dataField.third,
            isEnabled = dataRow.dataField.isEnabled,
            fieldHint = dataRow.dataField.fieldHint
        )
        list.add(newDataField)
    }
    return list
}