package com.jorotayo.fl_datatracker.screens.dataFieldsScreenRework.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.ui.theme.isDarkMode
import com.jorotayo.fl_datatracker.util.Dimen
import com.jorotayo.fl_datatracker.util.Dimen.xSmall

@DefaultPreviews
@Composable
fun PreviewMinimalCountField() {
    FL_DatatrackerTheme {
        minimalCountField(
            rowHeader = "Minimal Count Datafield",
            isError = false
        )
    }
}

@Composable
fun minimalCountField(
    rowHeader: String,
    isError: Boolean
): String {
    val count = remember { mutableIntStateOf(0) }
    val unChanged = remember { mutableStateOf(true) }

    var cardElevation by remember { mutableStateOf(xSmall) }
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(Dimen.xxSmall)
            .onFocusChanged {
                cardElevation = if (it.isFocused) Dimen.medium else xSmall
            },
        shape = RoundedCornerShape(xSmall),
        backgroundColor = if (!isDarkMode() && (cardElevation == Dimen.medium)) colors.surface.copy(
            alpha = 0.5f
        ) else colors.surface,
        elevation = if (isDarkMode()) cardElevation else Dimen.zero
    ) {
        Column(
            modifier = Modifier
                .padding(Dimen.xxSmall)
                .fillMaxWidth()
        ) {

            Text(
                text = rowHeader,
                style = MaterialTheme.typography.body1,
                color = Color.DarkGray
            )

            AnimatedVisibility(visible = isError && unChanged.value) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .padding(
                                start = Dimen.small,
                                end = Dimen.small,
                                top = 5.dp
                            ),
                        text = stringResource(id = R.string.count_row_error),
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.caption,
                        color = Color.Red,
                    )
                    Icon(
                        modifier = Modifier
                            .padding(end = 10.dp),
                        imageVector = Icons.Default.Warning,
                        contentDescription = stringResource(id = R.string.row_error_description),
                        tint = colors.primary
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.5f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        modifier = Modifier
                            .size(28.dp)
                            .weight(1f)
                            .background(colors.primary)
                            .padding(vertical = 5.dp),
                        onClick = {
                            if ((count.intValue - 1) >= 0) {
                                count.intValue = count.intValue - 1
                                unChanged.value = false
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = stringResource(id = R.string.decrement_description),
                            tint = colors.onPrimary
                        )
                    }
                    TextField(
                        modifier = Modifier
                            .weight(3f),
                        value = count.intValue.toString(),
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        placeholder = {
                            Text(
                                text = if (count.intValue == 0) "0" else count.intValue.toString(),
                                style = MaterialTheme.typography.h6,
                                color = colors.primary
                            )
                        },
                        onValueChange = {
                            if (it.toIntOrNull() == null || it.isBlank()) {
                                count.intValue = 0
                            } else {
                                count.intValue = it.toInt()
                            }
                            unChanged.value = false
                        },
                        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
                            .also { MaterialTheme.typography.subtitle1 },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            textColor = if (count.intValue < 1) Color.DarkGray else colors.onSurface
                        )
                    )
                    IconButton(
                        modifier = Modifier
                            .size(28.dp)
                            .weight(1f)
                            .background(colors.primary)
                            .padding(vertical = 5.dp),
                        onClick = {
                            count.intValue = count.intValue + 1
                            unChanged.value = false
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(id = R.string.increment_description),
                            tint = colors.onPrimary
                        )
                    }
                }
            }
        }
    }
    return count.intValue.toString()
}
