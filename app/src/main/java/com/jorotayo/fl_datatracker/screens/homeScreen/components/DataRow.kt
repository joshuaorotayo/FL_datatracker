package com.jorotayo.fl_datatracker.screens.homeScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jorotayo.fl_datatracker.domain.model.Data

@Composable
fun PreviewDataRow() {
    DataRow(
        data = hiltViewModel()
    )
}

@Composable
fun DataRow(
    data: Data,
) {
    Row(modifier = Modifier
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .fillMaxWidth()
        .clip(shape = RoundedCornerShape(10.dp))
        .background(MaterialTheme.colors.surface)
        .padding(4.dp)
    ) {
        Icon(
            modifier = Modifier.weight(1f),
            imageVector = Icons.Default.Group,
            contentDescription = "Row icon for data ${data.name}",
            tint = MaterialTheme.colors.primary
        )
        Text(
            modifier = Modifier.weight(7f),
            text = data.name,
            color = Color.Gray,
            style = MaterialTheme.typography.h6,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            modifier = Modifier.weight(7f),
            text = data.lastEdited,
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.subtitle1,
            overflow = TextOverflow.Ellipsis
        )
    }

}