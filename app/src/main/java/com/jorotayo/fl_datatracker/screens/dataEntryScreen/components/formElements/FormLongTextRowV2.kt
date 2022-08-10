package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.oldFormELements.ofMaxLength

@Preview(showBackground = true)
@Composable
fun PreviewFormLongTextRowV2() {
    FormLongTextRowV2(
        fieldName = "Data Field for Long Text Example",
        fieldHint = "Data capture long text row example..."
    )
}

@Composable
fun FormLongTextRowV2(
    fieldName: String,
    fieldHint: String?
) {
    //define any local variables
    val maxChar = 250
    val (text, setText) = remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .padding(vertical = 5.dp, horizontal = 16.dp)
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
            text = fieldName,
            textAlign = TextAlign.Start,
            color = Color.Gray,
        )
        // Data Field Name Data Capture
        TextField(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            value = text,
            onValueChange = { newText ->
                setText(newText.ofMaxLength(maxLength = maxChar))
            },
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = MaterialTheme.colors.surface,
                backgroundColor = Color.Transparent,
                textColor = MaterialTheme.colors.onSurface
            ),
            maxLines = 4,
            placeholder = {
                Text(
                    text = if (fieldHint?.isEmpty() == true) fieldHint else "Please enter content for field: $fieldName",
                    color = if (text.text.isBlank()) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface,
                    textAlign = TextAlign.Start
                )
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
}
