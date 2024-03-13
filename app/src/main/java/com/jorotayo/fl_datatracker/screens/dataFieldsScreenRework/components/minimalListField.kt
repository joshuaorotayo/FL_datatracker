package com.jorotayo.fl_datatracker.screens.dataFieldsScreenRework.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.google.gson.Gson
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.deleteValueAtIndex
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.listItem
import com.jorotayo.fl_datatracker.ui.DefaultDualPreview
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.ui.theme.isDarkMode
import com.jorotayo.fl_datatracker.util.Dimen
import com.jorotayo.fl_datatracker.util.Dimen.xSmall
import com.jorotayo.fl_datatracker.util.Dimen.xxxSmall

@DefaultDualPreview
@Composable
fun PreviewFormListRowV4() {
    FL_DatatrackerTheme {
        minimalListField(rowHeader = "Row for list items", isError = false)
    }
}

@Composable
fun minimalListField(
    rowHeader: String,
    isError: Boolean
): String {
    val textFields =
        rememberSaveable { mutableStateOf(getDataStringToList("")) }

    var cardElevation by remember { mutableStateOf(xSmall) }
    val number = rememberSaveable { mutableIntStateOf(textFields.value.size) }
    val itemHeight = 65f
    val columnHeight = rememberSaveable { mutableStateOf(75F + (itemHeight * number.intValue)) }
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
                .padding(Dimen.xxSmall)
                .onFocusChanged {
                    cardElevation = if (it.isFocused) Dimen.medium else xSmall
                },
            shape = RoundedCornerShape(xSmall),
            backgroundColor = if (!isDarkMode() && (cardElevation == Dimen.medium)) MaterialTheme.colors.surface.copy(
                alpha = 0.5f
            ) else MaterialTheme.colors.surface,
            elevation = if (isDarkMode()) cardElevation else Dimen.zero
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
            )
            {
                LazyColumn(
                    modifier = Modifier
                        .padding(xSmall)
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
                                text = rowHeader,
                                textAlign = TextAlign.Start,
                                color = DarkGray,
                            )
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .padding(bottom = xxxSmall)
                                .fillMaxWidth()
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                text = "Empty values will be removed on save",
                                textAlign = TextAlign.Start,
                                color = DarkGray,
                                style = MaterialTheme.typography.caption
                            )
                        }
                    }
                    items(number.intValue) { index ->
                        listItem(
                            modifier = Modifier,
                            itemValue = textFields.value[index].orEmpty(),
                            changeValue = { newText ->
                                textFields.value[index] = newText
//                    setDataValue(getDataMapToString(textFields.value))
                                Log.d("formListRowV2", getDataMapToString(textFields.value))
                            },
                            addItem = {
                                textFields.value[index + 1] = ""
                                columnHeight.value += itemHeight
                                number.intValue++
                                focusManager.moveFocus(FocusDirection.Down)
                            },
                            deleteItem = {
                                if (textFields.value.size > 1) {
                                    textFields.value.remove(index)
                                    columnHeight.value -= itemHeight
                                    number.intValue--
                                    textFields.value = deleteValueAtIndex(textFields.value, index)
                                }
                            },
                            clearField = {
                                textFields.value[index] = ""
                                if (index != textFields.value.size - 1) {
                                    if (textFields.value[textFields.value.size - 1].isNullOrBlank()) {
                                        columnHeight.value -= itemHeight
                                        number.intValue--
                                        textFields.value =
                                            deleteValueAtIndex(
                                                textFields.value,
                                                textFields.value.size
                                            )
                                    }
                                }
//                    setDataValue(getDataMapToString(textFields.value))
                            },
                            lastItem = index == number.intValue - 1,
                            index = index
                        )
                    }
                }
            }
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
