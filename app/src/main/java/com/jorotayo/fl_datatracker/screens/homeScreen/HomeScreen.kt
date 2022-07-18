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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jorotayo.fl_datatracker.screens.homeScreen.components.SearchBar
import com.jorotayo.fl_datatracker.screens.homeScreen.components.SearchVisible
import com.jorotayo.fl_datatracker.screens.homeScreen.components.TopBar
import com.jorotayo.fl_datatracker.viewModels.HomeScreenViewModel

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
    navController: NavHostController
) {
    val scaffoldState = rememberScaffoldState()

    val searchBarState = viewModel.searchBarState.value

    val searchFieldState = viewModel.searchFieldState.value

    /* BottomSheetScaffold(
         sheetPeekHeight = 10.dp,
         sheetBackgroundColor = Color.White,
         sheetElevation = 0.dp,
         sheetShape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp),
         sheetContent = {
         }
     ) {*/
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {

                },
                backgroundColor = MaterialTheme.colors.primary
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp, start = 10.dp, end = 10.dp)
            ) {

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
    val navController = rememberNavController()
    HomeScreen(viewModel = hiltViewModel(), navController = navController)
}