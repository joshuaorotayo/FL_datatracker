package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.google.gson.Gson
import com.jorotayo.fl_datatracker.domain.model.DataItem
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.theme.AppTheme
import com.jorotayo.fl_datatracker.ui.theme.AppTheme.dimens

@DefaultPreviews
@Composable
fun PreviewFormListRowV4() {
    AppTheme {
        val dataItem = DataRowState(
            DataItem(
                presetId = 0,
                dataItemId = 0,
                fieldName = "List Row",
                fieldDescription = "List Type row example...",
                dataId = 1
            ),
            hasError = false,
            errorMsg = ""
        )
        formListRowV4(
            data = dataItem,
            setDataValue = {}
        )
    }
}

@Composable
fun formListRowV4(
    data: DataRowState,
    setDataValue: (String) -> Unit,
): String {
    val textFields =
        rememberSaveable { mutableStateOf(getDataStringToList(data.dataItem.dataValue)) }

    val number = rememberSaveable { mutableStateOf(textFields.value.size) }
    val itemHeight = 65f
    val columnHeight = rememberSaveable { mutableStateOf(75F + (itemHeight * number.value)) }


    LazyColumn(
        modifier = Modifier
            .padding(dimens.xSmall)
            .fillMaxWidth()
            .height(Dp(columnHeight.value))
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = data.dataItem.fieldName,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colors.onSurface,
                )
            }
        }
        item {
            Row(
                modifier = Modifier
                    .padding(bottom = dimens.xSmall)
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Empty values will be removed on save",
                    textAlign = TextAlign.Start,
                    color = Color.DarkGray,
                    style = MaterialTheme.typography.caption
                )
            }
        }
        items(number.value) { index ->
            listItem(
                modifier = Modifier,
                itemValue = textFields.value[index].orEmpty(),
                changeValue = { newText ->
                    textFields.value[index] = newText
                    setDataValue(getDataMapToString(textFields.value))
                    Log.d("formListRowV2", getDataMapToString(textFields.value))
                },
                addItem = {
                    textFields.value[index + 1] = ""
                    columnHeight.value += itemHeight
                    number.value++
                },
                deleteItem = {
                    textFields.value.remove(index)
                    columnHeight.value -= itemHeight
                    number.value--
                    textFields.value = deleteValueAtIndex(textFields.value, index)
                },
                clearField = {
                    textFields.value[index] = ""
                    if (index != textFields.value.size - 1) {
                        if (textFields.value[textFields.value.size - 1].isNullOrBlank()) {
                            columnHeight.value -= itemHeight
                            number.value--
                            textFields.value =
                                deleteValueAtIndex(textFields.value, textFields.value.size)
                        }
                    }
                    setDataValue(getDataMapToString(textFields.value))
                },
                lastItem = index == number.value - 1,
                index = index
            )
        }
    }
    return getDataMapToString(textFields.value)
}

fun deleteValueAtIndex(textfield: HashMap<Int, String>, listPos: Int): HashMap<Int, String> {
    var newTextField = hashMapOf<Int, String>()
    for (field in textfield) {
        if (field.key < listPos) {
            newTextField[field.key] = field.value
        } else {
            newTextField[field.key - 1] = field.value
        }
    }
    return newTextField
}

private fun getDataMapToString(textFieldsMap: HashMap<Int, String>): String {
    val gson = Gson()
    val newMap = hashMapOf<Int, String>()
    for (value in textFieldsMap) {
        if (value.value.isNotBlank()) {
            newMap[value.key] = value.value
        }
    }
    return gson.toJson(newMap)
}

private fun getDataStringToList(textsFieldsString: String): HashMap<Int, String> {
    val gson = Gson()
    return if (textsFieldsString.isBlank()) {
        hashMapOf<Int, String>(0 to "")
    } else {
        val mapType = HashMap<Int, String>().javaClass
        gson.fromJson(textsFieldsString, mapType)
    }
}
