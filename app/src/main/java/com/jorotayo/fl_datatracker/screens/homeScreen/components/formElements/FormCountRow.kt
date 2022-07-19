package com.jorotayo.fl_datatracker.screens.homeScreen.components.formElements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun PreviewFormCountRow() {
    FormCountRow()
}

@Composable
fun FormCountRow() {

    var text by remember { mutableStateOf("3467") }

    Box(
        modifier = Modifier.fillMaxWidth(),
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
                    .fillMaxWidth(0.5f),
                text = "Data Field for Numbers Text",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onPrimary
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp, vertical = 2.dp)
                    .background(MaterialTheme.colors.onPrimary),
                value = text,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                onValueChange = {
                    text = it
                },
                label = {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 2.dp),
                        text = "12345",
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        style = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
                    )
                },
                leadingIcon = {
                    IconButton(
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .size(28.dp)
                            .background(MaterialTheme.colors.primary),
                        onClick = {
                            // TODO:
                        })
                    {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Increment Count",
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                },
                trailingIcon = {
                    IconButton(
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .size(28.dp)
                            .background(MaterialTheme.colors.primary),
                        onClick = {
                            // TODO:
                        }) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "Decrement Count",
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                }
            )
            /*     Box(
                     modifier = Modifier
                         .background(MaterialTheme.colors.onPrimary)
                         .fillMaxWidth(0.5f),

                 ) {

                 }*/
        }
    }
}