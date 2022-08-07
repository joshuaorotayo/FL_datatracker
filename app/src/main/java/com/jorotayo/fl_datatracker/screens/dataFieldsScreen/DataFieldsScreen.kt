package com.jorotayo.fl_datatracker.screens.dataFieldsScreen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.runtime.*
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
import com.jorotayo.fl_datatracker.domain.util.use_case.AddDataField
import com.jorotayo.fl_datatracker.navigation.Screen
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components.DataFieldRow
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components.NewDataField
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components.NoDataField
import com.jorotayo.fl_datatracker.screens.homeScreen.components.BottomNavigationBar
import com.jorotayo.fl_datatracker.viewModels.DataFieldsViewModel
import kotlinx.coroutines.flow.collectLatest

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
    viewModel: DataFieldsViewModel = hiltViewModel()
) {

    val bottomNavigationItems = listOf(
        Screen.DataFieldsScreen,
        Screen.HomeScreen,
    )

    val headingMessage by remember { mutableStateOf("Add/Edit Data Fields:") }

    val isAddDataFieldVisible = viewModel.isAddDataFieldVisible.value.isAddDataFieldVisible


    val fields = viewModel.dataFieldsBox

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is DataFieldsViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                DataFieldsViewModel.UiEvent.AddDataField -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "Note Saved!"
                    )
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colors.onBackground)
            ) {
                // Header Row
                item {
                    Row( //Heading row
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(10.dp)
                            .clip(shape = RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colors.onBackground),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
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
                                viewModel.onEvent(DataFieldEvent.ToggleAddNewDataField)
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
                }
                item {
                    AnimatedVisibility(visible = !isAddDataFieldVisible && viewModel.dataFieldsBox.isEmpty) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 10.dp, end = 10.dp, bottom = 20.dp)
                                .clip(shape = RoundedCornerShape(10.dp))
                                .background(MaterialTheme.colors.onBackground),
                            verticalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Spacer(modifier = Modifier.weight(1f))

                            //Show No Data Field Message
                            NoDataField()

                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
                item {
                    AnimatedVisibility(visible = viewModel.isAddDataFieldVisible.value.isAddDataFieldVisible) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 10.dp, end = 10.dp, bottom = 20.dp)
                                .clip(shape = RoundedCornerShape(10.dp))
                                .background(MaterialTheme.colors.onBackground),
                            verticalArrangement = Arrangement.SpaceBetween,
                        ) {
                            // Show New Data Field Message
                            NewDataField(
                                viewModel = DataFieldsViewModel(AddDataField()),
                                onClick = {
                                    viewModel.onEvent(DataFieldEvent.SaveDataField)
                                    Log.i(TAG, "DataFieldsScreen: toggleView")
                                }
                            )
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }

                item {
                    AnimatedVisibility(visible = !viewModel.dataFieldsBox.isEmpty) {
                        Row(
                            modifier = Modifier
                                .wrapContentHeight()
                                .padding(bottom = 5.dp, start = 10.dp, end = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier
                                    .weight(3f),
                                text = "Field Name",
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colors.onSurface
                            )
                            Text(
                                modifier = Modifier
                                    .weight(3f),
                                text = "Field Type",
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colors.onSurface
                            )
                            Text(
                                modifier = Modifier
                                    .weight(1.5f),
                                text = "Enabled?",
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colors.onSurface
                            )
                        }
                    }
                }
                itemsIndexed(viewModel.dataFieldsBox.all) { index, item ->
                    DataFieldRow(
                        dataField = item,
                        editName = {
                            viewModel.onEvent(
                                DataFieldEvent.EditRowName(
                                    index = item.id,
                                    value = it
                                )
                            )
                        },
                        editType = {
                            viewModel.onEvent(
                                (DataFieldEvent.EditRowType(
                                    index = item.id,
                                    value = it
                                ))
                            )
                        },
                        checkedChange = {
                            viewModel.onEvent(DataFieldEvent.CheckedChange(index = item.id))
                        }
                    )
                }
            }
        }
    }
}
