package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Bubble Loading Animation (مؤشر تحميل الفقاعات الدائري النابض).
 * Features 12 orbital breathing bubbles with dynamic scale, glowing alpha, and smooth 60/120Hz rotation.
 */
@Composable
fun SmoothProgressIndicator(
    modifier: Modifier = Modifier.size(36.dp),
    color: Color = MaterialTheme.colorScheme.primary,
    trackColor: Color = color.copy(alpha = 0.16f),
    strokeWidth: Dp = 3.dp,
    durationMillis: Int = 1200,
    dotCount: Int = 12
) {
    val transition = rememberInfiniteTransition(label = "bubble_loader_transition")

    val phase by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "bubble_wave_phase"
    )

    val rotationAngle by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMillis * 4, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "bubble_orbit_rotation"
    )

    Canvas(modifier = modifier.progressSemantics()) {
        val minDim = size.minDimension
        if (minDim <= 0f) return@Canvas

        val center = Offset(size.width / 2f, size.height / 2f)
        val maxDotRadius = (minDim * 0.135f).coerceAtLeast(1.8f)
        val minDotRadius = maxDotRadius * 0.32f
        val orbitRadius = (minDim / 2f) - maxDotRadius

        val rotationRad = Math.toRadians(rotationAngle.toDouble())

        for (i in 0 until dotCount) {
            val baseAngle = (2.0 * PI * i / dotCount) + rotationRad
            val dotProgress = ((phase - (i.toFloat() / dotCount)) + 1f) % 1f
            val pulse = (sin(dotProgress * 2.0 * PI - PI / 2).toFloat() + 1f) / 2f

            val currentRadius = minDotRadius + (maxDotRadius - minDotRadius) * pulse
            val currentAlpha = (0.22f + 0.78f * pulse).coerceIn(0f, 1f)

            val x = center.x + (orbitRadius * cos(baseAngle)).toFloat()
            val y = center.y + (orbitRadius * sin(baseAngle)).toFloat()

            drawCircle(
                color = color.copy(alpha = currentAlpha * 0.35f),
                radius = currentRadius * 1.45f,
                center = Offset(x, y)
            )

            drawCircle(
                color = color.copy(alpha = currentAlpha),
                radius = currentRadius,
                center = Offset(x, y)
            )
        }
    }
}

/**
 * Horizontal Bubble Loading Animation (انيميشن الفقاعات الأفقي المتمدد والعائد).
 * Features a row of bubbles expanding in scale and retracting horizontally in a fluid wave.
 */
@Composable
fun HorizontalBubbleLoadingAnimation(
    modifier: Modifier = Modifier,
    bubbleColor: Color = MaterialTheme.colorScheme.primary,
    bubbleCount: Int = 4,
    bubbleSize: Dp = 13.dp,
    durationMillis: Int = 1100
) {
    val transition = rememberInfiniteTransition(label = "horizontal_bubbles_transition")

    // Continuous wave progression
    val phase by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMillis, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "h_bubbles_phase"
    )

    // Dynamic horizontal breathing expansion and retraction of spacing
    val expansionSpread by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMillis, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "h_bubbles_expansion"
    )

    val currentSpacing = 8.dp + (6.dp * expansionSpread)

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(currentSpacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until bubbleCount) {
            val dotProgress = ((phase - (i.toFloat() / bubbleCount)) + 1f) % 1f
            val pulse = (sin(dotProgress * 2.0 * PI - PI / 2).toFloat() + 1f) / 2f

            // Dynamic scale: expands from 0.48f to 1.35f and returns smoothly
            val scale = 0.48f + 0.87f * pulse
            val alpha = 0.35f + 0.65f * pulse

            Box(
                modifier = Modifier
                    .size(bubbleSize)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        this.alpha = alpha
                    }
                    .background(bubbleColor, CircleShape)
            )
        }
    }
}

/**
 * Bubble Loading Animation component for compatibility.
 */
@Composable
fun BubbleLoadingAnimation(
    modifier: Modifier = Modifier.size(68.dp),
    bubbleColor: Color = MaterialTheme.colorScheme.primary,
    dotCount: Int = 12,
    durationMillis: Int = 1300
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        SmoothProgressIndicator(
            modifier = Modifier.fillMaxSize(),
            color = bubbleColor,
            dotCount = dotCount,
            durationMillis = durationMillis
        )
    }
}
