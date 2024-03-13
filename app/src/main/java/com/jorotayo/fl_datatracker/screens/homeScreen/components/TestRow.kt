package com.jorotayo.fl_datatracker.screens.homeScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.util.Dimen.fiftyPercent

@Composable
fun TestRow(
    number: Int,
    deleteRow: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
            .padding(16.dp)
    ) {
        Text(
            modifier = Modifier
                .weight(0.5f),
            text = "Item Number: $number",
            color = Color.Black
        )

        Text(
            modifier = Modifier
                .weight(fiftyPercent)
                .clickable(onClick = deleteRow),
            text = "x",
            color = Color.Black
        )
    }
}
