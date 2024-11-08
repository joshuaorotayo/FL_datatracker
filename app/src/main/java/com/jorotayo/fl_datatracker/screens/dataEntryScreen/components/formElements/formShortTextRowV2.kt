package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.domain.model.DataItem
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.util.ofMaxLength

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Preview(showBackground = true, name = "Light Mode")
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
    FL_DatatrackerTheme {
        formShortTextRowV2(data = dataItem, setDataValue = {})
    }
}

@Composable
fun formShortTextRowV2(
    data: DataRowState,
    setDataValue: (String) -> Unit,
): String {

    val textColour = if (isSystemInDarkTheme()) Color.DarkGray else MaterialTheme.colors.primary
    val maxChar = 50
    val (text, setText) = remember { mutableStateOf(TextFieldValue(data.dataItem.dataValue)) }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colors.surface)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = data.dataItem.fieldName,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colors.onSurface,
            )

            AnimatedVisibility(visible = data.hasError && data.dataItem.dataValue.isBlank()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier,
                        text = stringResource(id = R.string.short_row_error),
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.caption,
                        color = Color.Red,
                    )
                    Icon(
                        modifier = Modifier,
                        imageVector = Icons.Default.Warning,
                        contentDescription = stringResource(id = R.string.row_error_description),
                        tint = MaterialTheme.colors.primary
                    )
                }
            }

            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = String.format(
                            stringResource(id = R.string.edit_short_text),
                            data.dataItem.fieldName
                        ),
                        tint = textColour
                    )
                },
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
                placeholder = {
                    Text(
                        modifier = Modifier.padding(0.dp),
                        text = (if (data.dataItem.fieldDescription?.isBlank() == true) "Short Text Row Hint..." else data.dataItem.fieldDescription)!!,
                        color = textColour,
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
            )
        }
    }
    return text.text
}