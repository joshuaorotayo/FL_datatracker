package com.jorotayo.fl_datatracker.screens.dataFieldsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jorotayo.fl_datatracker.navigation.Screen
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components.NoDataField
import com.jorotayo.fl_datatracker.screens.homeScreen.components.BottomNavigationBar
import com.jorotayo.fl_datatracker.viewModels.DataFieldsViewModel

@Preview(showBackground = true)
@Composable
fun PreviewDataFieldsScreen() {
    DataFieldsScreen(
        navController = rememberNavController(),
        viewModel = hiltViewModel()
    )
}

@Composable
fun DataFieldsScreen(
    navController: NavController,
    viewModel: DataFieldsViewModel
) {

    val bottomNavigationItems = listOf(
        Screen.DataFieldsScreen,
        Screen.HomeScreen,
    )
    val headingMessage by remember { mutableStateOf("Add/Edit Data Fields:") }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController, bottomNavigationItems)
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = MaterialTheme.colors.background)
        ) {
            // Contents of data entry form
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, end = 10.dp, bottom = 20.dp, top = 32.dp)
                    .verticalScroll(rememberScrollState())
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colors.onBackground),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                )
                {
                    Text(
                        modifier = Modifier,
                        text = headingMessage,
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.h6.also { FontStyle.Italic },
                        textAlign = TextAlign.Start
                    )
                    IconButton(
                        modifier = Modifier,
                        onClick = {
                            viewModel.onEvent(DataEntryEvent.ToggleAddNewDataField)
                        }) {
                        Icon(
                            modifier = Modifier
                                .size(48.dp),
                            imageVector = Icons.Default.AddBox,
                            contentDescription = "Add New Data Field",
                            tint = MaterialTheme.colors.primary
                        )
                    }
                }
                /*if(viewModel.isVisble)*/
                if (viewModel.dataFieldsBox.isEmpty) {

                    Spacer(modifier = Modifier.weight(1f))

                    //Show No Data Field Message
                    NoDataField()

                    Spacer(modifier = Modifier.weight(1f))

                } else {
                    //show data fields

                }
            }
        }
    }
}
