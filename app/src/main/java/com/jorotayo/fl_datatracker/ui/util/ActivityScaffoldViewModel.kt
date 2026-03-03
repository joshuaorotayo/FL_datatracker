package com.jorotayo.fl_datatracker.ui.util

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.jorotayo.fl_datatracker.ui.scaffold.ScaffoldViewModel

@Composable
fun activityScaffoldViewModel(): ScaffoldViewModel {
    val activity = LocalContext.current as ComponentActivity
    return activity.viewModels<ScaffoldViewModel>().value
}