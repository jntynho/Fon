package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.ui.components.HorizontalBubbleLoadingAnimation
import com.example.ui.theme.LocalAccentColor
import com.example.ui.theme.LocalVaultPalette

/**
 * Minimalist Resource Loading Screen (واجهة تحميل الموارد).
 * 1. Centered app icon cropped in a circular shape directly with NO background layer beneath it.
 * 2. Horizontal bubble loading animation directly beneath it where bubbles expand and return horizontally.
 * ONLY these two elements in the center of the screen!
 */
@Composable
fun ResourceLoadingScreen(
    statusText: String = "",
    modifier: Modifier = Modifier,
    onDismiss: (() -> Unit)? = null
) {
    val palette = LocalVaultPalette.current
    val accent = LocalAccentColor.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(palette.bg),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // 1. The application icon itself, cropped into a circle with NO background layer underneath
            Image(
                painter = painterResource(id = R.drawable.ic_app_icon_full),
                contentDescription = "App Icon",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(30.dp))

            // 2. Horizontal bubble loading animation where bubbles expand and return
            HorizontalBubbleLoadingAnimation(
                bubbleColor = accent,
                bubbleCount = 4,
                bubbleSize = 13.dp,
                durationMillis = 1100
            )
        }
    }
}
