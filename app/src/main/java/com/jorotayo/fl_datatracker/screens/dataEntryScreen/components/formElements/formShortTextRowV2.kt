package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.domain.model.DataItem

@Preview(showBackground = true)
@Composable
fun PreviewFormShortTextRowV2() {
    val dataItem = DataRowState(
        DataItem(
            presetId = 0,
            dataItemId = 0,
            fieldName = "Short Text Row",
            fieldDescription = "Short Text row example...",
            dataId = 1
        ),
        hasError = false,
        errorMsg = ""
    )
    formShortTextRowV2(data = dataItem, setDataValue = {})
}

@Composable
fun formShortTextRowV2(
    data: DataRowState,
    setDataValue: (String) -> Unit,
): String {
    val maxChar = 50
    val (text, setText) = remember { mutableStateOf(TextFieldValue(data.dataItem.dataValue)) }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(shape = RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.surface)
    ) {
        Text(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                .fillMaxWidth(),
            text = data.dataItem.fieldName,
            textAlign = TextAlign.Start,
            color = MaterialTheme.colors.onSurface,
        )

        //TextField Data capture

        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = text,
            singleLine = true,
            onValueChange = { newText ->
                setText(newText.ofMaxLength(maxLength = maxChar))
                setDataValue(text.text)
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                textColor = MaterialTheme.colors.onSurface
            ),
            isError = data.hasError,
            trailingIcon = {
                if (data.hasError) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Error in ${data.dataItem.fieldName} field",
                    )
                }
            },
            placeholder = {
                Text(
                    text = (if (data.hasError) "Please enter a value for text field: ${data.dataItem.fieldName}"
                    else if (data.dataItem.fieldDescription?.isBlank() == true) "Short Text Row Hint..." else data.dataItem.fieldDescription)!!,
                    color = if (data.hasError) Color.Red else MaterialTheme.colors.primary,
                    textAlign = TextAlign.Center
                )
            },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
        )
        //Max Chars count
        Text(
            text = "${text.text.length} / $maxChar",
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.caption,
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp, bottom = 8.dp)
                .background(Color.Transparent)
        )
    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
    )

    return text.text
}