package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jorotayo.fl_datatracker.R

@Preview
@Composable
fun PreviewListItem() {
    ListItem(
//        listModifier = Modifier.fillMaxWidth(),
        changeValue = {},
        addItem = {},
        deleteItem = {}
    )
}

@Composable
fun ListItem(
    changeValue: (String) -> Unit,
    addItem: () -> Unit,
    deleteItem: () -> Unit,
) {
    val maxChar = 20
    val (text, setText) = remember { mutableStateOf("") }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier
                .weight(7f),
            value = text,
            onValueChange = { newText ->
//                setText(newText.ofMaxLength(maxLength = maxChar))
                if (newText.length < maxChar) {
                    setText(newText)
                    changeValue(newText)
                }
            },
            label = { Text(text = stringResource(R.string.add_list_item_placeholder)) },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.background,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                textColor = MaterialTheme.colors.onSurface
            ),
        )

        Button(
            enabled = text.isNotBlank(),
            onClick = { addItem() },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.list_item_add_item)
            )
        }
        AnimatedVisibility(visible = text.isNotBlank()) {
            Button(
                onClick = ({ deleteItem() }),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.delete_list_item)
                )

            }
        }
    }
}