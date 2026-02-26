package com.jorotayo.fl_datatracker.ui.components.loading

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerThemeNew

// ─────────────────────────────────────────────────────────────────────────────
// Loading Screen
// All colours sourced from MaterialTheme.colorScheme so they automatically
// respond to your existing FL_DatatrackerThemeNew light / dark setup.
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Full-screen loading overlay.
 *
 * Usage:
 * ```
 * if (isLoading) {
 *     LoadingScreen(message = "Fetching your data")
 * }
 * ```
 *
 * @param message   Descriptive text shown below the spinner.
 */
@Composable
fun LoadingScreen(
    message: String = "Loading"
) {
    val colorScheme = MaterialTheme.colorScheme
    val darkTheme = isSystemInDarkTheme()

    // ── Entrance fade-in ──────────────────────────────────────────────────
    val enterAlpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(600, easing = FastOutSlowInEasing),
        label = "enterAlpha"
    )

    // ── Slow pulse on the spinner ─────────────────────────────────────────
    val pulseScale by rememberInfiniteTransition(label = "pulse").animateFloat(
        initialValue = 0.96f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    // ── Derived colours from your theme ───────────────────────────────────
    // Light:  primary = deep burgundy #8B1A1A, tertiary = darker #6D1313
    // Dark:   primary = light coral  #FFB4AB, tertiary = soft pink #EFB8C8
    val spinnerArc = colorScheme.primary
    val spinnerAccent = colorScheme.tertiary
    val spinnerTrack = colorScheme.onBackground.copy(alpha = 0.08f)
    val textColor = colorScheme.onBackground
    val dotColor = colorScheme.primary

    Box(
        modifier = Modifier
            .fillMaxSize()
            .alpha(enterAlpha)
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        colorScheme.surface, // lighter centre
                        colorScheme.background // deeper edges
                    ),
                    center = Offset(0.5f, 0.4f),
                    radius = 1400f
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // ── Decorative blobs using theme primaries ────────────────────────
        DecorativeBlobs(
            primaryColor = colorScheme.primary,
            secondaryColor = colorScheme.secondary,
            tertiaryColor = colorScheme.tertiary,
            darkTheme = darkTheme
        )

        // ── Central content ───────────────────────────────────────────────
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            LoadingSpinner(
                modifier = Modifier
                    .size(80.dp)
                    .scale(pulseScale),
                size = 80.dp,
                trackColor = spinnerTrack,
                arcColor = spinnerArc,
                accentArcColor = spinnerAccent,
                strokeWidth = 5.dp
            )

            AnimatedLoadingText(
                text = message,
                textColor = textColor,
                dotColor = dotColor,
                dotSize = 7.dp
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Decorative blobs — pulled from theme colours, no hardcoded values
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun DecorativeBlobs(
    primaryColor: Color,
    secondaryColor: Color,
    tertiaryColor: Color,
    darkTheme: Boolean
) {
    // Dark mode blobs are slightly stronger to pop against the deep surface
    val blobAlpha = if (darkTheme) 0.22f else 0.13f

    Box(modifier = Modifier.fillMaxSize()) {
        // Top-left — primary (deep burgundy / light coral)
        Box(
            modifier = Modifier
                .size(300.dp)
                .offset(x = (-90).dp, y = (-70).dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            primaryColor.copy(alpha = blobAlpha),
                            Color.Transparent
                        )
                    )
                )
                .align(Alignment.TopStart)
        )

        // Bottom-right — secondary (brighter red / salmon)
        Box(
            modifier = Modifier
                .size(260.dp)
                .offset(x = 50.dp, y = 70.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            secondaryColor.copy(alpha = blobAlpha),
                            Color.Transparent
                        )
                    )
                )
                .align(Alignment.BottomEnd)
        )

        // Centre-left — tertiary (darker burgundy / soft pink), subtle
        Box(
            modifier = Modifier
                .size(180.dp)
                .offset(x = (-50).dp, y = 0.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            tertiaryColor.copy(alpha = blobAlpha * 0.6f),
                            Color.Transparent
                        )
                    )
                )
                .align(Alignment.CenterStart)
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Previews — wrapped in your real theme so colours are accurate in Android Studio
// ─────────────────────────────────────────────────────────────────────────────

@Preview(name = "Loading — Dark", showBackground = true)
@Composable
private fun LoadingScreenDarkPreview() {
    FL_DatatrackerThemeNew(darkTheme = true) {
        LoadingScreen(message = "Loading")
    }
}

@Preview(name = "Loading — Light", showBackground = true)
@Composable
private fun LoadingScreenLightPreview() {
    FL_DatatrackerThemeNew(darkTheme = false) {
        LoadingScreen(message = "Loading")
    }
}

@Preview(name = "Spinner only — Dark", showBackground = true)
@Composable
private fun SpinnerPreview() {
    FL_DatatrackerThemeNew(darkTheme = true) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            LoadingSpinner(
                size = 72.dp,
                trackColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.08f),
                arcColor = MaterialTheme.colorScheme.primary,
                accentArcColor = MaterialTheme.colorScheme.tertiary,
                strokeWidth = 5.dp
            )
        }
    }
}
