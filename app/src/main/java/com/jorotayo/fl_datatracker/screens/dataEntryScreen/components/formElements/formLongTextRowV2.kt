package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.domain.model.DataItem

@Preview(showBackground = true)
@Composable
fun PreviewFormLongTextRowV2() {
    val data = DataItem(
        dataItemId = 0,
        presetId = 0,
        fieldName = "Data Field for Long Text Example",
        fieldHint = "Data capture long text row example..."
    )
    formLongTextRowV2(
        data = data
    )
}

@Composable
fun formLongTextRowV2(
    data: DataItem,
): String {
    //define any local variables
    val maxChar = 200
    val (text, setText) = remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(shape = RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.surface)
            .padding(10.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 5.dp, horizontal = 10.dp)
                .fillMaxWidth(),
            text = data.fieldName,
            textAlign = TextAlign.Start,
            color = Color.Gray,
        )
        // Data Field Name Data Capture

        val lineHeight = MaterialTheme.typography.body1.fontSize * 4 / 3
        TextField(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .fillMaxWidth()
                .sizeIn(minHeight = with(LocalDensity.current) {
                    (lineHeight * 4).toDp()
                }),
            value = text,
            onValueChange = { newText ->
                setText(newText.ofMaxLength(maxLength = maxChar))
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                textColor = MaterialTheme.colors.onSurface
            ),
            maxLines = 4,
            placeholder = {
                (if (data.fieldHint?.isEmpty() == true) data.fieldHint else "Please enter content for field: ${data.fieldName}")?.let {
                    Text(
                        text = it,
                        color = if (text.text.isBlank()) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface,
                        textAlign = TextAlign.Start
                    )
                }
            }
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

    return text.text
}
