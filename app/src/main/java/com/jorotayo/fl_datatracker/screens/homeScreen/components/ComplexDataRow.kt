package com.jorotayo.fl_datatracker.screens.homeScreen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.domain.model.Data
import com.jorotayo.fl_datatracker.domain.model.DataItem
import com.jorotayo.fl_datatracker.domain.model.DataItem_
import com.jorotayo.fl_datatracker.util.BoxState
import com.jorotayo.fl_datatracker.util.Dimen

@Composable
@Preview
fun PreviewComplexDataRow() {
    ComplexDataRow(
        data = Data(
            dataId = 0,
            name = "Test Service",
            lastEditedTime = "Yesterday",
            createdTime = "Today"
        ),
        last = false,
        editData = ({})
    )
}

@Composable
fun ComplexDataRow(
    data: Data,
    editData: () -> Unit,
    last: Boolean,
) {
    val textColor = if (isSystemInDarkTheme()) Color.Gray else Color.DarkGray

    val dataItemsList: List<DataItem> =
        BoxState().dataItemBox.query(DataItem_.dataId.equal(data.dataId)).build().find()

    val largestCountField = BoxState().dataItemBox.query(DataItem_.dataId.equal(data.dataId)).and()
        .equal(DataItem_.dataFieldType, 5).build().findFirst()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = Dimen.xxSmall)
            .clip(shape = RoundedCornerShape(10.dp))
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Icon(
                modifier = Modifier,
                imageVector = Icons.Default.Group,
                contentDescription = "Row icon for data ${data.name}",
                tint = MaterialTheme.colors.primary
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = largestCountField?.dataValue!!.ifBlank { "Unknown" },
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.caption,
                textAlign = TextAlign.Center
            )
        }
        Column(
            modifier = Modifier
                .weight(7f)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = data.name,
                color = textColor,
                style = MaterialTheme.typography.h6,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .weight(1f),
                    text = if (dataItemsList.isEmpty()) "" else dataItemsList[0].fieldName + ": ${dataItemsList[0].dataValue}",
                    // text = "field one",
                    color = textColor,
                    style = MaterialTheme.typography.subtitle1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .weight(1f),
                    text = if (dataItemsList.size > 1) dataItemsList[1].dataValue else "Field 2", // TODO: correct  - data.dataFields[1].fieldName + ": ${data.dataFields[1].dataValue}"
                    // text = "field two",
                    color = textColor,
                    style = MaterialTheme.typography.subtitle1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(id = R.string.dataRow_lastEdited) + data.lastEditedTime,
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

    AnimatedVisibility(visible = !last) {
        Divider(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray.copy(0.1f))
        )
    }
}
