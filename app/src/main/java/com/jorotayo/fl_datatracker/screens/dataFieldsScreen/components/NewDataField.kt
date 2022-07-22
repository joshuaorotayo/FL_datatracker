package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun PreviewNewDataField() {
    NewDataField()
}

@Composable
fun NewDataField() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Data Field Name",
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.onPrimary
        )
        OutlinedTextField(
            value = "Add Data Field Name",
            onValueChange = { },
            colors = TextFieldDefaults.textFieldColors(
                unfocusedLabelColor = MaterialTheme.colors.primary,
                unfocusedIndicatorColor = Color.Transparent,
                focusedLabelColor = MaterialTheme.colors.primary,
                textColor = Color.Black
            )

        )
        Text(
            text = "Data Field Name",
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.onPrimary
        )

    }
}