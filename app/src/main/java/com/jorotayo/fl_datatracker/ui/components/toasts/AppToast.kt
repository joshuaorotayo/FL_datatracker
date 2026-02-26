package com.jorotayo.fl_datatracker.ui.components.toasts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerThemeNew
import com.jorotayo.fl_datatracker.ui.util.Dimensions.iconSmall
import com.jorotayo.fl_datatracker.ui.util.Dimensions.spacingMedium
import com.jorotayo.fl_datatracker.ui.util.Dimensions.spacingSmall
import com.jorotayo.fl_datatracker.ui.util.Dimensions.spacingXSmall
import com.jorotayo.fl_datatracker.ui.util.Dimensions.spacingXXSmall
import kotlinx.coroutines.delay

// =============================================================================
// APP TOAST
// =============================================================================

/**
 * Card-style toast that slides up from the bottom of the screen.
 *
 * Place inside a [Box] wrapping your screen content:
 *
 * ```
 * Box(modifier = Modifier.fillMaxSize()) {
 *     YourScreenContent()
 *     AppToast(
 *         data = state.toast,
 *         onDismiss = { viewModel.onEvent(MyEvent.DismissToast) }
 *     )
 * }
 * ```
 */
@Composable
fun AppToast(
    data: AppToastData?,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(data) {
        if (data?.durationMs != null) {
            delay(data.durationMs)
            onDismiss()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .padding(spacingMedium),
        contentAlignment = Alignment.BottomCenter
    ) {
        AnimatedVisibility(
            visible = data != null,
            enter = slideInVertically(tween(300)) { it } + fadeIn(tween(300)),
            exit = slideOutVertically(tween(250)) { it } + fadeOut(tween(200))
        ) {
            data?.let { ToastCard(data = it, onDismiss = onDismiss) }
        }
    }
}

// =============================================================================
// TOAST CARD
// =============================================================================

@Composable
private fun ToastCard(
    data: AppToastData,
    onDismiss: () -> Unit
) {
    val colours = toastColours(data.mode)

    // Resolve icon tints — fall back to the mode's content colour if not overridden
    val leadingIconTint = data.leadingIcon?.tint ?: colours.content
    val trailingIconTint = data.trailingIcon?.tint ?: colours.content

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .then(
                if (data.onAction != null) Modifier.clickable { data.onAction.invoke() }
                else Modifier
            ),
        colors = CardDefaults.cardColors(containerColor = colours.container),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacingMedium, vertical = spacingSmall),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spacingXSmall)
        ) {
            // ── Leading icon — custom or default mode icon ────────────────────
            val leadingVector = data.leadingIcon?.icon ?: colours.icon
            Icon(
                imageVector = leadingVector,
                contentDescription = data.leadingIcon?.contentDescription,
                tint = leadingIconTint,
                modifier = Modifier.size(iconSmall)
            )

            // ── Message + optional action label ───────────────────────────────
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = data.message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colours.content
                )
                if (data.actionLabel != null && data.onAction != null) {
                    Spacer(modifier = Modifier.size(spacingXXSmall))
                    Text(
                        text = data.actionLabel,
                        style = MaterialTheme.typography.labelMedium,
                        color = colours.action
                    )
                }
            }

            // ── Trailing icon — optional custom icon ──────────────────────────
            if (data.trailingIcon != null) {
                Icon(
                    imageVector = data.trailingIcon.icon,
                    contentDescription = data.trailingIcon.contentDescription,
                    tint = trailingIconTint,
                    modifier = Modifier.size(iconSmall)
                )
            }

            // ── Dismiss button ────────────────────────────────────────────────
            IconButton(
                onClick = onDismiss,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Dismiss",
                    tint = colours.content.copy(alpha = 0.7f),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

// =============================================================================
// COLOUR SCHEME PER MODE
// =============================================================================

private data class ToastColours(
    val container: Color,
    val content: Color,
    val action: Color,
    val icon: ImageVector
)

@Composable
private fun toastColours(mode: ToastMode): ToastColours = when (mode) {
    ToastMode.INFO -> ToastColours(
        container = MaterialTheme.colorScheme.secondaryContainer,
        content = MaterialTheme.colorScheme.onSecondaryContainer,
        action = MaterialTheme.colorScheme.secondary,
        icon = Icons.Default.CheckCircle
    )

    ToastMode.WARNING -> ToastColours(
        container = MaterialTheme.colorScheme.tertiaryContainer,
        content = MaterialTheme.colorScheme.onTertiaryContainer,
        action = MaterialTheme.colorScheme.tertiary,
        icon = Icons.Default.Warning
    )

    ToastMode.ERROR -> ToastColours(
        container = MaterialTheme.colorScheme.errorContainer,
        content = MaterialTheme.colorScheme.onErrorContainer,
        action = MaterialTheme.colorScheme.error,
        icon = Icons.Default.Error
    )
}

// =============================================================================
// PREVIEWS
// =============================================================================

@DefaultPreviews
@Composable
fun PreviewAppToasts() {
    FL_DatatrackerThemeNew {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(spacingMedium),
                verticalArrangement = Arrangement.spacedBy(spacingSmall),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Info — default icon
                ToastCard(
                    data = AppToastData(
                        message = "Default preset loaded successfully.",
                        mode = ToastMode.INFO
                    ),
                    onDismiss = {}
                )

                // Warning — custom trailing icon, default tint
                ToastCard(
                    data = AppToastData(
                        message = "This preset has no active fields.",
                        mode = ToastMode.WARNING,
                        actionLabel = "Add a field →",
                        onAction = {},
                        trailingIcon = ToastIcon(
                            icon = Icons.Default.NavigateNext
                        )
                    ),
                    onDismiss = {}
                )

                // Error — custom leading icon with custom tint,
                //         custom trailing icon with mode-default tint
                ToastCard(
                    data = AppToastData(
                        message = "A field named \"Score\" already exists.",
                        mode = ToastMode.ERROR,
                        actionLabel = "Rename instead →",
                        onAction = {},
                        leadingIcon = ToastIcon(
                            icon = Icons.Default.Error,
                            tint = null   // uses mode content colour
                        ),
                        trailingIcon = ToastIcon(
                            icon = Icons.Default.Edit,
                            tint = null   // uses mode content colour
                        )
                    ),
                    onDismiss = {}
                )

                // Info — both icons with explicit custom tints
                ToastCard(
                    data = AppToastData(
                        message = "Field saved to Custom preset.",
                        mode = ToastMode.INFO,
                        leadingIcon = ToastIcon(
                            icon = Icons.Default.CheckCircle,
                            tint = Color(0xFF2E7D32)   // custom green
                        ),
                        trailingIcon = ToastIcon(
                            icon = Icons.Default.NavigateNext,
                            tint = Color(0xFF1565C0)   // custom blue
                        )
                    ),
                    onDismiss = {}
                )
            }
        }
    }
}