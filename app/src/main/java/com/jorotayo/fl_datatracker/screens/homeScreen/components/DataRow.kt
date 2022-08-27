package com.jorotayo.fl_datatracker.screens.homeScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Group
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jorotayo.fl_datatracker.domain.model.Data

@Composable
@Preview
fun PreviewDataRow() {
    DataRow(
        data = hiltViewModel(),
        editData = {}
    )
}

@Composable
fun DataRow(
    data: Data,
    editData: () -> Unit,
) {

    val textColor = if (isSystemInDarkTheme()) Color.Gray else Color.Black

    Row(modifier = Modifier
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .fillMaxWidth()
        .clip(shape = RoundedCornerShape(10.dp))
        .background(MaterialTheme.colors.surface)
        .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Icon(
            modifier = Modifier.weight(1f),
            imageVector = Icons.Default.Group,
            contentDescription = "Row icon for data ${data.name}",
            tint = MaterialTheme.colors.primary
        )
        Text(
            modifier = Modifier.weight(6f),
            text = data.name,
            color = textColor,
            style = MaterialTheme.typography.h6,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.weight(2f),
            text = data.lastEdited,
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.subtitle1,
            overflow = TextOverflow.Ellipsis
        )
        Icon(
            modifier = Modifier
                .weight(1f)
                .clickable(onClick = editData),
            imageVector = Icons.Default.Edit,
            contentDescription = "Edit icon for data ${data.name}",
            tint = MaterialTheme.colors.primary
        )
    }

}