package com.jorotayo.fl_datatracker.screens.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jorotayo.fl_datatracker.navigation.Screen
import com.jorotayo.fl_datatracker.screens.homeScreen.components.BottomNavigationBar
import com.jorotayo.fl_datatracker.screens.homeScreen.components.SearchBar
import com.jorotayo.fl_datatracker.screens.homeScreen.components.SearchVisible
import com.jorotayo.fl_datatracker.screens.homeScreen.components.TopBar
import com.jorotayo.fl_datatracker.viewModels.HomeScreenViewModel

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
    navController: NavController
) {

    val searchBarState = viewModel.searchBarState.value

    val searchFieldState = viewModel.searchFieldState.value

    val itemCount = viewModel.dataItems.value

    val bottomNavigationItems = listOf(
        Screen.DataFieldsScreen,
        Screen.HomeScreen,
    )

    val scaffoldState = rememberScaffoldState()

    val systemUiController = rememberSystemUiController()

    systemUiController.setStatusBarColor(MaterialTheme.colors.primary)

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomAppBar(
                modifier = Modifier,
                cutoutShape = CircleShape
            ) {
                BottomNavigationBar(navController, bottomNavigationItems)
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier,
                backgroundColor = MaterialTheme.colors.primary,
                onClick = {
                    navController.navigate(Screen.DataEntry.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add New Data",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        isFloatingActionButtonDocked = true,
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(innerPadding)
        ) {
            //...main content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.primary)
                        .height(64.dp)
                )
                {
                    //No Content in Row
                }
                // Item Count Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, start = 20.dp)
                )
                {
                    Text(
                        "$itemCount items showing",
                        style = MaterialTheme.typography.h5,
                        color = MaterialTheme.colors.primary
                    )
                }

                // Items example

                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxSize()
                        .clip(shape = RoundedCornerShape(20.dp))
                        .background(MaterialTheme.colors.onBackground)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                    ) {

                    }

                }

            }
            // Top Bar/Search Bar Area
            Row(
                modifier = Modifier
                    .fillMaxWidth()
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