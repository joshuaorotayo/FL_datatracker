package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
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
import com.jorotayo.fl_datatracker.ui.theme.headingTextColour
import com.jorotayo.fl_datatracker.util.Dimen

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Preview(showBackground = true, name = "Light Mode")
@Composable
fun PreviewListItem() {
    FL_DatatrackerTheme {
        listItem(
            modifier = Modifier,
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
    modifier: Modifier,
    itemValue: String,
    changeValue: (String) -> Unit,
    addItem: () -> Unit,
    deleteItem: () -> Unit,
    clearField: () -> Unit,
    lastItem: Boolean,
    index: Int,
): String {

    val maxChar = 20
    val (text, setText) = mutableStateOf(itemValue)

    Row(
        modifier = modifier
            .fillMaxWidth(),
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
                    text = String.format(
                        stringResource(R.string.add_list_item_placeholder),
                        index + 1
                    ),
                    style = TextStyle(
                        color = MaterialTheme.colors.headingTextColour
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
                    contentDescription = String.format(
                        stringResource(id = R.string.list_item_leading_icon),
                        index + 1
                    ),
                    tint = MaterialTheme.colors.headingTextColour
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