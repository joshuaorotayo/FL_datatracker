package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.domain.model.DataItem

@Preview(showBackground = true)
@Composable
fun PreviewFormLongTextRowV2() {
    val data = DataItem(
        dataItemId = 0,
        presetId = 0,
        fieldName = "Data Field for Long Text Example",
        fieldHint = "Data capture long text row example...",
        dataId = 1
    )
    FormLongTextRowV2(
        data = data,
        setDataValue = {}
    )
}

@Composable
fun FormLongTextRowV2(
    data: DataItem,
    setDataValue: (String) -> Unit,
): String {
    //define any local variables
    val maxChar = 200
    val (text, setText) = remember { mutableStateOf(TextFieldValue(data.dataValue)) }

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
            text = data.fieldName,
            textAlign = TextAlign.Start,
            color = MaterialTheme.colors.onSurface,
        )
        // Data Field Name Data Capture

        val lineHeight = MaterialTheme.typography.body1.fontSize * 4 / 3
        TextField(
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = String.format(stringResource(id = R.string.edit_long_text),
                        data.fieldName),
                    tint = MaterialTheme.colors.primary
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .sizeIn(maxHeight = with(LocalDensity.current) {
                    (lineHeight * 5).toDp()
                }),
            value = text,
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
            maxLines = 4,
            placeholder = {
                (if (data.fieldHint?.isBlank() == true) data.fieldHint else "Please enter content for field: ${data.fieldName}")?.let {
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

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
    )

    return text.text
}
