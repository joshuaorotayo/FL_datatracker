package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.ofMaxLength
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme

@Preview(showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode")
@Preview(showBackground = true, showSystemUi = true, name = "Light Mode")
@Composable
fun PreviewBasicAddPresetDialog() {
    FL_DatatrackerTheme {
        val state = remember { mutableStateOf(true) }
        BasicAddPresetDialog(
            state = state,
            modifier = Modifier,
            addPreset = {}
        )
    }
}


@Composable
fun BasicAddPresetDialog(
    state: MutableState<Boolean>,
    modifier: Modifier,
    addPreset: (String) -> Unit,
) {

    val (presetText, setText) = remember { mutableStateOf(TextFieldValue("")) }
    val maxChar = 30

    if (state.value) {
        Card(
            modifier = modifier
                .padding(32.dp)
                .shadow(10.dp, RoundedCornerShape(10.dp))
                .defaultMinSize(minWidth = 280.dp)
                .wrapContentHeight(),
            shape = RoundedCornerShape(28.dp),
            elevation = 8.dp
        ) {
            Column(
                modifier
                    .background(MaterialTheme.colors.surface)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.addPresetHeader),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onSurface
                )
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textStyle = MaterialTheme.typography.h6,
                    value = presetText,
                    maxLines = 1,
                    placeholder = {
                        Text(
                            modifier = Modifier
                                .wrapContentHeight()
                                .padding(0.dp),
                            text = stringResource(R.string.enterPresetPlaceholder),
                            style = MaterialTheme.typography.h6,
                            color = MaterialTheme.colors.onSurface,
                            textAlign = TextAlign.Center
                        )
                    },
                    onValueChange = {

                        setText(it.ofMaxLength(maxLength = maxChar))
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.surface,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        textColor = MaterialTheme.colors.onSurface
                    )
                )
                Text(text = "${presetText.text.length} / $maxChar",
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp, end = 10.dp)
                        .background(Color.Transparent)
                )
                Row(
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {
                        state.value = false
                    }) {
                        Text(
                            modifier = Modifier
                                .padding(end = 8.dp),
                            text = stringResource(R.string.cancelText),
                            color = MaterialTheme.colors.primary,
                        )
                    }
                    TextButton(onClick = {
                        state.value = false
                        addPreset(presetText.text)
                    }) {
                        Text(
                            modifier = Modifier,
                            text = stringResource(R.string.addPresetBtn),
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colors.primary,
                        )
                    }
                }
            }
        }
    }
}