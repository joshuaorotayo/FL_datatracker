package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.domain.model.DataItem

@Preview(showBackground = true)
@Composable
fun FormListRowV2() {
    val dataItem = DataRowState(
        DataItem(
            presetId = 0,
            dataItemId = 0,
            fieldName = "List Row",
            fieldHint = "List Type row example...",
            dataId = 1
        ),
        hasError = false,
        errorMsg = ""
    )
    FormShortTextRowV2(data = dataItem, setDataValue = {})
}

@Composable
fun FormListRowV2(
    data: DataRowState,
    setDataValue: (String) -> Unit,
): String {
    val number = remember { mutableStateOf(1) }
    val listTextField = remember { mutableStateListOf<String>() }
//    var

    Column(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
//            .wrapContentHeight()
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
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .fillMaxWidth(),
                text = data.dataItem.fieldName,
                textAlign = TextAlign.Start,
                color = Color.Gray,
            )
        }
        //Button Data capture
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()

        ) {
            items(number.value) { index ->
                ListItem(
                    changeValue = {
                        listTextField[index] = it
                        setDataValue(listTextField.toString())
                    },
                    addItem = { number.value++ },
                    deleteItem = { number.value-- },
                )
            }
        }
    }
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
    )

    // TODO: return gson string of values from textfield
    return number.value.toString()
}

