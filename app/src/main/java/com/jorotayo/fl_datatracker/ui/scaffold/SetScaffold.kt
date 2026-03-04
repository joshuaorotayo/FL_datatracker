package com.jorotayo.fl_datatracker.ui.scaffold

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
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

    SideEffect {
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
}