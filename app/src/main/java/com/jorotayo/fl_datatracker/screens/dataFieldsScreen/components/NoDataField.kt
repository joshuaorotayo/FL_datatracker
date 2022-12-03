package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.R

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
            .wrapContentSize()
            .padding(vertical = 20.dp, horizontal = 8.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(MaterialTheme.colors.onBackground)
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
                    modifier = Modifier
                        .size(128.dp),
                    imageVector = Icons.Default.AddBox,
                    tint = MaterialTheme.colors.primary,
                    contentDescription = stringResource(id = R.string.no_data_fields_msg_icon),
                )
                Text(
                    modifier = Modifier
                        .padding(top = 5.dp),
                    text = stringResource(id = R.string.no_data_fields_header),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onSurface
                )
                Text(
                    modifier = Modifier
                        .padding(top = 5.dp),
                    text = stringResource(id = R.string.no_data_fields_msg),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h5,
                    color = Color.DarkGray
                )
            }
        }
    }
}
