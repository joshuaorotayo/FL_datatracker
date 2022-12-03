package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.R

@Preview
@Composable
fun PreviewListItem() {
    ListItem(
        changeValue = {},
        addItem = {},
        deleteItem = {},
        lastItem = false,
        index = 1
    )
}

@Composable
fun ListItem(
    changeValue: (String) -> Unit,
    addItem: () -> Unit,
    deleteItem: () -> Unit,
    lastItem: Boolean,
    index: Int,
): String {
    val maxChar = 20
    val (text, setText) = remember { mutableStateOf("") }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier
                .weight(12f)
                .padding(horizontal = 10.dp),
            value = text,
            onValueChange = { newText ->
                if (newText.length < maxChar) {
                    setText(newText)
                    changeValue(newText)
                }
            },
            label = {
                Text(
                    text = String.format(stringResource(R.string.add_list_item_placeholder),
                        index + 1)
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = MaterialTheme.colors.onBackground,
                focusedBorderColor = MaterialTheme.colors.primary,
                backgroundColor = MaterialTheme.colors.surface,
                textColor = MaterialTheme.colors.onSurface
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = String.format(stringResource(id = R.string.list_item_leading_icon),
                        index + 1),
                    tint = MaterialTheme.colors.primary
                )
            },
            trailingIcon = {
                AnimatedVisibility(visible = text.isNotBlank()) {
                    Icon(
                        modifier = Modifier
                            .clickable {
                                setText("")
                                changeValue("")
                            },
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.clear_list_item),
                        tint = MaterialTheme.colors.primary
                    )
                }
            }
        )
        AnimatedVisibility(visible = lastItem) {
            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                enabled = text.isNotBlank(),
                onClick = { addItem() },
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
            ) {
                Icon(
                    modifier = Modifier,
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.list_item_add_item)
                )
            }
        }
        AnimatedVisibility(visible = text.isNotBlank() && !lastItem) {
            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                onClick = { deleteItem() },
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
            ) {
                Icon(
                    modifier = Modifier,
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.delete_list_item)
                )
            }
        }
    }
    return text
}