package com.jorotayo.fl_datatracker.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign.Companion.Start
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components.DataFieldRowV2
import com.jorotayo.fl_datatracker.ui.theme.AppTheme
import com.jorotayo.fl_datatracker.ui.theme.AppTheme.dimens
import com.jorotayo.fl_datatracker.util.DefaultPreviews
import com.jorotayo.fl_datatracker.util.exampleBooleanField
import com.jorotayo.fl_datatracker.util.exampleShortField

@DefaultPreviews
@Composable
fun PreviewPageTemplate() {
    AppTheme {
        PageTemplate()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PageTemplate() {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            scaffoldState.snackbarHostState
        },
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = dimens.medium)
                    .background(colors.background)
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = dimens.small),
                    text = "Page Heading",
                    color = colors.primary,
                    style = typography.h4.also { FontWeight.SemiBold },
                    textAlign = Start
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            DefaultSnackbar(
                modifier = Modifier
                    .align(Alignment.Center),
                snackbarHostState = scaffoldState.snackbarHostState,
                onDismiss = {
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                    if (scaffoldState.snackbarHostState.currentSnackbarData?.actionLabel?.contains(
                            "Restore"
                        ) == true
                    ) {
//                            viewModel.onDataEvent(DataFieldEvent.RestoreDeletedField)
                    }
                }
            )
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                    .background(colors.background)
            ) {
                item {
                    DataFieldRowV2(
                        currentDataField = exampleShortField,
                        onRowEvent = {},
                        onDataFieldEvent = {}
                    )
                    DataFieldRowV2(
                        currentDataField = exampleBooleanField,
                        onRowEvent = {},
                        onDataFieldEvent = {}
                    )
                }
            }
        }
    }
}
