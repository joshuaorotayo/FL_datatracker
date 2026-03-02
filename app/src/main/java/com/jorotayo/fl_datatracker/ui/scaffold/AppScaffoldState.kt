package com.jorotayo.fl_datatracker.ui.scaffold

import androidx.compose.runtime.Composable

/**
 * Describes the full scaffold configuration for a single screen.
 *
 * @param showBottomBar  Set to false on screens where the bottom nav should
 *                       be hidden (e.g. DataEntry, Onboarding, detail screens).
 *                       Defaults to true.
 */
data class AppScaffoldState(
    val title: @Composable () -> Unit = {},
    val navigationIcon: (@Composable () -> Unit)? = null,
    val actions: (@Composable () -> Unit)? = null,
    val fab: (@Composable () -> Unit)? = null,
    val showBottomBar: Boolean = true
)