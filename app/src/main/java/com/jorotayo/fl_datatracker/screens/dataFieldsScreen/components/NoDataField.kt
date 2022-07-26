package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
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
fun PreviewNoDataField() {
    NoDataField()
}

@Composable
fun NoDataField(
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(vertical = 20.dp, horizontal = 8.dp)
            .clip(shape = RoundedCornerShape(20.dp))
    )
    {

        //empty Message
        Box(
            modifier = Modifier
                .wrapContentSize()
                .padding(10.dp)
                .clip(shape = RoundedCornerShape(10.dp))
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(128.dp),
                    imageVector = Icons.Default.AddBox,
                    tint = MaterialTheme.colors.primary,
                    contentDescription = "No Data Fields Message",
                )
                Text(
                    modifier = Modifier
                        .padding(top = 5.dp),
                    text = "There are currently no Data Fields. Add Data Fields to begin adding Data ",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h5,
                    color = Color.DarkGray
                )
            }
        }
    }
}
