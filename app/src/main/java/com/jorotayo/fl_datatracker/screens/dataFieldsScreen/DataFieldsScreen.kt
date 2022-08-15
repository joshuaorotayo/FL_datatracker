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
import com.jorotayo.fl_datatracker.navigation.Screen
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components.*
import com.jorotayo.fl_datatracker.screens.homeScreen.components.BottomNavigationBar
import com.jorotayo.fl_datatracker.ui.DefaultSnackbar
import com.jorotayo.fl_datatracker.viewModels.DataFieldsViewModel
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun PreviewDataFieldsScreen() {
    DataFieldsScreen(
        navController = rememberNavController(),
        viewModel = hiltViewModel()
    )
}

@OptIn(ExperimentalMaterialApi::class)
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

    val fields = viewModel.dataFieldsBox2

    val scaffoldState = rememberScaffoldState()

    val scope = rememberCoroutineScope()


    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            scaffoldState.snackbarHostState
        },
        bottomBar = {
            BottomNavigationBar(navController, bottomNavigationItems)
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.TopStart),
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 16.dp, horizontal = 10.dp)
                        .clip(shape = RoundedCornerShape(10.dp))
                ) {
                    // Header Row
                    item {
                        Row( //Heading row
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
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
                        AnimatedVisibility(visible = !isAddDataFieldVisible && fields.value.isEmpty) {
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
                                    viewModel = DataFieldsViewModel(),
                                    onClick = {
                                        scope.launch {
                                            // fields.value.put(it)
                                            //  viewModel.onEvent(DataFieldEvent.SaveDataField(it))

                                            val msg = ValidateDataField().invoke(dataField = it)
                                            if (!msg.first) {
                                                viewModel.onEvent(DataFieldEvent.ToggleAddNewDataField)
                                            }
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                message = msg.second,
                                                actionLabel = "",
                                                duration = SnackbarDuration.Short
                                            )
                                        }
                                    }
                                )
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }

                    item {
                        AnimatedVisibility(visible = !fields.value.isEmpty && !viewModel.isAddDataFieldVisible.value.isAddDataFieldVisible) {
                            Row(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .padding(bottom = 8.dp, start = 10.dp, end = 10.dp),
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
                                Text(
                                    modifier = Modifier
                                        .weight(0.5f),
                                    text = "",
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colors.onSurface
                                )
                            }
                        }
                    }
                    itemsIndexed(fields.value.all) { index, item ->
                        DataFieldRow(
                            dataField = item,
                            editName = {
                                viewModel.onEvent(
                                    DataFieldEvent.EditFieldName(
                                        index = item.id,
                                        value = it
                                    )
                                )
                                item.fieldName = it
                            },
                            editType = {
                                viewModel.onEvent(
                                    (DataFieldEvent.EditRowType(
                                        index = item.id,
                                        value = it
                                    ))
                                )
                                item.dataFieldType = it
                            },
                            checkedChange = {
                                viewModel.onEvent(DataFieldEvent.CheckedChange(index = item.id))
                                item.isEnabled = !item.isEnabled
                            },
                            editStateValues = {
                                viewModel.onEvent(
                                    DataFieldEvent.EditStateValues(
                                        index = item.id,
                                        valIndex = it.first,
                                        value = it.second
                                    )
                                )
                                val dataList = item.dataList?.toMutableList()
                                dataList?.set(it.first, it.second)
                                if (dataList != null) {
                                    item.dataList = dataList.toList()
                                }
                            },
                            editHintText = {
                                viewModel.onEvent(
                                    DataFieldEvent.EditHintText(
                                        index = item.id,
                                        value = it
                                    )
                                )
                                item.fieldHint = it
                            },
                            deleteIcon = {
                                // viewModel.onEvent(DataFieldEvent.ConfirmDelete(dataField = item))
                                //isShowAlert.value = true
                                viewModel.onEvent(DataFieldEvent.OpenDeleteDialog(dataField = item))
                                Log.i(TAG, "DataFieldsScreen: delete icon")
                            }
                        )
                    }
                }
            }

            DeleteDialog(
                modifier = Modifier.align(Alignment.Center),
                confirmDelete = { /*TODO*/ },
                state = viewModel.isShowAlert,
                dataField = viewModel.deletedDataField.value
            )

            DefaultSnackbar(
                snackbarHostState = scaffoldState.snackbarHostState,
                onDismiss = { scaffoldState.snackbarHostState.currentSnackbarData?.dismiss() },
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}
