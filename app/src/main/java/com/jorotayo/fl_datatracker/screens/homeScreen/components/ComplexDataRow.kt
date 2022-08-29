package com.jorotayo.fl_datatracker.screens.homeScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.domain.model.Data

@Composable
@Preview

fun PreviewComplexDataRow() {
    ComplexDataRow(
        data = hiltViewModel(),
        editData = { }
    )
}

@Composable
fun ComplexDataRow(
    data: Data,
    editData: () -> Unit,
) {
    val textColor = if (isSystemInDarkTheme()) Color.Gray else Color.DarkGray

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp, vertical = 8.dp)
        .clip(shape = RoundedCornerShape(10.dp))
        .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Column(modifier = Modifier
            .weight(1f)) {
            Icon(
                modifier = Modifier,
                imageVector = Icons.Default.Group,
                contentDescription = "Row icon for data ${data.name}",
                tint = MaterialTheme.colors.primary
            )
        }
        Column(modifier = Modifier
            .weight(7f)) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = data.name,
                color = textColor,
                style = MaterialTheme.typography.h6,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 5.dp),
//                        text = if(data.dataFields.isEmpty()) "" else data.dataFields[0].fieldName + ": ${data.dataFields[0].dataValue}",
                    text = "field one",
                    color = textColor,
                    style = MaterialTheme.typography.subtitle1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier
                        .padding(horizontal = 5.dp),
                    text = if (data.dataFields.size > 1) "data 1" else "", // TODO: correct  - data.dataFields[1].fieldName + ": ${data.dataFields[1].dataValue}"
                    color = textColor,
                    style = MaterialTheme.typography.subtitle1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(id = R.string.dataRow_lastEdited) + data.lastEdited,
                color = textColor,
                style = MaterialTheme.typography.overline,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }
        Icon(
            modifier = Modifier
                .clickable(onClick = editData)
                .weight(1f),
            imageVector = Icons.Default.Edit,
            contentDescription = "Edit icon for data ${data.name}",
            tint = MaterialTheme.colors.primary
        )
        Icon(
            modifier = Modifier
                .weight(1f),
            imageVector = Icons.Default.Share,
            contentDescription = "Share icon for data ${data.name}",
            tint = MaterialTheme.colors.primary
        )
    }

    Divider(modifier = Modifier
        .padding(horizontal = 20.dp)
        .fillMaxWidth()
        .height(1.dp)
        .background(Color.Gray.copy(0.1f))
    )
}