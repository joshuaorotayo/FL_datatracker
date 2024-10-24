package com.jorotayo.fl_datatracker.screens.homeScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.domain.model.Data
import com.jorotayo.fl_datatracker.navigation.MainScreens
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.DataEvent
import com.jorotayo.fl_datatracker.screens.homeScreen.components.BasicDeleteDataDialog
import com.jorotayo.fl_datatracker.screens.homeScreen.components.HomeScreenEvent
import com.jorotayo.fl_datatracker.screens.homeScreen.components.MembersPanel
import com.jorotayo.fl_datatracker.screens.homeScreen.components.SearchBar
import com.jorotayo.fl_datatracker.screens.homeScreen.components.SearchFilters
import com.jorotayo.fl_datatracker.screens.homeScreen.components.SimpleDataRow
import com.jorotayo.fl_datatracker.screens.homeScreen.components.TopBar
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.DefaultSnackbar
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.ui.theme.isDarkMode
import com.jorotayo.fl_datatracker.ui.theme.subtitleTextColour
import com.jorotayo.fl_datatracker.util.Dimen.bottomBarPadding
import com.jorotayo.fl_datatracker.util.Dimen.medium
import com.jorotayo.fl_datatracker.util.Dimen.one
import com.jorotayo.fl_datatracker.util.Dimen.small
import com.jorotayo.fl_datatracker.util.Dimen.xSmall
import com.jorotayo.fl_datatracker.util.Dimen.xxSmall
import com.jorotayo.fl_datatracker.util.Dimen.xxxSmall
import com.jorotayo.fl_datatracker.util.Dimen.zero
import com.jorotayo.fl_datatracker.util.components.AlertDialogLayout

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    state: HomeScreenState,
    onHomeEvent: (HomeScreenEvent) -> Unit,
    onDataEvent: (DataEvent) -> Unit,
    navController: NavController,
) {
    val scaffoldState = rememberScaffoldState()

    val density = LocalDensity.current
    var expandData by remember { mutableStateOf(false) }

    Scaffold(
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
            // ...main content
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
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
                    AnimatedVisibility(
                        visible = state.isSearchVisible,
                        enter = slideInVertically {
                            with(density) { -40.dp.roundToPx() }
                        } + expandVertically(
                            expandFrom = Alignment.Top
                        ) + fadeIn(
                            initialAlpha = 0.3f
                        ),
                        exit = slideOutVertically() + shrinkVertically() + fadeOut()
                    ) {
                        SearchFilters()
                    }
                    AnimatedVisibility(visible = !state.isSearchVisible) {
                        TopBar(
                            toggleSearchBar = { onHomeEvent(HomeScreenEvent.ToggleSearchBar) },
                        )
                    }
                }
                // Item Count Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = small,
                            bottom = one,
                            end = small,
                            start = small
                        )
                ) {
                    Text(
                        text = pluralStringResource(
                            id = R.plurals.items_showing_header,
                            count = state.dataList.size,
                            state.dataList.size
                        ),
                        style = typography.h3,
                        color = colors.subtitleTextColour
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(bottom = bottomBarPadding + small)
                        .wrapContentSize()
                ) {
                    Card(
                        modifier = Modifier
                            .padding(small)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(small),
                        backgroundColor = colors.surface,
                        elevation = if (isDarkMode()) xxSmall else zero
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .then(
                                    if (expandData) Modifier.fillMaxHeight() else Modifier.wrapContentHeight()
                                )
                                .fillMaxWidth()
                        ) {
                            item {
                                if (state.dataList.size > 5 && !expandData) {
                                    Surface(
                                        modifier = Modifier
                                            .padding(vertical = xxxSmall, horizontal = xSmall)
                                            .wrapContentWidth()
                                            .align(Alignment.CenterEnd),
                                        color = colors.background,
                                        shape = RoundedCornerShape(xSmall)
                                    ) {
                                        Text(
                                            modifier = Modifier
                                                .padding(top = xxSmall)
                                                .clickable {
                                                    expandData = !expandData
                                                },
                                            text = "Show All Data",
                                            color = colors.primary
                                        )
                                    }
                                }

                                if (state.dataList.size > 5 && expandData) {
                                    Surface(
                                        modifier = Modifier
                                            .padding(vertical = xxxSmall, horizontal = xSmall)
                                            .fillMaxWidth(),
                                        color = colors.surface,
                                        shape = RoundedCornerShape(xSmall)
                                    ) {
                                        Text(
                                            modifier = Modifier
                                                .align(Alignment.CenterEnd)
                                                .padding(top = xxSmall)
                                                .clickable {
                                                    expandData = !expandData
                                                },
                                            text = "Minimise Data",
                                            color = colors.primary
                                        )
                                    }
                                }
                            }

                            itemsIndexed(items = state.dataList) { index, data ->
                                if (state.dataList.isNotEmpty()) {
                                    SimpleDataRow(
                                        data = data,
                                        editData = {
                                            navController.navigate(MainScreens.DataEntry.route + "?id=${data.dataId}")
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
                    }

                    state.alertDialogState?.let { AlertDialogLayout(alertDialogState = it) }

                    BasicDeleteDataDialog(
                        modifier = Modifier,
                        confirmDelete = { onHomeEvent(HomeScreenEvent.DeleteDataItem) },
                        scaffold = scaffoldState,
                        state = state.isDeleteDialogVisible,
                        data = state.deletedItem
                    )
                }
                MembersPanel(modifier = Modifier.padding(horizontal = small), totalCount = 52)
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
@DefaultPreviews
fun HomeScreenPreview() {
    FL_DatatrackerTheme {
        HomeScreen(
            navController = rememberNavController(),
            onHomeEvent = {},
            onDataEvent = {},
            state = HomeScreenState(
                isSearchVisible = true,
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
