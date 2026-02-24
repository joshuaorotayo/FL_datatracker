package com.jorotayo.fl_datatracker.ui.util.components.loading

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Animated loading label — the text stays static while three dots
 * perform a staggered fade-bounce, simulating a Lottie-style animation
 * without requiring the Lottie dependency.
 *
 * If you have the Lottie library available, swap the [DotWave] composable
 * for a [LottieAnimation] call pointing at your own loading JSON file.
 */
@Composable
fun AnimatedLoadingText(
    text: String = "Loading",
    modifier: Modifier = Modifier,
    textColor: Color = Color.White,
    fontSize: TextUnit = 18.sp,
    dotSize: Dp = 6.dp,
    dotColor: Color = Color(0xFF6C63FF)
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = fontSize,
            fontWeight = FontWeight.Medium,
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.width(6.dp))

        DotWave(dotColor = dotColor, dotSize = dotSize)
    }
}

@Composable
private fun DotWave(
    dotColor: Color,
    dotSize: Dp,
    dotCount: Int = 3
) {
    val infiniteTransition = rememberInfiniteTransition(label = "dotWave")

    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(dotCount) { index ->
            val offsetY by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = -8f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 1000
                        0f at (index * 160) with FastOutSlowInEasing
                        -8f at (index * 160 + 200) with FastOutSlowInEasing
                        0f at (index * 160 + 400) with FastOutSlowInEasing
                        0f at 999
                    },
                    repeatMode = RepeatMode.Restart
                ),
                label = "dot_$index"
            )

            val alpha by infiniteTransition.animateFloat(
                initialValue = 0.3f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 1000
                        0.3f at (index * 160)
                        1f at (index * 160 + 200)
                        0.3f at (index * 160 + 400)
                        0.3f at 999
                    },
                    repeatMode = RepeatMode.Restart
                ),
                label = "alpha_$index"
            )

            Box(
                modifier = Modifier
                    .size(dotSize)
                    .offset(y = offsetY.dp)
                    .alpha(alpha)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawCircle(color = dotColor)
                }
            }
        }
    }
}

// Alias import for Canvas inside a Box
@Composable
private fun Canvas(modifier: Modifier, onDraw: DrawScope.() -> Unit) {
    Canvas(modifier = modifier, onDraw = onDraw)
}
