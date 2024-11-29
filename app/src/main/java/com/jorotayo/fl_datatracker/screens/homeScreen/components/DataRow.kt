package com.jorotayo.fl_datatracker.screens.homeScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Group
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.domain.model.Data
import com.jorotayo.fl_datatracker.ui.DefaultDualPreview
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.util.Dimen
import com.jorotayo.fl_datatracker.util.Dimen.xSmall

@DefaultDualPreview
@Composable
fun PreviewDataRow() {
    FL_DatatrackerTheme {
        DataRow(
            data = Data(
                dataId = 0,
                dataPresetId = 0,
                name = "hello",
                lastEditedTime = "2023-06-01",
                createdTime = "2023-01-01"
            ),
            editData = {}
        )
    }
}

@Composable
fun DataRow(
    data: Data,
    editData: () -> Unit,
) {
    Card(
        modifier = Modifier.run {
            padding(horizontal = Dimen.small, vertical = Dimen.xxSmall)
                .fillMaxWidth()
                .background(colors.surface)
        },
        shape = RoundedCornerShape(xSmall),
        elevation = Dimen.small,
    ) {
        Row(
            modifier = Modifier
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.weight(1f),
                imageVector = Icons.Default.Group,
                contentDescription = "Row icon for data ${data.name}",
                tint = colors.primary
            )
            Text(
                modifier = Modifier.weight(5f),
                text = data.name,
                color = colors.onBackground,
                style = MaterialTheme.typography.subtitle1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(3f),
                text = data.lastEditedTime,
                color = colors.onBackground,
                style = MaterialTheme.typography.subtitle1,
                overflow = TextOverflow.Ellipsis
            )
            Icon(
                modifier = Modifier
                    .weight(1f)
                    .clickable(onClick = editData),
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit icon for data ${data.name}",
                tint = colors.primary
            )
        }
    }
}
