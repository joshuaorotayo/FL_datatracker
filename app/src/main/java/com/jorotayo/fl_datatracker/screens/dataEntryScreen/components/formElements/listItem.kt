package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.util.Dimen

@Preview(showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode")
@Preview(showBackground = true, name = "Light Mode")
@Composable
fun PreviewListItem() {
    FL_DatatrackerTheme {
        listItem(
            changeValue = {},
            addItem = {},
            deleteItem = {},
            clearField = {},
            lastItem = false,
            index = 1,
            itemValue = "test"
        )
    }
}

@Composable
fun listItem(
    itemValue: String,
    changeValue: (String) -> Unit,
    addItem: () -> Unit,
    deleteItem: () -> Unit,
    clearField: () -> Unit,
    lastItem: Boolean,
    index: Int,
): String {

    val textColour = if (isSystemInDarkTheme()) Color.DarkGray else MaterialTheme.colors.primary
    val maxChar = 20
    val (text, setText) = mutableStateOf(itemValue)

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
                        index + 1),
                    style = TextStyle(
                        color = textColour
                    )
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray,
                focusedBorderColor = MaterialTheme.colors.primary,
                backgroundColor = Color.Transparent,
                textColor = MaterialTheme.colors.onSurface
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = String.format(stringResource(id = R.string.list_item_leading_icon),
                        index + 1),
                    tint = textColour
                )
            },
            trailingIcon = {
                AnimatedVisibility(visible = text.isNotBlank()) {
                    Icon(
                        modifier = Modifier
                            .clickable {
                                clearField()
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
                    .padding(end = Dimen.xxSmall),
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
                    .padding(end = Dimen.xxSmall),
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