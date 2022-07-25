package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onPrimary
        )
        OutlinedTextField(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .shadow(elevation = 10.dp)
                .background(MaterialTheme.colors.surface),
            value = "Add Data Field Name",
            onValueChange = { },
            colors = TextFieldDefaults.textFieldColors(
                unfocusedLabelColor = MaterialTheme.colors.primary,
                unfocusedIndicatorColor = Color.Transparent,
                focusedLabelColor = MaterialTheme.colors.primary,
                textColor = Color.Black
            )

        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Data Field Name",
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onPrimary
        )

    }
}