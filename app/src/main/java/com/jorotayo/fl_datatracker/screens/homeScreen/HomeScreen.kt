package com.jorotayo.fl_datatracker.screens.homeScreen

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.domain.model.Data
import com.jorotayo.fl_datatracker.navigation.Screen
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.DataEvent
import com.jorotayo.fl_datatracker.screens.homeScreen.components.BasicDeleteDataDialog
import com.jorotayo.fl_datatracker.screens.homeScreen.components.BottomNavigationBar
import com.jorotayo.fl_datatracker.screens.homeScreen.components.HomeScreenEvent
import com.jorotayo.fl_datatracker.screens.homeScreen.components.SearchBar
import com.jorotayo.fl_datatracker.screens.homeScreen.components.SimpleDataRow
import com.jorotayo.fl_datatracker.screens.homeScreen.components.TopBar
import com.jorotayo.fl_datatracker.ui.DefaultSnackbar
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.util.Dimen
import com.jorotayo.fl_datatracker.util.Dimen.medium

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(
    homeState: HomeScreenState,
    onHomeEvent: (HomeScreenEvent) -> Unit,
    onDataEvent: (DataEvent) -> Unit,
    navController: NavController,
) {

    val bottomNavigationItems = listOf(
        Screen.DataFieldsScreen,
        Screen.HomeScreen,
        Screen.DataEntry
    )

    val scaffoldState = rememberScaffoldState()

    rememberSystemUiController().setSystemBarsColor(colors.background)

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = medium)
                    .background(colors.background)
            ) {
                // Top Bar/Search Bar Area
                AnimatedVisibility(visible = homeState.isSearchVisible) {
                    SearchBar(
                        onHomeEvent = onHomeEvent,
                        searchState = homeState
                    )
                }
                AnimatedVisibility(visible = !homeState.isSearchVisible) {
                    TopBar(
                        toggleSearchBar = { onHomeEvent(HomeScreenEvent.ToggleSearchBar) },
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
                .background(color = colors.background)
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
                    .background(color = colors.background)
            ) {
                // Item Count Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, bottom = Dimen.small, end = Dimen.small, start = Dimen.small)
                )
                {
                    Text(
                        text = pluralStringResource(
                            id = R.plurals.items_showing_header,
                            count = homeState.dataList.size,
                            homeState.dataList.size
                        ),
                        style = typography.h5,
                        color = colors.onBackground,
                        fontWeight = SemiBold
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
                        if (homeState.dataList.isNotEmpty()) {
                            for (data in homeState.dataList) {
                                SimpleDataRow(
                                    modifier = Modifier,
                                    data = data,
                                    editData = {
                                        navController.navigate(Screen.DataEntry.route + "?id=${data.dataId}")
                                        onDataEvent(
                                            DataEvent.UpdateDataId(
                                                data.dataId
                                            )
                                        )
                                        Log.d(
                                            "fl_datatracker",
                                            "HomeScreen: edit value ${data.dataId} "
                                        )
                                    },
                                    deleteData = {
                                        onHomeEvent(
                                            HomeScreenEvent.ToggleDeleteDataDialog(
                                                data
                                            )
                                        )
                                    }
                                )
                            }
                        }
                    }
                    BasicDeleteDataDialog(
                        modifier = Modifier,
                        confirmDelete = { onHomeEvent(HomeScreenEvent.DeleteDataItem) },
                        scaffold = scaffoldState,
                        state = homeState.isDeleteDialogVisible,
                        data = homeState.deletedItem
                    )
                }
            }
        }
    }
}


@SuppressLint("UnrememberedMutableState")
@Composable
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Preview(showBackground = true, name = "Light Mode")
fun HomeScreenPreview() {
    FL_DatatrackerTheme {
        HomeScreen(
            navController = rememberNavController(),
            onHomeEvent = {},
            onDataEvent = {},
            homeState = HomeScreenState(
                isSearchVisible = false,
                text = "",
                hint = "",
                isHintVisible = true,
                isDeleteDialogVisible = mutableStateOf(false),
                deletedItem = Data(),
                dataList = emptyList()
            )
        )
    }
}
