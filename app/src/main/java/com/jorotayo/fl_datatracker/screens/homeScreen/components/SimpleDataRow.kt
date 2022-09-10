package com.jorotayo.fl_datatracker.screens.homeScreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.domain.model.Data

@Composable
@Preview
fun PreviewSimpleDataRow() {
    SimpleDataRow(
        modifier = Modifier,
        data = Data(
            dataId = 0,
            createdTime = "Yesterday",
            lastEditedTime = "Today",
            name = "Simple Service Test"
        ),
        editData = ({}),
        deleteData = ({})
    )
}

@Composable
fun SimpleDataRow(
    modifier: Modifier,
    data: Data,
    editData: (Long) -> Unit,
    deleteData: (Data) -> Unit,
) {

    val textColor = MaterialTheme.colors.primary

    Row(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .clip(shape = RoundedCornerShape(10.dp))
        .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f),
            text = data.name,
            color = textColor,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .weight(1f),
            text = data.createdTime,
            color = textColor,
            style = MaterialTheme.typography.caption,
            textAlign = TextAlign.Center
        )
        Icon(
            modifier = Modifier
                .weight(0.25f)
                .clickable(onClick = { editData(data.dataId) }),
            imageVector = Icons.Default.Edit,
            contentDescription = "Edit icon for data ${data.name}",
            tint = textColor
        )
        Icon(
            modifier = Modifier
                .weight(0.25f)
                .clickable(onClick = { deleteData(data) }),
            imageVector = Icons.Default.Close,
            contentDescription = "Delete icon for data ${data.name}",
            tint = textColor
        )
    }
}