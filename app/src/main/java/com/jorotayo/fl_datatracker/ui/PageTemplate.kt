package com.jorotayo.fl_datatracker.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign.Companion.Start
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components.DataFieldRowV2
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.util.Dimen.medium
import com.jorotayo.fl_datatracker.util.Dimen.small
import com.jorotayo.fl_datatracker.util.exampleBooleanField
import com.jorotayo.fl_datatracker.util.exampleShortField

@Preview
@Composable
fun PreviewPageTemplate() {

    FL_DatatrackerTheme {
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
                    .padding(top = medium)
                    .background(colors.background)
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = small),
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
                        // TODO: default snackbar to show on top
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