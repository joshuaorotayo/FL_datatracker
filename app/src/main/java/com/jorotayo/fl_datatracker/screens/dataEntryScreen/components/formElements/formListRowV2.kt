package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.domain.model.DataItem
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import kotlinx.coroutines.launch


@Preview(showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode")
@Preview(showBackground = true, name = "Light Mode")
@Composable
fun PreviewFormListRowV2() {
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
        formListRowV2(data = dataItem, setDataValue = {})
    }
}

@Composable
fun formListRowV2(
    data: DataRowState,
    setDataValue: (String) -> Unit,
): String {
    val number = remember { mutableStateOf(1) }
    val textFields = remember { mutableStateMapOf<Int, String>() }
    val columnHeight = remember { mutableStateOf(150F) }
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val itemHeight = 70f

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
                changeValue = {
                    textFields[index] = it
                    textFields.values.filter { string -> string.isNotBlank() }
                },
                addItem = {
                    number.value++
                    columnHeight.value += itemHeight
                    scope.launch {
                        scrollState.animateScrollBy(itemHeight)
                    }
                },
                deleteItem = {
                    number.value--
                    columnHeight.value -= itemHeight
                    scope.launch {
                        scrollState.animateScrollBy(-itemHeight)
                    }
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
    return number.value.toString()
}

