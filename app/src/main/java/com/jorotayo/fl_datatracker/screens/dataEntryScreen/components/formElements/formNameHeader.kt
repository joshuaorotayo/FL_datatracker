package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme

@Preview(showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode")
@Preview(showBackground = true, name = "Light Mode")
@Composable
fun PreviewFormNameHeader() {
    FL_DatatrackerTheme {
        formNameHeader(
            setName = { },
            data = DataEntryScreenState(
                dataName = "Test name",
                dataRows = mutableListOf(),
                nameError = false,
                nameErrorMsg = ""
            ),
            formSubmitted = false
        )
    }
}

@Composable
fun formNameHeader(
    setName: (String) -> Unit,
    data: DataEntryScreenState,
    formSubmitted: Boolean,
) {
    val textColour = if (isSystemInDarkTheme()) Color.DarkGray else MaterialTheme.colors.primary
    val focusManager = LocalFocusManager.current
    val nameText = remember { mutableStateOf(TextFieldValue(data.dataName)) }
    val submittedForm by remember { mutableStateOf(formSubmitted) }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(shape = RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.surface)
    ) {

        AnimatedVisibility(visible = data.nameError && (data.dataName.isBlank() && submittedForm)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 0.dp)
                    .clip(shape = RoundedCornerShape(10.dp)),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier,
                    text = data.nameErrorMsg,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.caption,
                    color = Color.Red,
                )
                Icon(
                    modifier = Modifier,
                    imageVector = Icons.Default.Warning,
                    contentDescription = stringResource(id = R.string.row_error_description),
                    tint = Color.Red
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = nameText.value,
                placeholder = {
                    Text(
                        text = "Please enter a meeting or Service Name...",
                        color = textColour,
                        textAlign = TextAlign.Center
                    )
                },
                singleLine = true,
                onValueChange = {
                    nameText.value = it
                    setName(nameText.value.text)
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    textColor = MaterialTheme.colors.onSurface
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
            )
        }
    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
    )
}