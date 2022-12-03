package com.jorotayo.fl_datatracker.screens.homeScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jorotayo.fl_datatracker.navigation.Screen
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.DataEvent
import com.jorotayo.fl_datatracker.screens.homeScreen.components.*
import com.jorotayo.fl_datatracker.ui.DefaultSnackbar
import com.jorotayo.fl_datatracker.viewModels.DataEntryScreenViewModel
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
        Screen.DataEntry
    )

    val scaffoldState = rememberScaffoldState()

    val systemUiController = rememberSystemUiController()

    val testList = viewModel.testRowItemBox

    systemUiController.setStatusBarColor(MaterialTheme.colors.primary)

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.1f)
                    .background(MaterialTheme.colors.primary)
            ) {
                // Top Bar/Search Bar Area
                AnimatedVisibility(visible = uiState.isSearchVisible) {
                    SearchBar(
                        viewModel = viewModel,
                        searchState = uiState
                    )
                }
                AnimatedVisibility(visible = !uiState.isSearchVisible) {
                    TopBar(
                        toggleSearchBar = { viewModel.onEvent(HomeScreenEvent.ToggleSearchBar) },
                        showSettingsView = { viewModel.onEvent(HomeScreenEvent.ShowSettingsView) },
                        settingsNavigate = { navController.navigate(Screen.Settings.route) }
                    )
                }
            }
        },
        bottomBar = {
            BottomNavigationBar(navController, bottomNavigationItems)
        },
        scaffoldState = scaffoldState,
        snackbarHost = {
            scaffoldState.snackbarHostState
        }

    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = MaterialTheme.colors.primary)
        ) {
            DefaultSnackbar(
                snackbarHostState = scaffoldState.snackbarHostState,
                onDismiss = { scaffoldState.snackbarHostState.currentSnackbarData?.dismiss() },
                modifier = Modifier
                    .align(Alignment.Center)
            )
            //...main content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clip(shape = RoundedCornerShape(topEnd = 40.dp, topStart = 40.dp))
                    .background(color = MaterialTheme.colors.background)
            ) {
                // Item Count Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 20.dp)
                )
                {
                    Text(
                        "${uiState.dataList.size} items showing",
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
                ) {
                    Column(
                        modifier = Modifier
                    ) {
                        if (uiState.dataList.isNotEmpty()) {
                            for (data in uiState.dataList) {
                                SimpleDataRow(
                                    modifier = Modifier,
                                    data = data,
                                    editData = { dataId ->
                                        navController.navigate(Screen.DataEntry.route + "?dataId=${dataId}")
                                        DataEntryScreenViewModel().onEvent(DataEvent.UpdateDataId(
                                            dataId))
                                    },
                                    deleteData = {
                                        viewModel.onEvent(HomeScreenEvent.ToggleDeleteDataDialog(
                                            data))
                                    }
                                )
                            }
                        }
                    }
                }
                DeleteDataDialog(modifier = Modifier,
                    confirmDelete = { viewModel.onEvent(HomeScreenEvent.DeleteDataItem) },
                    scaffold = scaffoldState,
                    state = uiState.isDeleteDialogVisible,
                    data = uiState.deletedItem
                )
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