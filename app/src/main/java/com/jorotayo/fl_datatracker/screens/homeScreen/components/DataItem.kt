package com.jorotayo.fl_datatracker.screens.homeScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Group
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jorotayo.fl_datatracker.screens.homeScreen.HomeScreenViewModel

@Preview(showBackground = true)
@Composable
fun PreviewDataItem() {
    DataItem(viewModel = hiltViewModel())
}

@Composable
fun DataItem(
    viewModel: HomeScreenViewModel
) {
    val meetingName = "City Church Service"
    val meetingDate = "2022.06.26"
    val preacher = "Pastor Andrew Gyamfi"
    val location = "King Solomon School (B7 4AA)"

    Card(
        modifier = Modifier
            .wrapContentSize()
            .background(color = MaterialTheme.colors.onPrimary)
            .clickable { },
        elevation = 10.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(10.dp)
        ) {
            // first icons
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = Modifier
                        .size(48.dp)
                        .padding(bottom = 0.dp),
                    imageVector = Icons.Default.Group,
                    tint = MaterialTheme.colors.primary.copy(0.6f),
                    contentDescription = "Group Icon"
                )
                Text(
                    modifier = Modifier,
                    text = "500",
                    color = MaterialTheme.colors.primary.copy(0.6f),
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold
                )
            }
            // Middle Text
            Column(
                modifier = Modifier
                    .weight(5f)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "$meetingName - $meetingDate",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = preacher,
                        textAlign = TextAlign.Center,
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = location,
                        textAlign = TextAlign.Center,
                    )
                }
            }
            // Last Icon icons
            Column(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = {
                    viewModel.onEvent(HomeScreenEvent.EditDataItem)
                }) {
                    Icon(
                        modifier = Modifier
                            .size(48.dp),
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit data for meeting",
                        tint = MaterialTheme.colors.primary.copy(0.6f),
                    )
                }
            }
        }
    }
}
