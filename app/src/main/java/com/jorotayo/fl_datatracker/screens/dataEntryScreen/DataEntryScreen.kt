package com.jorotayo.fl_datatracker.screens.dataEntryScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jorotayo.fl_datatracker.viewModels.HomeScreenViewModel

@Preview(showBackground = true/*, showSystemUi = true*/)
@Composable
fun PreviewDataEntryScreen() {
    DataEntryScreen(viewModel = hiltViewModel())
}

@Composable
fun DataEntryScreen(
    viewModel: HomeScreenViewModel
) {
    val meetingName by remember { mutableStateOf("Meeting Name") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.primary)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = meetingName,
                color = MaterialTheme.colors.onPrimary,
                fontStyle = FontStyle.Italic.also { MaterialTheme.typography.h4 },
                textAlign = TextAlign.Center
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(5.dp)
                .clip(shape = RoundedCornerShape(30.dp))
                .background(MaterialTheme.colors.onPrimary)
        )
        {
            // Contents of data entry form
        }
    }
}
