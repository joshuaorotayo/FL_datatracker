package com.jorotayo.fl_datatracker.ui.components.loading

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerThemeNew
import com.jorotayo.fl_datatracker.ui.util.Dimensions.spacingMedium

// =============================================================================
// PREVIEW
// =============================================================================

@DefaultPreviews
@Composable
fun PreviewLoadingSpinner(modifier: Modifier = Modifier) {
    FL_DatatrackerThemeNew {
        LoadingSpinner()
    }
}

/**
 * A reusable animated loading spinner with two concentric arcs rotating
 * in opposite directions.
 *
 * @param modifier  Standard modifier (use [Modifier.size] to control dimensions).
 * @param size      Diameter of the spinner.
 * @param trackColor Colour of the background ring.
 * @param arcColor  Colour of the animated arc.
 * @param strokeWidth Width of the stroke lines.
 */
@Composable
fun LoadingSpinner(
    modifier: Modifier = Modifier,
    size: Dp = 56.dp,
    trackColor: Color = Color.White.copy(alpha = 0.15f),
    arcColor: Color = Color(0xFF6C63FF),
    accentArcColor: Color = Color(0xFFFF6584),
    strokeWidth: Dp = 4.dp
) {
    // Outer arc — rotates clockwise
    val outerAngle by rememberInfiniteTransition(label = "outer").animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing)
        ),
        label = "outerRotation"
    )

    // Inner arc — rotates counter-clockwise, slightly faster
    val innerAngle by rememberInfiniteTransition(label = "inner").animateFloat(
        initialValue = 360f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = LinearEasing)
        ),
        label = "innerRotation"
    )

    // Sweep pulse
    val sweepAnim by rememberInfiniteTransition(label = "sweep").animateFloat(
        initialValue = 60f,
        targetValue = 280f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "sweep"
    )

    Canvas(modifier = modifier
        .padding(spacingMedium)
        .size(size)) {
        val strokePx = strokeWidth.toPx()
        val halfStroke = strokePx / 2f

        // — Outer track —
        drawArc(
            color = trackColor,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = Offset(halfStroke, halfStroke),
            size = Size(this.size.width - strokePx, this.size.height - strokePx),
            style = Stroke(width = strokePx, cap = StrokeCap.Round)
        )

        // — Outer animated arc —
        drawArc(
            color = arcColor,
            startAngle = outerAngle,
            sweepAngle = sweepAnim,
            useCenter = false,
            topLeft = Offset(halfStroke, halfStroke),
            size = Size(this.size.width - strokePx, this.size.height - strokePx),
            style = Stroke(width = strokePx, cap = StrokeCap.Round)
        )

        // — Inner track —
        val innerInset = strokePx * 2.5f
        drawArc(
            color = trackColor,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = Offset(innerInset + halfStroke, innerInset + halfStroke),
            size = Size(
                this.size.width - innerInset * 2 - strokePx,
                this.size.height - innerInset * 2 - strokePx
            ),
            style = Stroke(width = strokePx * 0.75f, cap = StrokeCap.Round)
        )

        // — Inner animated arc (opposite direction) —
        drawArc(
            color = accentArcColor,
            startAngle = innerAngle,
            sweepAngle = 120f,
            useCenter = false,
            topLeft = Offset(innerInset + halfStroke, innerInset + halfStroke),
            size = Size(
                this.size.width - innerInset * 2 - strokePx,
                this.size.height - innerInset * 2 - strokePx
            ),
            style = Stroke(width = strokePx * 0.75f, cap = StrokeCap.Round)
        )
    }
}
