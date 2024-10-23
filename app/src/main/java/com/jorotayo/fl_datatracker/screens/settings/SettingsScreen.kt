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
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign.Companion.Start
import com.jorotayo.fl_datatracker.navigation.SettingScreens
import com.jorotayo.fl_datatracker.ui.DefaultSnackbar
import com.jorotayo.fl_datatracker.ui.theme.AppTheme
import com.jorotayo.fl_datatracker.ui.theme.AppTheme.dimens
import com.jorotayo.fl_datatracker.ui.theme.isDarkMode
import com.jorotayo.fl_datatracker.util.DefaultPreviews

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@DefaultPreviews
@Composable
private fun PreviewSettingsScreen() {
    AppTheme {
        SettingsScreen(
            {},
            {},
            {}
        )
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsScreen(
    onDisplaySettingsClick: () -> Unit,
    onDataFieldSettingsClick: () -> Unit,
    onFAQSListClick: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()

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
                            .padding(top = dimens.large)
                            .background(colors.background)
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(start = dimens.small),
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
                            .padding(dimens.small)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(dimens.small),
                        backgroundColor = colors.surface,
                        elevation = if (isDarkMode()) dimens.xxSmall else dimens.zero
                    ) {
                        Column {
                            SettingRow(
                                setting = SettingScreens.DisplaySettings,
                                onSettingSelected = onDisplaySettingsClick
                            )
                            SettingDivider()
                            SettingRow(
                                setting = SettingScreens.DataFieldSettings,
                                onSettingSelected = onDataFieldSettingsClick
                            )
                            SettingDivider()
                            SettingRow(
                                setting = SettingScreens.FAQsList,
                                onSettingSelected = onFAQSListClick
                            )
                        }
                    }
                }

//                item {
//                    FormBase()
//                }
            }

            DefaultSnackbar(
                modifier = Modifier
                    .align(Alignment.Center),
                snackbarHostState = scaffoldState.snackbarHostState,
                onDismiss = {
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                    // TODO: snackbar method
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
