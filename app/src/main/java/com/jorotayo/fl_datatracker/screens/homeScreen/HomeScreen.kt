package com.jorotayo.fl_datatracker.screens.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jorotayo.fl_datatracker.screens.homeScreen.components.SearchBar
import com.jorotayo.fl_datatracker.screens.homeScreen.components.TopBar
import com.jorotayo.fl_datatracker.viewModels.HomeScreenViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel
) {

    val count by remember { mutableStateOf(viewModel.count) }

    val scaffoldState = rememberScaffoldState()

    val scope = rememberCoroutineScope()

    val isSearchVisible by remember { mutableStateOf(viewModel.isSearchVisible) }

    val searchFieldState = viewModel.searchFieldState.value

    BottomSheetScaffold(
        sheetPeekHeight = 10.dp,
        sheetBackgroundColor = Color.White,
        sheetElevation = 0.dp,
        sheetShape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp),
        sheetContent = {
        }
    ) {
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

                    if (!isSearchVisible.value) {
                        TopBar(
                            viewModel = viewModel,
                            isSearchVisible = isSearchVisible.value
                        )
                    } else {
                        SearchBar(
                            viewModel = viewModel,
                            isSearchVisible = isSearchVisible.value,
                            searchFieldState = searchFieldState
                        )
                    }
                    Row {
                        Text(
                            text = "${count.value} Number of Clicks",
                            style = MaterialTheme.typography.h6
                        )

                    }

                    /*SimpleIconButton(
                        icon = Icons.Default.Add,
                        contentDescription = "Add One",
                        modifier = Modifier,
                        tint = Color.White
                    ) {
                        viewModel.onEvent(HomeScreenEvent.IncrementCount)
                    }
                    SimpleIconButton(
                        icon = Icons.Default.Remove,
                        contentDescription = "Add One",
                        modifier = Modifier,
                        tint = Color.White
                    ) {
                        viewModel.onEvent(HomeScreenEvent.DecrementCount)
                    }*/
                }

            }
        }
    }
}

@Composable
@Preview(showBackground = false)
fun HomeScreenPreview() {
    HomeScreen(viewModel = hiltViewModel())
}