package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.domain.model.DataItem
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme

@Preview(showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode")
@Preview(showBackground = true, name = "Light Mode")
@Composable
fun PreviewFormListRowV3() {
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
        formListRowV3(
            data = dataItem,
            setDataValue = {}
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun formListRowV3(
    data: DataRowState,
    setDataValue: (String) -> Unit,
): String {

    val textColour = if (isSystemInDarkTheme()) Color.DarkGray else MaterialTheme.colors.primary
    val maxChar = 20
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
                    .padding(bottom = 16.dp)
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .weight(12f)
                        .padding(horizontal = 10.dp),
                    value = textFields.value[index].orEmpty(),
                    onValueChange = { newText ->
                        textFields.value[index] = newText
                        setDataValue(getDataMapToString(textFields.value))
                        Log.d("formListRowV2", getDataMapToString(textFields.value))
                    },
                    label = {
                        Text(
                            text = String.format(stringResource(R.string.add_list_item_placeholder),
                                index + 1),
                            style = TextStyle(
                                color = textColour
                            )
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray,
                        focusedBorderColor = MaterialTheme.colors.primary,
                        backgroundColor = Color.Transparent,
                        textColor = MaterialTheme.colors.onSurface
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = String.format(stringResource(id = R.string.list_item_leading_icon),
                                index + 1),
                            tint = textColour
                        )
                    },
                    trailingIcon = {
                        AnimatedVisibility(visible = !textFields.value[index].isNullOrBlank()) {
                            Icon(
                                modifier = Modifier
                                    .clickable {
                                        textFields.value[index] = ""
                                    },
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(R.string.clear_list_item),
                                tint = MaterialTheme.colors.primary
                            )
                        }
                    }
                )
                AnimatedVisibility(visible = index == textFields.value.size - 1) {
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        enabled = textFields.value[index].isNullOrEmpty(),
                        onClick = {
                            textFields.value[index + 1] = ""
                            columnHeight.value += itemHeight
                            number.value++
                            Log.d("formListRowV2", getDataMapToString(textFields.value))
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                    ) {
                        Icon(
                            modifier = Modifier,
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.list_item_add_item)
                        )
                    }
                }
                AnimatedVisibility(visible = !textFields.value[index].isNullOrEmpty() && index != textFields.value.size - 1) {
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        onClick = {
                            columnHeight.value -= itemHeight
                            var newTextField = textFields.value
                            newTextField.remove(index)
                            textFields.value = newTextField
                            //number.value--
                            Log.d("formListRowV2", getDataMapToString(textFields.value))
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                    ) {
                        Icon(
                            modifier = Modifier,
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.delete_list_item)
                        )
                    }
                }
            }
        }

    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
    )

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
