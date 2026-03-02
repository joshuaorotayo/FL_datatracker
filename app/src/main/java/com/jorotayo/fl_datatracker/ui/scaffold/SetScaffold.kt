package com.jorotayo.fl_datatracker.ui.scaffold

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect

/**
 * Call this at the top of any screen to configure the shared scaffold.
 * The scaffoldViewModel MUST be the Activity-scoped instance passed down
 * from MainActivity — do NOT resolve it via hiltViewModel() here, as that
 * would scope it to the nav back stack entry and produce a different instance
 * than the one MainActivity observes.
 */
@Composable
fun SetScaffold(
    title: @Composable () -> Unit = {},
    navigationIcon: (@Composable () -> Unit)? = null,
    actions: (@Composable () -> Unit)? = null,
    fab: (@Composable () -> Unit)? = null,
    showBottomBar: Boolean = true
) {

    val scaffoldController = LocalScaffoldController.current

    // Update when values change
    LaunchedEffect(title, navigationIcon, actions, fab, showBottomBar) {
        scaffoldController.update(
            AppScaffoldState(
                title = title,
                navigationIcon = navigationIcon,
                actions = actions,
                fab = fab,
                showBottomBar = showBottomBar
            )
        )
    }

    // Reset only when screen leaves composition
    DisposableEffect(Unit) {
        onDispose {
            scaffoldController.reset()
        }
    }
}