package com.jorotayo.fl_datatracker.screens.dataFieldsScreenRework

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.jorotayo.fl_datatracker.ui.DefaultDualPreview
import com.jorotayo.fl_datatracker.ui.DefaultSnackbar
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.util.Dimen

@DefaultDualPreview
@Composable
fun PreviewDataFieldsScreenRework() {
    FL_DatatrackerTheme {
        DataFieldsReworkScreen(
            state = DataFieldsReworkState(), viewModel = dataFieldsReworkPreview
        )
    }
}

@Composable
fun DataFieldsReworkView() {
    val viewModel = hiltViewModel<DataFieldsReworkViewModel>()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.initView()
    }

    DataFieldsReworkScreen(viewModel = viewModel, state = state)

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DataFieldsReworkScreen(
    viewModel: DataFieldsReworkInterface,
    state: DataFieldsReworkState
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(topBar = {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(top = Dimen.large)
        ) {
            HeaderRow()
        }
    }, content = { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = Dimen.xSmall, bottom = Dimen.bottomBarPadding),
            ) {

            }

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
                        //function
                    }
                }
            )
        }
    }
    )
}

@Composable
private fun HeaderRow() {
    Text(
        modifier = Modifier.padding(start = Dimen.small),
        text = "Data Fields",
        color = MaterialTheme.colors.primary,
        style = MaterialTheme.typography.h1,
        textAlign = TextAlign.Start
    )
}