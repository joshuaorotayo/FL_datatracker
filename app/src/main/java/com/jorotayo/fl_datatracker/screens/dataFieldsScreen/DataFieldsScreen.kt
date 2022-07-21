package com.jorotayo.fl_datatracker.screens.dataFieldsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jorotayo.fl_datatracker.navigation.Screen
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
    val meetingName by remember { mutableStateOf("Fill in the information below:") }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController, bottomNavigationItems)
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = MaterialTheme.colors.primary)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(vertical = 20.dp, horizontal = 8.dp)
                    .clip(shape = RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colors.primaryVariant.copy(alpha = 0.3f))
            )
            {
                // Contents of data entry form
                Column(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp, bottom = 20.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp)
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = meetingName,
                            color = MaterialTheme.colors.surface,
                            style = MaterialTheme.typography.h6.also { FontStyle.Italic },
                            textAlign = TextAlign.Start
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    if (viewModel.dataFieldsBox.isEmpty) {
                        //empty Message
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp)
                                .clip(shape = RoundedCornerShape(10.dp))
                                .shadow(8.dp)
                                .background(MaterialTheme.colors.surface)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(10.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    modifier = Modifier.size(128.dp),
                                    imageVector = Icons.Default.AddBox,
                                    tint = MaterialTheme.colors.primary,
                                    contentDescription = "No Data Fields Message",
                                )
                                Text(
                                    modifier = Modifier
                                        .padding(top = 5.dp),
                                    text = "There are currently no Data Fields, please add some to begin adding data ",
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.h5,
                                    color = MaterialTheme.colors.primary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
