package com.jorotayo.fl_datatracker

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jorotayo.fl_datatracker.navigation.MainNavGraph
import com.jorotayo.fl_datatracker.navigation.Screen
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerThemeNew

// =============================================================================
// NAV ITEM DEFINITION
// =============================================================================

data class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector,
    val description: String
)

val bottomNavItems = listOf(
    BottomNavItem("home", "Home", Icons.Default.Home, "Home screen"),
    BottomNavItem("dataForm", "Forms", Icons.Default.ViewList, "Data forms"),
    BottomNavItem("dataEntry", "Entry", Icons.Default.EditNote, "Data entry")
//    BottomNavItem("settings", "Settings", Icons.Default.Settings, "Settings")
)

// =============================================================================
// MAIN SCREEN
// =============================================================================

// Screens that should NOT show the bottom bar
private val screensWithoutBottomBar = listOf(
    Screen.Onboarding.route
)

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute !in screensWithoutBottomBar

    FL_DatatrackerThemeNew {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                if (showBottomBar) {
                    FloatingBottomBar(navController = navController)
                }
            }
        ) { paddingValues ->
            MainNavGraph(
                navController = navController,
                // Change to Screen.Onboarding.route if onboarding isn't complete
                startDestination = Screen.Home.route,
                modifier = Modifier
                    .padding(bottom = paddingValues.calculateBottomPadding())
                    .statusBarsPadding()
            )
        }
    }
}

// =============================================================================
// FLOATING BOTTOM BAR
// =============================================================================

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
    items: List<BottomNavItem> = bottomNavItems
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val selectedIndex = items.indexOfFirst { item ->
        currentDestination?.hierarchy?.any { it.route == item.route } == true
    }.coerceAtLeast(0)

    // Track x-offsets of each item so the bubble can slide between them
    val itemOffsets = remember { mutableListOf<Float>().apply { repeat(items.size) { add(0f) } } }
    val itemWidths = remember { mutableListOf<Float>().apply { repeat(items.size) { add(0f) } } }
    val density = LocalDensity.current

    // Animate bubble x position
    val targetOffset = if (itemOffsets.isNotEmpty() && itemOffsets[selectedIndex] != 0f)
        with(density) { itemOffsets[selectedIndex].toDp() }
    else 0.dp

    val targetWidth = if (itemWidths.isNotEmpty() && itemWidths[selectedIndex] != 0f)
        with(density) { itemWidths[selectedIndex].toDp() }
    else 64.dp

    val animatedBubbleOffset: Dp by animateDpAsState(
        targetValue = targetOffset,
        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing),
        label = "bubble_offset"
    )
    val animatedBubbleWidth: Dp by animateDpAsState(
        targetValue = targetWidth,
        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing),
        label = "bubble_width"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 20.dp)
    ) {
        // The floating pill container
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
            // Sliding bubble — sits behind the icons
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

            // Nav items row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEachIndexed { index, item ->
                    val isSelected = index == selectedIndex
                    FloatingNavItem(
                        item = item,
                        isSelected = isSelected,
                        onPositioned = { offset, width ->
                            itemOffsets[index] = offset
                            itemWidths[index] = width
                        },
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    }
}

// =============================================================================
// INDIVIDUAL NAV ITEM
// =============================================================================

@Composable
private fun FloatingNavItem(
    item: BottomNavItem,
    isSelected: Boolean,
    onPositioned: (offsetX: Float, width: Float) -> Unit,
    onClick: () -> Unit
) {
    val iconAlpha by animateFloatAsState(
        targetValue = if (isSelected) 1f else 0.45f,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
        label = "icon_alpha_${item.route}"
    )
    val textAlpha by animateFloatAsState(
        targetValue = if (isSelected) 1f else 0.55f,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
        label = "text_alpha_${item.route}"
    )

    Column(
        modifier = Modifier
            .onGloballyPositioned { coords ->
                onPositioned(
                    coords.positionInParent().x,
                    coords.size.width.toFloat()
                )
            }
            .clip(RoundedCornerShape(22.dp))
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick
            )
            .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.description,
            modifier = Modifier.size(22.dp),
            tint = MaterialTheme.colorScheme.primary.copy(alpha = iconAlpha)
        )
        Text(
            text = item.title,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
            ),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = textAlpha)
        )
    }
}

// =============================================================================
// HELPER
// =============================================================================

private fun NavHostController.isCurrentRoute(route: String): Boolean {
    val currentDestination = currentBackStackEntry?.destination
    return currentDestination?.hierarchy?.any { it.route == route } == true
}