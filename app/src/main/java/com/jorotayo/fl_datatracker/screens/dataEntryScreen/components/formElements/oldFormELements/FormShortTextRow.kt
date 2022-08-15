package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.oldFormELements

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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun PreviewFormShortTextRow() {
    FormShortTextRow(rowHint = "Row Hint")
}

@Composable
fun FormShortTextRow(
    rowHint: String?
) {
    val maxChar = 50
    val (text, setText) = remember { mutableStateOf(TextFieldValue("")) }

    Box(
        modifier = Modifier.wrapContentSize(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(shape = RoundedCornerShape(10.dp))
                .shadow(elevation = 4.dp)
                .background(MaterialTheme.colors.primary),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(0.5f),
                text = "TEST",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onPrimary
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .wrapContentHeight()
                    .background(MaterialTheme.colors.onPrimary),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TextField(
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .background(MaterialTheme.colors.onPrimary)
                        .padding(0.dp),
                    value = text, onValueChange = {
                        //if (text.text.length <= maxChar) text = it
                            newText ->
                        setText(newText.ofMaxLength(maxLength = maxChar))
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        backgroundColor = MaterialTheme.colors.onPrimary,
                        textColor = Color.Black
                    ),
                    placeholder = {
                        Text(
                            text = rowHint ?: "placeholder text",
                            color = if (text.text.isBlank()) MaterialTheme.colors.primary else Color.Black,
                            textAlign = TextAlign.Center
                        )
                    }
                )
                /* //Max Chars count

                  Text(
                      text = "${text.text.length} / $maxChar",
                      textAlign = TextAlign.End,
                      style = MaterialTheme.typography.caption,
                      color = MaterialTheme.colors.primary,
                      modifier = Modifier
                          .fillMaxWidth()
                          .background(MaterialTheme.colors.onPrimary)
                  )*/
            }
        }
    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
    )
}

fun TextFieldValue.ofMaxLength(maxLength: Int): TextFieldValue {
    val overLength = text.length - maxLength
    return if (overLength > 0) {
        val headIndex = selection.end - overLength
        val trailIndex = selection.end
        // Under normal conditions, headIndex >= 0
        if (headIndex >= 0) {
            copy(
                text = text.substring(0, headIndex) + text.substring(trailIndex, text.length),
                selection = TextRange(headIndex)
            )
        } else {
            // exceptional
            copy(text.take(maxLength), selection = TextRange(maxLength))
        }
    } else {
        this
    }
}