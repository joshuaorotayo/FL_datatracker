package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.domain.model.DataItem
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.ui.theme.bodyTextColour
import com.jorotayo.fl_datatracker.ui.theme.subtitleTextColour
import com.jorotayo.fl_datatracker.util.Dimen.small
import com.jorotayo.fl_datatracker.util.ofMaxLength

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "Light Mode"
)
@Composable
fun PreviewFormLongTextRowV2() {
    FL_DatatrackerTheme {
        val data = DataItem(
            dataItemId = 0,
            presetId = 0,
            fieldName = "Data Field for Long Text Example",
            fieldDescription = "Data capture long text row example...",
            dataId = 1
        )
        formLongTextRowV2(
            data = data,
            setDataValue = {}
        )
    }
}

@Composable
fun formLongTextRowV2(
    data: DataItem,
    setDataValue: (String) -> Unit,
): String {
    // define any local variables
    val maxChar = 200
    val (text, setText) = remember { mutableStateOf(TextFieldValue(data.dataValue)) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(small)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = data.fieldName,
            textAlign = TextAlign.Start,
            color = MaterialTheme.colors.subtitleTextColour,
        )
        // Data Field Name Data Capture

        val lineHeight = MaterialTheme.typography.body1.fontSize * 4 / 3
        TextField(
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = String.format(
                        stringResource(id = R.string.edit_long_text),
                        data.fieldName
                    ),
                    tint = MaterialTheme.colors.primary
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .sizeIn(
                    maxHeight = with(LocalDensity.current) {
                        (lineHeight * 5).toDp()
                    }
                ),
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
                (if (data.fieldDescription?.isBlank() == true) data.fieldDescription else "Please enter content for field: ${data.fieldName}")?.let {
                    Text(
                        text = it,
                        color = if (text.text.isBlank()) MaterialTheme.colors.bodyTextColour else MaterialTheme.colors.subtitleTextColour,
                        textAlign = TextAlign.Start
                    )
                }
            }
        )
        // Max Chars count
        Text(
            text = "${text.text.length} / $maxChar",
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.subtitleTextColour,
            modifier = Modifier
                .fillMaxWidth()
        )
    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
    )
    return text.text
}
