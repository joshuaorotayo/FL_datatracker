package com.jorotayo.fl_datatracker.screens.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jorotayo.fl_datatracker.navigation.Screen
import com.jorotayo.fl_datatracker.screens.homeScreen.components.SearchBar
import com.jorotayo.fl_datatracker.screens.homeScreen.components.SearchVisible
import com.jorotayo.fl_datatracker.screens.homeScreen.components.TopBar
import com.jorotayo.fl_datatracker.viewModels.HomeScreenViewModel

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
    navController: NavController
) {
    val scaffoldState = rememberScaffoldState()

    val searchBarState = viewModel.searchBarState.value

    val searchFieldState = viewModel.searchFieldState.value

    val itemCount = viewModel.dataItems.value

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {

        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.DataEntry.route)
                    //Toast.makeText(context, "Showing toast....", Toast.LENGTH_SHORT).show()
                },
                backgroundColor = MaterialTheme.colors.primaryVariant
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add New Data",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
    ) { innerPadding ->
        //...main content
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colors.primary)

        ) {
            // Top Bar/Search Bar Area
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp, start = 10.dp)
            )
            {
                if (searchBarState.isSearchVisible == SearchVisible.SearchBarHidden) {
                    TopBar(
                        viewModel = viewModel,
                        context = LocalContext.current
                    )
                } else {
                    SearchBar(
                        viewModel = viewModel,
                        searchFieldState = searchFieldState
                    )
                }
            }
            // Item Count Area
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp, start = 20.dp)
            )
            {
                Text(
                    "$itemCount items showing",
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onPrimary
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