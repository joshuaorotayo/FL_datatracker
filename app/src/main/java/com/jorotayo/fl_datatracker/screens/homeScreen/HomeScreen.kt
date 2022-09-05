package com.jorotayo.fl_datatracker.screens.homeScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.TestRowItem
import com.jorotayo.fl_datatracker.navigation.Screen
import com.jorotayo.fl_datatracker.screens.homeScreen.components.*
import com.jorotayo.fl_datatracker.ui.DefaultSnackbar
import com.jorotayo.fl_datatracker.viewModels.HomeScreenViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    navController: NavController,
) {

    val uiState = viewModel.uiState.value

    val bottomNavigationItems = listOf(
        Screen.DataFieldsScreen,
        Screen.HomeScreen,
    )

    val scaffoldState = rememberScaffoldState()

    val systemUiController = rememberSystemUiController()

    val testList = viewModel.testRowItemBox

    systemUiController.setStatusBarColor(MaterialTheme.colors.background)

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController, bottomNavigationItems)
        },
        scaffoldState = scaffoldState,
        snackbarHost = {
            scaffoldState.snackbarHostState
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier,
                backgroundColor = MaterialTheme.colors.background,
                onClick = {
                    navController.navigate(Screen.DataEntry.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add New Data",
                    tint = Color.Gray
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        isFloatingActionButtonDocked = true,
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            DefaultSnackbar(
                snackbarHostState = scaffoldState.snackbarHostState,
                onDismiss = { scaffoldState.snackbarHostState.currentSnackbarData?.dismiss() },
                modifier = Modifier
                    .align(Alignment.Center)
            )


            LazyColumn(modifier = Modifier
                .background(MaterialTheme.colors.background)
            ) {
                item {
                    // Item Count Header
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 90.dp, start = 20.dp)
                    )
                    {
                        Text(
                            "${testList.value.itemsList.size}: Test items showing",
                            style = MaterialTheme.typography.h5,
                            color = MaterialTheme.colors.primary
                        )
                    }
                    // Test Row
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Add a New Field",
                            color = Color.Black
                        )
                        TextButton(onClick = {
                            val newBox = ObjectBox.get().boxFor(TestRowItem::class.java)
                            newBox.put(TestRowItem(0))
                            viewModel.onEvent(HomeScreenEvent.UpdateData(newBox))
                        }) {
                            Text(
                                text = "add"
                            )
                        }
                    }
                }
                items(testList.value._itemsBox.all) { item ->
                    TestRow(number = item.testRowId.toInt()) {
                        val newBox = ObjectBox.get().boxFor(TestRowItem::class.java)
                        testList.value._itemsBox.remove(item.testRowId)
                        viewModel.onEvent(HomeScreenEvent.UpdateData(newBox))
                    }
                }
            }
            //...main content
            /*  Column(
                  modifier = Modifier
                      .fillMaxSize()
                      .background(MaterialTheme.colors.background)
                      .verticalScroll(rememberScrollState()),
                  verticalArrangement = Arrangement.Top
              ) {
                  Row(
                      modifier = Modifier
                          .fillMaxWidth()
                          .background(MaterialTheme.colors.background)
                          .height(20.dp)
                  )
                  {
                      //No Content in Row
                  }
                  // Item Count Header
                  Row(
                      modifier = Modifier
                          .fillMaxWidth()
                          .padding(top = 90.dp, start = 20.dp)
                  )
                  {
                      Text(
                          "${uiState.dataItems.size} items showing",
                          style = MaterialTheme.typography.h5,
                          color = MaterialTheme.colors.primary
                      )
                  }

                  // Items example
                  Box(
                      modifier = Modifier
                          .padding(16.dp)
                          .fillMaxSize()
                          .clip(shape = RoundedCornerShape(20.dp))
                          .background(MaterialTheme.colors.onBackground)
                  ) {
                      Column(
                          modifier = Modifier
                      ) {
                          if (uiState.dataItems.isNotEmpty()) {
                              for (dataItem in uiState.dataItems) {
                                  ComplexDataRow(data = dataItem, editData = {
                                      navController.navigate(Screen.DataEntry.route + "?dataId=${dataItem.dataId}")
                                  })
                              }
                          }
                      }
                  }
              }*/
            // Top Bar/Search Bar Area
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.background)
                    .padding(top = 40.dp)
            )
            {
                AnimatedVisibility(visible = uiState.isSearchVisible) {
                    SearchBar(
                        viewModel = viewModel,
                        searchState = uiState
                    )
                }
                AnimatedVisibility(visible = !uiState.isSearchVisible) {
                    TopBar(
                        toggleSearchBar = { viewModel.onEvent(HomeScreenEvent.ToggleSearchBar) },
                        showSettingsView = { viewModel.onEvent(HomeScreenEvent.ShowSettingsView) }
                    )
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = false)
fun HomeScreenPreview() {
    HomeScreen(
        viewModel = hiltViewModel(),
        navController = rememberNavController()
    )
}