package com.jorotayo.fl_datatracker.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.util.DataFieldType
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.DataFieldEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components.DataFieldScreenState
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components.NewDataFieldState
import io.objectbox.Box
import javax.inject.Inject

class DataFieldsViewModel @Inject constructor() : ViewModel() {
    val dataFieldsBox: Box<DataField> = ObjectBox.get().boxFor(DataField::class.java)


    private val _isAddDataFieldVisible = mutableStateOf(DataFieldScreenState())
    var isAddDataFieldVisible: State<DataFieldScreenState> = _isAddDataFieldVisible

    private val _newDataField = mutableStateOf(NewDataFieldState())
    var newDataField: State<NewDataFieldState> = _newDataField

    fun onEvent(event: DataFieldEvent) {
        when (event) {
            DataFieldEvent.ToggleAddNewDataField -> {
                _isAddDataFieldVisible.value = _isAddDataFieldVisible.value.copy(
                    isAddDataFieldVisible = !isAddDataFieldVisible.value.isAddDataFieldVisible
                )
            }
            is DataFieldEvent.AddFieldName -> {
                _newDataField.value = newDataField.value.copy(
                    fieldName = event.value
                )
            }
            is DataFieldEvent.AddFirstValue -> {
                _newDataField.value = newDataField.value.copy(
                    firstValue = event.value
                )
            }
            is DataFieldEvent.AddSecondValue -> {
                _newDataField.value = newDataField.value.copy(
                    secondValue = event.value
                )
            }
            is DataFieldEvent.AddThirdValue -> {
                _newDataField.value = newDataField.value.copy(
                    thirdValue = event.value
                )
            }
            is DataFieldEvent.SelectFieldType -> {
                _newDataField.value = newDataField.value.copy(
                    fieldType = event.value
                )
            }
            DataFieldEvent.SaveDataField -> TODO()
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