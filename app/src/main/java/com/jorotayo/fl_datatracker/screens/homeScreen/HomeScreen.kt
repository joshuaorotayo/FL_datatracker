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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.jorotayo.fl_datatracker.util.Dimen.small
import com.jorotayo.fl_datatracker.util.Dimen.xSmall
import com.jorotayo.fl_datatracker.util.components.AlertDialog

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    state: HomeScreenState,
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
            ) {
                // Top Bar/Search Bar Area
                AnimatedVisibility(visible = state.isSearchVisible) {
                    SearchBar(
                        onHomeEvent = onHomeEvent,
                        searchState = state
                    )
                }
                AnimatedVisibility(visible = !state.isSearchVisible) {
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
                        .padding(
                            top = 24.dp,
                            bottom = small,
                            end = small,
                            start = small
                        )
                )
                {
                    Text(
                        text = pluralStringResource(
                            id = R.plurals.items_showing_header,
                            count = state.dataList.size,
                            state.dataList.size
                        ),
                        style = typography.h5,
                        color = colors.onBackground,
                        fontWeight = SemiBold
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    Card(
                        modifier = Modifier
                            .padding(xSmall)
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        shape = RoundedCornerShape(small),
                        elevation = xSmall
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .wrapContentHeight()
                                .fillMaxWidth()
                                .background(color = colors.surface)
                        ) {
                            itemsIndexed(items = state.dataList) { index, data ->
                                if (state.dataList.isNotEmpty()) {
                                    SimpleDataRow(
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
                                    if (index < state.dataList.size - 1) {
                                        Divider(
                                            modifier = Modifier
                                                .padding(xSmall),
                                            thickness = Dimen.xxxxSmall
                                        )
                                    }
                                }
                            }
                        }
                    }

                    state.alertDialogState?.let { AlertDialog(alertDialogState = it) }

                    BasicDeleteDataDialog(
                        modifier = Modifier,
                        confirmDelete = { onHomeEvent(HomeScreenEvent.DeleteDataItem) },
                        scaffold = scaffoldState,
                        state = state.isDeleteDialogVisible,
                        data = state.deletedItem
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
            state = HomeScreenState(
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
