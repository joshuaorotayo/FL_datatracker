package com.jorotayo.fl_datatracker.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.util.DataFieldType
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.DataEntryEvent
import io.objectbox.Box
import javax.inject.Inject

class DataFieldsViewModel @Inject constructor() : ViewModel() {
    val dataFieldsBox: Box<DataField> = ObjectBox.get().boxFor(DataField::class.java)

    private var _isVisible = mutableStateOf(false)
    private var isVisible: State<Boolean> = _isVisible


    fun onEvent(event: DataEntryEvent) {
        when (event) {
            DataEntryEvent.ToggleAddNewDataField -> {
                _isVisible.value = !isVisible.value
            }
        }
    }

}


fun initFakeData(): List<DataField> {
    return listOf(

        DataField(
            id = 1,
            fieldName = "SERVICE_NAME",
            niceFieldName = "Service/Meeting Name",
            dataFieldType = DataFieldType.SHORTSTRING.type,
            dataValue = "",
            isEnabled = true
        ),
        DataField(
            id = 2,
            fieldName = "PREACHER",
            niceFieldName = "Preacher/Leader's Name",
            dataFieldType = DataFieldType.SHORTSTRING.type,
            dataValue = "",
            isEnabled = true
        ),
        DataField(
            id = 3,
            fieldName = "DATE",
            niceFieldName = "Date of Service/Meeting",
            dataFieldType = DataFieldType.DATE.type,
            dataValue = "",
            isEnabled = true
        ),
        DataField(
            id = 4,
            fieldName = "TIME",
            niceFieldName = "Time of Service/Meeting",
            dataFieldType = DataFieldType.TIME.type,
            dataValue = "",
            isEnabled = true
        ),
        DataField(
            id = 5,
            fieldName = "ATTENDANCE",
            niceFieldName = "Total Attendance",
            dataFieldType = DataFieldType.COUNT.type,
            dataValue = "",
            isEnabled = true
        ),
        DataField(
            id = 6,
            fieldName = "TITHE_PAYERS",
            niceFieldName = "Number of Tithe Payers",
            dataFieldType = DataFieldType.COUNT.type,
            dataValue = "",
            isEnabled = true
        ),
        DataField(
            id = 7,
            fieldName = "COMMUNION",
            niceFieldName = "Was Communion Taken",
            dataFieldType = DataFieldType.BOOLEAN.type,
            dataValue = "",
            isEnabled = true
        ),
        DataField(
            id = 8,
            fieldName = "J-SCHOOL",
            niceFieldName = "Was J-School Taught?",
            dataFieldType = DataFieldType.TRISTATE.type,
            dataValue = "",
            isEnabled = true
        ),
        DataField(
            id = 8,
            fieldName = "PREACHING_NOTES",
            niceFieldName = "NOTES",
            dataFieldType = DataFieldType.LONGSTRING.type,
            dataValue = "",
            isEnabled = true
        )
    )
}