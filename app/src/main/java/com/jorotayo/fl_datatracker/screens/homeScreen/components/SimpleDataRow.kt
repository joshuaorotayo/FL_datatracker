package com.jorotayo.fl_datatracker.screens.homeScreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.SpaceEvenly
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.jorotayo.fl_datatracker.domain.model.Data
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.ui.theme.subtitleTextColour
import com.jorotayo.fl_datatracker.util.Dimen.small
import com.jorotayo.fl_datatracker.util.Dimen.xSmall
import com.jorotayo.fl_datatracker.util.Dimen.xxSmall

@DefaultPreviews
@Composable
fun PreviewSimpleDataRow() {
    FL_DatatrackerTheme {
        SimpleDataRow(
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
}

@Composable
fun SimpleDataRow(
    data: Data,
    editData: () -> Unit,
    deleteData: (Data) -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(xSmall)
            .fillMaxWidth(),
        horizontalArrangement = SpaceEvenly,
        verticalAlignment = CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(2f),
            text = data.name,
            color = colors.subtitleTextColour,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            modifier = Modifier
                .weight(1.5f),
            text = data.createdTime,
            color = colors.subtitleTextColour,
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis
        )
        Icon(
            modifier = Modifier
                .padding(end = xxSmall)
                .size(small + xxSmall)
                .clickable(onClick = { editData() }),
            imageVector = Icons.Default.Edit,
            contentDescription = "Edit icon for data ${data.name}",
            tint = colors.primary
        )
        Icon(
            modifier = Modifier
                .padding(end = xxSmall)
                .size(small + xxSmall)
                .clickable(onClick = { }),
            imageVector = Icons.Default.Share,
            contentDescription = "Share icon for data ${data.name}",
            tint = colors.primary
        )
        Icon(
            modifier = Modifier
                .padding(end = xxSmall)
                .size(small + xxSmall)
                .clickable(onClick = { deleteData(data) }),
            imageVector = Icons.Default.Close,
            contentDescription = "Delete icon for data ${data.name}",
            tint = colors.primary
        )
    }
}
