package com.example.app_tetris.ui.game

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardDoubleArrowDown
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.RotateRight
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.app_tetris.ui.theme.NeonButtonBlue
import com.example.app_tetris.ui.theme.NeonButtonGreen
import com.example.app_tetris.ui.theme.NeonButtonOrange
import com.example.app_tetris.ui.theme.NeonButtonPurple
import com.example.app_tetris.ui.theme.NeonButtonRed

@Composable
fun GameControls(
    isPaused: Boolean,
    isGameOver: Boolean,
    onMoveLeft: () -> Unit,
    onMoveRight: () -> Unit,
    onMoveDown: () -> Unit,
    onRotate: () -> Unit,
    onHardDrop: () -> Unit,
    onPause: () -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Game control buttons (Pause/Play and Reset)
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            NeonButton(
                onClick = onPause,
                enabled = !isGameOver,
                color = NeonButtonGreen,
                icon = if (isPaused) Icons.Default.PlayArrow else Icons.Default.Pause,
                contentDescription = if (isPaused) "Play" else "Pause",
                size = 50.dp
            )

            Spacer(modifier = Modifier.width(24.dp))

            NeonButton(
                onClick = onReset,
                enabled = true,
                color = NeonButtonRed,
                icon = Icons.Default.Refresh,
                contentDescription = "Reset",
                size = 50.dp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Movement controls
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            NeonButton(
                onClick = onMoveLeft,
                enabled = !isPaused && !isGameOver,
                color = NeonButtonBlue,
                icon = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Move Left"
            )

            NeonButton(
                onClick = onMoveDown,
                enabled = !isPaused && !isGameOver,
                color = NeonButtonBlue,
                icon = Icons.Default.KeyboardArrowDown,
                contentDescription = "Move Down"
            )

            NeonButton(
                onClick = onMoveRight,
                enabled = !isPaused && !isGameOver,
                color = NeonButtonBlue,
                icon = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Move Right"
            )

            NeonButton(
                onClick = onRotate,
                enabled = !isPaused && !isGameOver,
                color = NeonButtonPurple,
                icon = Icons.Default.RotateRight,
                contentDescription = "Rotate"
            )

            NeonButton(
                onClick = onHardDrop,
                enabled = !isPaused && !isGameOver,
                color = NeonButtonOrange,
                icon = Icons.Default.KeyboardDoubleArrowDown,
                contentDescription = "Hard Drop"
            )
        }
    }
}

@Composable
private fun NeonButton(
    onClick: () -> Unit,
    enabled: Boolean,
    color: Color,
    icon: ImageVector,
    contentDescription: String,
    size: Dp = 56.dp
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val actualColor = if (enabled) color else Color.Gray.copy(alpha = 0.5f)
    val glowAlpha = if (isPressed && enabled) 0.6f else if (enabled) 0.3f else 0.1f

    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .drawBehind {
                // Outer glow
                if (enabled) {
                    for (i in 3 downTo 1) {
                        drawCircle(
                            color = actualColor.copy(alpha = glowAlpha * i / 3),
                            radius = this.size.minDimension / 2 + i * 4f
                        )
                    }
                }
                // Background gradient
                drawCircle(
                    brush = Brush.verticalGradient(
                        colors = if (enabled) listOf(
                            actualColor,
                            actualColor.copy(alpha = 0.7f)
                        ) else listOf(
                            Color.DarkGray,
                            Color.DarkGray.copy(alpha = 0.7f)
                        )
                    )
                )
                // Inner highlight
                drawCircle(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = if (isPressed) 0.1f else 0.3f),
                            Color.Transparent
                        ),
                        startY = 0f,
                        endY = this.size.height * 0.5f
                    ),
                    radius = this.size.minDimension / 2 - 2f
                )
                // Border
                drawCircle(
                    color = if (enabled) Color.White.copy(alpha = 0.5f) else Color.Gray.copy(alpha = 0.3f),
                    style = Stroke(width = 2f)
                )
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(size * 0.5f),
            tint = if (enabled) Color.White else Color.Gray
        )
    }
}
