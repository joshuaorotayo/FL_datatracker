package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import com.jorotayo.fl_datatracker.domain.model.DataItem
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme

@Preview(showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode")
@Preview(showBackground = true, name = "Light Mode")
@Composable
fun PreviewFormListRowV4() {
    FL_DatatrackerTheme {
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
        rememberSaveable { mutableStateOf(getDataStringToMap(data.dataItem.dataValue)) }
    val number = rememberSaveable { mutableStateOf(textFields.value.size) }
    val itemHeight = 65f
    val columnHeight = rememberSaveable { mutableStateOf(70F + (itemHeight * number.value)) }

    LazyColumn(modifier = Modifier
        .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
        .fillMaxWidth()
        .height(Dp(columnHeight.value))
        .clip(shape = RoundedCornerShape(10.dp))
        .background(MaterialTheme.colors.surface)) {
        item {
            Row(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp)
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
                    .padding(bottom = 10.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 0.dp)
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
                    Log.d("formListRowV2", getDataMapToString(textFields.value))
                },
                deleteItem = {
                    textFields.value.remove(index)
                    columnHeight.value -= itemHeight
                    number.value--
                    Log.d("formListRowV2", getDataMapToString(textFields.value))

                },
                lastItem = index == number.value - 1,
                index = index
            )
        }

    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
    )

    // TODO: return gson string of values from textfield
    return getDataMapToString(textFields.value)
}

private fun getDataStringToMap(textsFieldsString: String): LinkedHashMap<Int, String> {
    val gson = Gson()
    return if (textsFieldsString.isBlank()) {
        linkedMapOf<Int, String>(0 to "")
    } else {
        val mapType = LinkedHashMap<Int, String>().javaClass
        gson.fromJson(textsFieldsString, mapType)
    }
}

private fun getDataMapToString(textFieldsMap: LinkedHashMap<Int, String>): String {
    val gson = Gson()
    val newMap = linkedMapOf<Int, String>()
    for (value in textFieldsMap) {
        if (value.value.isNotBlank()) {
            newMap[value.key] = value.value.trim()
        }
    }
    return gson.toJson(newMap)
}


