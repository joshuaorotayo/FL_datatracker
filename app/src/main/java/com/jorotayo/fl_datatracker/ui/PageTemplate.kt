package com.jorotayo.fl_datatracker.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jorotayo.fl_datatracker.navigation.Screen
import com.jorotayo.fl_datatracker.screens.homeScreen.components.BottomNavigationBar
import com.jorotayo.fl_datatracker.screens.homeScreen.components.SimpleIconButton

@Preview
@Composable
fun PreviewPageTemplate() {
    PageTemplate(
        navController = rememberNavController()
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PageTemplate(
    navController: NavController,
) {

    val scaffoldState = rememberScaffoldState()

    val bottomNavigationItems = listOf(
        Screen.DataFieldsScreen,
        Screen.HomeScreen,
        Screen.DataEntry
    )

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            scaffoldState.snackbarHostState
        },
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.1f)
                    .background(MaterialTheme.colors.primary)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    Arrangement.SpaceBetween,
                ) {
                    SimpleIconButton(
                        icon = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colors.surface,
                        modifier = Modifier
                            .weight(1f),
                        onClick = { navController.navigateUp() }
                    )
                    Text(
                        modifier = Modifier
                            .weight(10f),
                        text = "Page Title",
                        color = MaterialTheme.colors.background,
                        style = MaterialTheme.typography.h5.also { FontWeight.Bold },
                        textAlign = TextAlign.Center
                    )
                    SimpleIconButton(
                        icon = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .weight(1f),
                        onClick = {}
                    )
                }
            }
        },
        bottomBar = {
            BottomNavigationBar(navController, bottomNavigationItems)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colors.primary)

        ) {
            DefaultSnackbar(
                modifier = Modifier
                    .align(Alignment.Center),
                snackbarHostState = scaffoldState.snackbarHostState,
                onDismiss = {
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                    if (scaffoldState.snackbarHostState.currentSnackbarData?.actionLabel?.contains("Restore") == true) {
//                            viewModel.onDataEvent(DataFieldEvent.RestoreDeletedField)
                        // TODO: default snackbar to show on top
                    }
                }
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                    .background(MaterialTheme.colors.background)
            ) {


            }
        }
    }
}