package com.jorotayo.fl_datatracker.screens.settings

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign.Companion.Start
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jorotayo.fl_datatracker.navigation.SettingScreens
import com.jorotayo.fl_datatracker.screens.settings.SettingEvent.DataFieldSettings
import com.jorotayo.fl_datatracker.screens.settings.SettingEvent.DisplaySettings
import com.jorotayo.fl_datatracker.screens.settings.SettingEvent.FAQsList
import com.jorotayo.fl_datatracker.screens.settings.SettingsViewModel.SettingNavigation
import com.jorotayo.fl_datatracker.screens.settings.states.DisplayUiState
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.DefaultSnackbar
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.ui.theme.isDarkMode
import com.jorotayo.fl_datatracker.util.Dimen
import com.jorotayo.fl_datatracker.util.Dimen.large
import com.jorotayo.fl_datatracker.util.Dimen.small

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@DefaultPreviews
@Composable
private fun PreviewSettingsScreen() {
    FL_DatatrackerTheme {
        SettingsScreenView(
            scaffoldState = rememberScaffoldState(),
            onSettingEvent = {}
        )
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun SettingsScreen(
    navController: NavController
) {
    val viewModel = hiltViewModel<SettingsViewModel>()
    val uiState by viewModel.uiState.collectAsState(DisplayUiState())

    val scaffoldState = rememberScaffoldState()

    val onSettingEvent = viewModel::onSettingEvent
    LaunchedEffect(key1 = true) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                SettingNavigation.DataFieldSettings -> {
                    navController.navigate(SettingScreens.DataFieldSettings.route) {
                        popUpTo("settings_screen")
                        launchSingleTop = true
                    }
                }

                SettingNavigation.DisplaySettings -> {
                    navController.navigate(SettingScreens.DisplaySettings.route) {
                        popUpTo("settings_screen")
                        launchSingleTop = true
                    }
                }

                SettingNavigation.FAQsList -> {
                    navController.navigate(SettingScreens.FAQsList.route) {
                        popUpTo("settings_screen")
                        launchSingleTop = true
                    }
                }
            }
        }
    }

    SettingsScreenView(scaffoldState, onSettingEvent)

}

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun SettingsScreenView(
    scaffoldState: ScaffoldState,
    onSettingEvent: (SettingEvent) -> Unit
) {

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            scaffoldState.snackbarHostState
        },
        topBar = {
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(top = large)
                            .background(colors.background)
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(start = small),
                            text = "Settings",
                            color = colors.primary,
                            style = typography.h1,
                            textAlign = Start
                        )
                    }
                }
                item {
                    Card(
                        modifier = Modifier
                            .padding(small)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(small),
                        backgroundColor = colors.surface,
                        elevation = if (isDarkMode()) Dimen.one else Dimen.xxSmall
                    ) {
                        Column {
                            SettingRow(
                                setting = SettingScreens.DisplaySettings,
                                onSettingSelected = { onSettingEvent(DisplaySettings) }
                            )
                            SettingRow(
                                setting = SettingScreens.DataFieldSettings,
                                onSettingSelected = { onSettingEvent(DataFieldSettings) }
                            )
                            SettingRow(
                                setting = SettingScreens.FAQsList,
                                onSettingSelected = { onSettingEvent(FAQsList) }
                            )
                        }
                    }
                }
            }

            DefaultSnackbar(
                modifier = Modifier
                    .align(Alignment.Center),
                snackbarHostState = scaffoldState.snackbarHostState,
                onDismiss = {
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                    /*if (scaffoldState.snackbarHostState.currentSnackbarData?.actionLabel?.contains(
                        "Restore"
                    ) == true
                ) {
                        viewModel.onDataEvent(DataFieldEvent.RestoreDeletedField)

                }*/
                }
            )
        }
    }
}
