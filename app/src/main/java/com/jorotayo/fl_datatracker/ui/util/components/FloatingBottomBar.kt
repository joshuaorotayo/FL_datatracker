package com.jorotayo.fl_datatracker.ui.util.components


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
import androidx.compose.runtime.remember
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
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jorotayo.fl_datatracker.navigation.Screen
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerThemeNew

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
    items: List<Screen> = listOf(
        Screen.DataForm,
        Screen.Home,
        Screen.DataEntry,
        Screen.Settings
    )
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
    } else {
        0.dp
    }

    val targetWidth = if (itemWidths.isNotEmpty() && itemWidths[selectedIndex] != 0f) {
        with(density) { itemWidths[selectedIndex].toDp() }
    } else {
        64.dp
    }

    val animatedBubbleOffset: Dp = targetOffset
    val animatedBubbleWidth: Dp = targetWidth

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 20.dp)
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
                            navController.navigate(screen.route) {
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
