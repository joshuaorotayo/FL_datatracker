package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements.oldFormELements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun PreviewFormCountRowV2() {
    FormCountRowV2()
}

@Composable
fun FormCountRowV2() {
    var count = remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(shape = RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.surface)
    ) {
        Row(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 10.dp)
                    .fillMaxWidth(),
                text = "Data Field for Numbers Text",
                textAlign = TextAlign.Start,
                color = Color.Gray,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
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

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
    )
}