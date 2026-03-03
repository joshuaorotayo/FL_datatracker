package com.jorotayo.fl_datatracker.ui.scaffold

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable

@Stable
class AppScaffoldState(
    val title: @Composable () -> Unit = {},
    val navigationIcon: (@Composable () -> Unit)? = null,
    val actions: (@Composable () -> Unit)? = null,
    val fab: (@Composable () -> Unit)? = null,
    val showBottomBar: Boolean = true
)