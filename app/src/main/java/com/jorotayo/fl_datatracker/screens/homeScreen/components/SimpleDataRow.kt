package com.jorotayo.fl_datatracker.screens.homeScreen.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.SpaceEvenly
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.jorotayo.fl_datatracker.domain.model.Data
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.util.Dimen.xxSmall

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Preview(showBackground = true, name = "Light Mode")
@Composable
fun PreviewSimpleDataRow() {
    FL_DatatrackerTheme {
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
}

@Composable
fun SimpleDataRow(
    modifier: Modifier,
    data: Data,
    editData: () -> Unit,
    deleteData: (Data) -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(xxSmall)
            .fillMaxWidth(),
        horizontalArrangement = SpaceEvenly,
        verticalAlignment = CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f),
            text = data.name,
            color = colors.onBackground,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .weight(1f),
            text = data.createdTime,
            color = colors.onBackground,
            style = MaterialTheme.typography.caption,
            textAlign = TextAlign.Center
        )
        Icon(
            modifier = Modifier
                .weight(0.25f)
                .clickable(onClick = { editData() }),
            imageVector = Icons.Default.Edit,
            contentDescription = "Edit icon for data ${data.name}",
            tint = colors.primary
        )
        Icon(
            modifier = Modifier
                .weight(0.25f)
                .clickable(onClick = { deleteData(data) }),
            imageVector = Icons.Default.Close,
            contentDescription = "Delete icon for data ${data.name}",
            tint = colors.primary
        )
    }
}