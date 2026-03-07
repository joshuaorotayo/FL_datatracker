package com.jorotayo.fl_datatracker.ui.scaffold

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.jorotayo.fl_datatracker.ui.components.toasts.AppToastData

@Composable
fun SetScaffold(
    title: @Composable () -> Unit = {},
    navigationIcon: (@Composable () -> Unit)? = null,
    actions: (@Composable () -> Unit)? = null,
    fab: (@Composable () -> Unit)? = null,
    showBottomBar: Boolean = true,
    toast: AppToastData? = null
) {
    val scaffoldController = LocalScaffoldController.current

    // SideEffect skips execution if the composable doesn't recompose.
    // LaunchedEffect(toast) reruns specifically when toast changes.
    LaunchedEffect(title, navigationIcon, actions, fab, showBottomBar) {
        scaffoldController.update(
            AppScaffoldState(
                title = title,
                navigationIcon = navigationIcon,
                actions = actions,
                fab = fab,
                showBottomBar = showBottomBar,
                toast = toast
            )
        )
    }

    // Separate effect keyed on toast so it always fires on new toasts
    // independently of whether anything else recomposed
    LaunchedEffect(toast) {
        scaffoldController.updateToast(toast)
    }
}