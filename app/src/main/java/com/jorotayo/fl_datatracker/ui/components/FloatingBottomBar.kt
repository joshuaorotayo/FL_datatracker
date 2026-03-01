package com.jorotayo.fl_datatracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jorotayo.fl_datatracker.domain.usecase.GetFieldsForPresetUseCase
import com.jorotayo.fl_datatracker.domain.usecase.GetSelectedPresetUseCase
import com.jorotayo.fl_datatracker.navigation.Screen
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.components.toasts.AppToast
import com.jorotayo.fl_datatracker.ui.components.toasts.AppToastData
import com.jorotayo.fl_datatracker.ui.components.toasts.ToastMode
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerThemeNew
import kotlinx.coroutines.launch

@DefaultPreviews
@Composable
fun PreviewFloatingBottomBar() {
    FL_DatatrackerThemeNew {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.BottomCenter
        ) {
            FloatingBottomBar(navController = rememberNavController())
        }
    }
}

@Composable
fun FloatingBottomBar(
    navController: NavHostController,
    items: List<Screen> = listOf(
        Screen.DataForm,
        Screen.Home,
        Screen.DataEntry,
        Screen.Settings
    ),
    getSelectedPreset: GetSelectedPresetUseCase = hiltViewModel<BottomBarViewModel>().getSelectedPreset,
    getFieldsForPreset: GetFieldsForPresetUseCase = hiltViewModel<BottomBarViewModel>().getFieldsForPreset
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val selectedIndex = items.indexOfFirst { screen ->
        currentDestination?.hierarchy?.any { it.route == screen.route } == true
    }.coerceAtLeast(0)

    val itemOffsets = remember { mutableListOf<Float>().apply { repeat(items.size) { add(0f) } } }
    val itemWidths = remember { mutableListOf<Float>().apply { repeat(items.size) { add(0f) } } }
    val density = LocalDensity.current

    val targetOffset = if (itemOffsets.isNotEmpty() && itemOffsets[selectedIndex] != 0f) {
        with(density) { itemOffsets[selectedIndex].toDp() }
    } else 0.dp

    val targetWidth = if (itemWidths.isNotEmpty() && itemWidths[selectedIndex] != 0f) {
        with(density) { itemWidths[selectedIndex].toDp() }
    } else 64.dp

    val animatedBubbleOffset: Dp = targetOffset
    val animatedBubbleWidth: Dp = targetWidth

    // Local toast state — only used when the DataEntry guard fires from the
    // bottom nav. Each screen still manages its own toasts independently.
    var localToast by remember { mutableStateOf<AppToastData?>(null) }
    val scope = rememberCoroutineScope()

    // FloatingBottomBar is always in the composition when visible, so this
    // Box wraps both the bar and the toast so the toast is always rendered.
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .navigationBarsPadding()
    ) {
        // ── Bottom bar pill ───────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .align(Alignment.BottomCenter)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 12.dp,
                        shape = RoundedCornerShape(28.dp),
                        ambientColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                        spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                    )
                    .clip(RoundedCornerShape(28.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(vertical = 8.dp, horizontal = 8.dp)
            ) {
                if (animatedBubbleOffset > 0.dp || selectedIndex == 0) {
                    Box(
                        modifier = Modifier
                            .offset(x = animatedBubbleOffset)
                            .width(animatedBubbleWidth)
                            .height(60.dp)
                            .clip(RoundedCornerShape(22.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items.forEachIndexed { index, screen ->
                        val isSelected = index == selectedIndex
                        FloatingNavItem(
                            screen = screen,
                            isSelected = isSelected,
                            onPositioned = { offset, width ->
                                itemOffsets[index] = offset
                                itemWidths[index] = width
                            },
                            onClick = {
                                if (screen is Screen.DataEntry) {
                                    // Guard: check preset exists and has active fields
                                    // before navigating. Show a local toast here because
                                    // FloatingBottomBar is always composed — unlike
                                    // individual screens which may not be active.
                                    scope.launch {
                                        val preset = getSelectedPreset()
                                        if (preset == null) {
                                            localToast = AppToastData(
                                                message = "No preset selected. Please set one up in Data Forms first.",
                                                mode = ToastMode.ERROR
                                            )
                                            return@launch
                                        }
                                        val activeFields = getFieldsForPreset(preset.presetId)
                                            .filter { it.isActive }
                                        if (activeFields.isEmpty()) {
                                            localToast = AppToastData(
                                                message = "\"${preset.presetName}\" has no fields. Add fields in Data Forms before creating a record.",
                                                mode = ToastMode.ERROR
                                            )
                                            return@launch
                                        }
                                        // Guards passed — navigate normally
                                        navController.navigate(Screen.DataEntry.newRoute()) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                } else {
                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }

        // ── Local toast — rendered above the bar, always visible ─────────────
        AppToast(
            data = localToast,
            onDismiss = { localToast = null },
            // Offset upward so it clears the bottom bar pill
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

// =============================================================================
// BOTTOM BAR VIEWMODEL — thin holder so use cases can be injected via Hilt
// =============================================================================

@dagger.hilt.android.lifecycle.HiltViewModel
class BottomBarViewModel @javax.inject.Inject constructor(
    val getSelectedPreset: GetSelectedPresetUseCase,
    val getFieldsForPreset: GetFieldsForPresetUseCase
) : androidx.lifecycle.ViewModel()

// =============================================================================
// INDIVIDUAL NAV ITEM
// =============================================================================

@Composable
private fun FloatingNavItem(
    screen: Screen,
    isSelected: Boolean,
    onPositioned: (offsetX: Float, width: Float) -> Unit,
    onClick: () -> Unit
) {
    val iconAlpha = if (isSelected) 1f else 0.45f
    val textAlpha = if (isSelected) 1f else 0.55f

    Column(
        modifier = Modifier
            .onGloballyPositioned { coords ->
                onPositioned(coords.positionInParent().x, coords.size.width.toFloat())
            }
            .clip(RoundedCornerShape(22.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        screen.icon?.let { icon ->
            Icon(
                imageVector = icon,
                contentDescription = screen.title,
                modifier = Modifier.size(22.dp),
                tint = MaterialTheme.colorScheme.primary.copy(alpha = iconAlpha)
            )
        }
        screen.title?.let { title ->
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                ),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = textAlpha)
            )
        }
    }
}