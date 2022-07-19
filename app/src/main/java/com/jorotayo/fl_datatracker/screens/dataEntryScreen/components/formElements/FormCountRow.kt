package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun PreviewFormCountRow() {
    FormCountRow()
}

@Composable
fun FormCountRow() {
    var count = remember { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .wrapContentSize(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .shadow(elevation = 4.dp)
                .background(MaterialTheme.colors.primary),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .fillMaxWidth(0.5f),
                text = "Data Field for Numbers Text",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onPrimary,
            )
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(1f)
                    .shadow(elevation = (4.dp))
                    .background(MaterialTheme.colors.onPrimary),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    modifier = Modifier
                        .size(28.dp)
                        .background(MaterialTheme.colors.primary)
                        .padding(vertical = 5.dp),
                    onClick = {
                        if ((count.value - 1) >= 0) {
                            count.value = count.value - 1
                        }
                    })
                {
                    Icon(
                        imageVector = Icons.Default.Remove,
                        contentDescription = "Decrement Count",
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
                Text(
                    text = "" + count.value,
                    color = if (count.value <= 0) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface,
                    modifier = Modifier
                        .weight(1f, fill = false),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h6

                )
                IconButton(
                    modifier = Modifier
                        .size(28.dp)
                        .background(MaterialTheme.colors.primary)
                        .padding(vertical = 5.dp),
                    onClick = {
                        count.value = count.value + 1
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Increment Count",
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }
    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
    )
}