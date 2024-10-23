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
import com.jorotayo.fl_datatracker.screens.homeScreen.components.SearchBar
import com.jorotayo.fl_datatracker.screens.homeScreen.components.SearchFilters
import com.jorotayo.fl_datatracker.screens.homeScreen.components.SimpleDataRow
import com.jorotayo.fl_datatracker.screens.homeScreen.components.TopBar
import com.jorotayo.fl_datatracker.ui.DefaultSnackbar
import com.jorotayo.fl_datatracker.ui.theme.AppTheme
import com.jorotayo.fl_datatracker.ui.theme.AppTheme.dimens
import com.jorotayo.fl_datatracker.ui.theme.isDarkMode
import com.jorotayo.fl_datatracker.ui.theme.subtitleTextColour
import com.jorotayo.fl_datatracker.util.DefaultPreviews
import com.jorotayo.fl_datatracker.util.components.AlertDialogLayout

@SuppressLint("UnrememberedMutableState")
@Composable
@DefaultPreviews
fun HomeScreenPreview() {
    AppTheme {
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
                        .padding(top = dimens.medium)
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
                            top = dimens.small,
                            bottom = dimens.one,
                            end = dimens.small,
                            start = dimens.small
                        )
                ) {
                    Text(
                        text = pluralStringResource(
                            id = R.plurals.items_showing_header,
                            count = state.dataList.size,
                            state.dataList.size
                        ),
                        style = AppTheme.typography.titleSmall,
                        color = colors.subtitleTextColour
                    )
                }
                Text(
                    text = pluralStringResource(
                        id = R.plurals.members_showing_header,
                        count = state.memberList.size,
                        state.memberList.size
                    ),
                    style = AppTheme.typography.titleSmall,
                    color = colors.subtitleTextColour
                )
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(bottom = dimens.bottomBarPadding + dimens.small)
                        .wrapContentSize()
                ) {
                    Card(
                        modifier = Modifier
                            .padding(dimens.small)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(dimens.small),
                        backgroundColor = colors.surface,
                        elevation = if (isDarkMode()) dimens.xxSmall else dimens.zero
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
                                            .padding(
                                                vertical = dimens.xxxSmall,
                                                horizontal = dimens.xSmall
                                            )
                                            .wrapContentWidth()
                                            .align(Alignment.CenterEnd),
                                        color = colors.background,
                                        shape = RoundedCornerShape(dimens.xSmall)
                                    ) {
                                        Text(
                                            modifier = Modifier
                                                .padding(top = dimens.xxSmall)
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
                                            .padding(
                                                vertical = dimens.xxxSmall,
                                                horizontal = dimens.xSmall
                                            )
                                            .fillMaxWidth(),
                                        color = colors.surface,
                                        shape = RoundedCornerShape(dimens.xSmall)
                                    ) {
                                        Text(
                                            modifier = Modifier
                                                .align(Alignment.CenterEnd)
                                                .padding(top = dimens.xxSmall)
                                                .clickable {
                                                    expandData = !expandData
                                                },
                                            text = "Minimise Data",
                                            color = AppTheme.colors.onBackground
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
//                MembersPanel(modifier = Modifier.padding(horizontal = small), totalCount = 52)
            }
        }
    }
}
