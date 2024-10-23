package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.ui.theme.AppTheme
import com.jorotayo.fl_datatracker.ui.theme.AppTheme.dimens
import com.jorotayo.fl_datatracker.util.DefaultPreviews

@DefaultPreviews
@Composable
fun PreviewFormNameHeader() {
    val testData = DataEntryScreenState(
        dataName = "Test name",
        dataRows = mutableListOf(),
        nameError = false,
        nameErrorMsg = "",
        formSubmitted = false
    )

    AppTheme {
        FormNameHeader(
            setName = { },
            data = testData
        )
    }
}

@Composable
fun FormNameHeader(
    setName: (String) -> Unit,
    data: DataEntryScreenState,
) {
    val focusManager = LocalFocusManager.current
    val nameText = remember { mutableStateOf(TextFieldValue(data.dataName)) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                start = dimens.xSmall,
                top = dimens.xSmall,
                end = dimens.xSmall,
                bottom = dimens.xSmall
            )
    ) {
        AnimatedVisibility(visible = data.nameError || (data.dataName.isBlank() && data.formSubmitted)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimens.xSmall,
                        end = dimens.xSmall,
                        top = dimens.xSmall,
                        bottom = 0.dp
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier,
                    text = data.nameErrorMsg,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.h6,
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
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            TextField(
                modifier = Modifier,
                value = nameText.value,
                placeholder = {
                    Text(
                        text = "Please enter a meeting or Service Name...",
                        color = MaterialTheme.colors.onBackground,
                        textAlign = TextAlign.Start
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
                    textColor = MaterialTheme.colors.onBackground,
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
}
