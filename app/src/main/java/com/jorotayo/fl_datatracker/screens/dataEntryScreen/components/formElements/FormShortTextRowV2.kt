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
import androidx.compose.ui.Alignment
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
fun PreviewFormShortTextRowV2() {
    FormShortTextRowV2(rowHint = "Short Text row example...")
}

@Composable
fun FormShortTextRowV2(
    rowHint: String?
): String {
    val maxChar = 50
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
        Row(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 10.dp)
                    .fillMaxWidth(),
                text = "Data Field for Short Text Row",
                textAlign = TextAlign.Start,
                color = Color.Gray,
            )
        }
        //Button Data capture
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
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
                    placeholder = {
                        Text(
                            text = rowHint ?: "placeholder text",
                            color = if (text.text.isBlank()) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface,
                            textAlign = TextAlign.Center
                        )
                    },
                    maxLines = 1
                )
                //Max Chars count
                Text(
                    text = "${text.text.length} / $maxChar",
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.caption,
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                        .background(Color.Transparent)
                )
            }
        }
    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(5.dp)
    )

    return text.text
}