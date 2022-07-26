package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun PreviewNoDataForm() {
    NoDataForm()
}


@Composable
fun NoDataForm() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .clip(shape = RoundedCornerShape(10.dp))
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    modifier = Modifier.size(128.dp),
                    imageVector = Icons.Default.AddBox,
                    tint = MaterialTheme.colors.primary,
                    contentDescription = "No Data Fields Message",
                )
            }

            Text(
                modifier = Modifier,
                text = "Data Form not created",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.onSurface
            )
            Text(
                modifier = Modifier
                    .padding(bottom = 10.dp),
                text = "There are currently no Data Fields. Add some Data Fields to begin adding Data",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h5,
                color = Color.DarkGray
            )
        }
    }
}