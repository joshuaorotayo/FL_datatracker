package com.jorotayo.fl_datatracker.screens.dataFieldsScreenRework

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.jorotayo.fl_datatracker.screens.dataFieldsScreenRework.components.MinimalShortTextField
import com.jorotayo.fl_datatracker.ui.DefaultDualPreview
import com.jorotayo.fl_datatracker.ui.DefaultSnackbar
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.util.Dimen
import com.jorotayo.fl_datatracker.util.Dimen.small
import com.jorotayo.fl_datatracker.util.Dimen.xSmall

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

    /*LaunchedEffect(key1 = Unit) {
        viewModel.initView()
    }*/

    DataFieldsReworkScreen(viewModel = viewModel, state = state)

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DataFieldsReworkScreen(
    viewModel: DataFieldsReworkInterface,
    state: DataFieldsReworkState
) {
    val scaffoldState = rememberScaffoldState()
    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(top = Dimen.large)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    HeaderRow()
                    AddMembers(viewModel::onAddMembersClicked)
                }
            }
        },
        scaffoldState = scaffoldState,
        snackbarHost = {
            scaffoldState.snackbarHostState
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {

            AnimatedVisibility(
                visible = state.isAddMembersFormShowing,
                enter = fadeIn(animationSpec = tween(durationMillis = 500)),
                exit = fadeOut(animationSpec = tween(durationMillis = 300))
            )
            {
                Column {
                    for (i in 1..4) {
                        MinimalShortTextField(
                            rowHeader = "Header for row number: $i",
                            rowHint = "hint for row $i",
                            isError = false
                        )
                    }
                }
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
}

@Composable
private fun HeaderRow() {
    Text(
        modifier = Modifier.padding(start = small),
        text = "Data Fields",
        color = colors.primary,
        style = MaterialTheme.typography.h1,
        textAlign = TextAlign.Start
    )
}


@Composable
fun AddMembers(onAddMembersClick: () -> Unit) {
    IconButton(
        onClick =
        onAddMembersClick
    ) {
        Icon(
            Icons.Filled.Add, contentDescription = "Add Members Button", tint = colors.primary,
            modifier = Modifier
        )
    }
}

@Composable
fun AddMembersForm(viewModel: DataFieldsReworkInterface, state: DataFieldsReworkState) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(xSmall),
        elevation = xSmall,
        contentColor = Color.Blue,
        shape = RoundedCornerShape(small)
    ) {

    }
}