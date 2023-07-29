package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.jorotayo.fl_datatracker.domain.util.DataFieldType
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.DataFieldEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.states.NewDataFieldState
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.util.Dimen.medium
import com.jorotayo.fl_datatracker.util.Dimen.small
import com.jorotayo.fl_datatracker.util.Dimen.xSmall
import com.jorotayo.fl_datatracker.util.Dimen.xxSmall
import com.jorotayo.fl_datatracker.util.TransparentTextField
import com.jorotayo.fl_datatracker.util.ofMaxLength
import com.jorotayo.fl_datatracker.util.returnNewDataField

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Preview(showBackground = true, name = "Light Mode")
@Composable
fun PreviewNewDataField() {
    FL_DatatrackerTheme {
        NewDataField(
            currentPresetId = 0L,
            onDataFieldEvent = {}
        )
    }
}

@Composable
fun NewDataField(
    currentPresetId: Long,
    onDataFieldEvent: (DataFieldEvent) -> Unit
) {
    val optionsMaxChars = 20
    val maxChar = 30
    val maxHintChar = 40

    val items = DataFieldType.values().map { dataFieldType -> dataFieldType.type }
    val icons = DataFieldType.values().map { dataFieldImage -> dataFieldImage.image }


    var _newDataField = remember { mutableStateOf(NewDataFieldState()) }
    val newDataField: State<NewDataFieldState> = _newDataField

    val (nameText, setNameText) = remember { mutableStateOf(TextFieldValue(newDataField.value.fieldName)) }
    val (hintText, setHintText) = remember { mutableStateOf(TextFieldValue(newDataField.value.fieldHint)) }
    val (firstText, setFirstText) = remember { mutableStateOf(TextFieldValue(newDataField.value.firstValue)) }
    val (secondText, setSecondText) = remember { mutableStateOf(TextFieldValue(newDataField.value.secondValue)) }
    val (thirdText, setThirdText) = remember { mutableStateOf(TextFieldValue(newDataField.value.thirdValue)) }
    var expanded by remember { mutableStateOf(false) } // Dropdown Menu


//    rememberCoroutineScope()

    val textColour = if (isSystemInDarkTheme()) Color.DarkGray else MaterialTheme.colors.primary

    val focusManager = LocalFocusManager.current

    Card(
        modifier = Modifier
            .wrapContentSize()
            .padding(xSmall),
        shape = RoundedCornerShape(xSmall),
        elevation = xSmall
    ) {
        Column(
            modifier = Modifier
                .padding(xxSmall)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth(),
                text = "New Data Field",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onSurface,
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth()
                    .padding(top = small),
                text = "Data Field Name",
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.body2,
                color = Color.Gray
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth(),
                text = "This name will be formatted and capitalised on save",
                textAlign = TextAlign.Start,
                color = Color.Gray,
                style = MaterialTheme.typography.caption
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = nameText,
                onValueChange = {
                    setNameText(it)
                    _newDataField.value.fieldName = it.text
                },
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = MaterialTheme.colors.surface,
                    backgroundColor = Color.Transparent,
                    textColor = MaterialTheme.colors.onSurface
                ),
                placeholder = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = "Add New Data Field Text",
                        color = textColour,
                        textAlign = TextAlign.Center
                    )
                },
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
            )
            //Max Chars count
            Text(
                text = "${newDataField.value.fieldName.length} / $maxChar",
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.caption,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp)
                    .background(Color.Transparent)
            )

            Text(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(top = small)
                    .fillMaxWidth(),
                text = stringResource(R.string.select_data_field_type),
                textAlign = TextAlign.Start,
                color = Color.Gray,
                style = MaterialTheme.typography.body2,
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth(),
                text = stringResource(R.string.select_data_field_type_caption),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.caption,
                color = Color.Gray,
            )

            Box(
                modifier = Modifier
                    .padding(top = xxSmall)
                    .wrapContentSize(Alignment.Center),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = xxSmall),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier
                            .clickable(onClick = { expanded = true })
                            .padding(end = xSmall),
                        text = items[newDataField.value.fieldType],
                        color = textColour,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body2
                    )
                    Icon(
                        modifier = Modifier,
                        imageVector = icons[newDataField.value.fieldType],
                        contentDescription = "Icon for Field Type Dropdown",
                        tint = textColour
                    )
                    Icon(
                        modifier = Modifier
                            .clickable(onClick = { expanded = true }),
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop down arrow for Field Type Dropdown",
                        tint = textColour
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .wrapContentWidth()
                        .background(MaterialTheme.colors.background),
                ) {
                    items.forEachIndexed { index, s ->
                        DropdownMenuItem(onClick = {
                            newDataField.value.fieldType = index
                            expanded = false
                        }) {
                            Text(
                                modifier = Modifier.weight(3f),
                                text = s,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colors.onSurface
                            )
                            Icon(
                                modifier = Modifier
                                    .padding(end = 10.dp)
                                    .weight(1f),
                                imageVector = icons[index],
                                contentDescription = String.format(
                                    stringResource(
                                        id = R.string.dropdown_icon_description,
                                        items[index]
                                    )
                                ),
                                tint = MaterialTheme.colors.primary
                            )
                        }
                    }
                }
            }

            AnimatedVisibility(visible = newDataField.value.fieldType <= 1 || newDataField.value.fieldType >= 7) { // ShortString = int 0, LongString = int 1
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = small)
                            .padding(horizontal = 10.dp),
                        text = "Data Field Hint",
                        textAlign = TextAlign.Start,
                        color = Color.Gray,
                        style = MaterialTheme.typography.body2,
                    )
                    //Button Data capture
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        textStyle = MaterialTheme.typography.body2,
                        value = hintText,
                        onValueChange = { hintText ->
                            setHintText(hintText.ofMaxLength(maxLength = maxHintChar))
                            _newDataField.value.fieldHint = hintText.text
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = MaterialTheme.colors.surface,
                            backgroundColor = Color.Transparent,
                            textColor = MaterialTheme.colors.onSurface
                        ),
                        singleLine = true,
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {
                            focusManager.clearFocus()
//                            keyboardController?.hide()
//                            onClick(NewData)
                        }),
                        placeholder = {
                            Text(
                                text = "Please enter hint text for " + DataFieldType.values()[newDataField.value.fieldType] + " Field",
                                color = textColour,
                                textAlign = TextAlign.Start
                            )
                        })
                    //Max Chars count
                    Text(
                        text = "${hintText.text.length} / $maxHintChar",
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.caption,
                        color = Color.Gray,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 10.dp)
                            .background(Color.Transparent)
                    )
                }
            }
            AnimatedVisibility(visible = newDataField.value.fieldType == 2) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp, start = 5.dp, end = 5.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = xxSmall, bottom = 5.dp)
                    ) {
                        Text(
                            text = "Enter in the values for the boolean e.g. Yes and No",
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.caption,
                            color = Color.Gray
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TransparentTextField(
                            modifier = Modifier.weight(1f),
                            text = firstText.text,
                            label = "1st Value",
                            placeholder = firstText.text.ifBlank { "1st Value" },
                            onValueChange = { firstText ->
                                setSecondText(TextFieldValue(firstText).ofMaxLength(maxLength = optionsMaxChars))
                                _newDataField.value.firstValue = firstText
                            }
                        )
                        TransparentTextField(modifier = Modifier.weight(1f),
                            text = secondText.text,
                            label = "2nd Value",
                            placeholder = secondText.text.ifBlank { "2nd Value" },
                            onValueChange = { secondText ->
                                setSecondText(TextFieldValue(secondText).ofMaxLength(maxLength = optionsMaxChars))
                                _newDataField.value.secondValue = secondText
                            }
                        )
                    }
                }
            }

            AnimatedVisibility(visible = newDataField.value.fieldType == 6) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp, start = 5.dp, end = 5.dp)
                )
                {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = xxSmall, bottom = 5.dp)
                    ) {
                        Text(
                            text = "Enter in the values for the Tri-state e.g. No, N/A and Yes",
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.caption,
                            color = Color.Gray
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TransparentTextField(
                            modifier = Modifier.weight(1f),
                            text = firstText.text,
                            label = "1st Value",
                            placeholder = firstText.text.ifBlank { "1st Value" },
                            onValueChange = { firstText ->
                                setFirstText(TextFieldValue(firstText).ofMaxLength(maxLength = optionsMaxChars))
                                _newDataField.value.thirdValue = firstText
                            }
                        )
                        TransparentTextField(
                            modifier = Modifier.weight(1f),
                            text = secondText.text,
                            label = "2nd Value",
                            placeholder = secondText.text.ifBlank { "2nd Value" },
                            onValueChange = { secondText ->
                                setSecondText(TextFieldValue(secondText).ofMaxLength(maxLength = optionsMaxChars))
                                _newDataField.value.thirdValue = secondText
                            }
                        )
                        TransparentTextField(
                            modifier = Modifier.weight(1f),
                            text = thirdText.text,
                            label = "3rd Value",
                            placeholder = thirdText.text.ifBlank { "3rd Text" },
                            onValueChange = { thirdText ->
                                setThirdText(TextFieldValue(thirdText).ofMaxLength(maxLength = optionsMaxChars))
                                _newDataField.value.thirdValue = thirdText
                            }
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = small),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier
                        .padding(small)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(medium)),
                    onClick = {
                        _newDataField.value = newDataField.value.copy(presetId = currentPresetId)
                        val dataField = returnNewDataField(newDataField.value)
                        onDataFieldEvent(DataFieldEvent.SaveDataField(dataField))
                        _newDataField = mutableStateOf(NewDataFieldState())

                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                ) {
                    Text(
                        modifier = Modifier,
                        text = "Save Data Field",
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }
}
