package com.example.app_tetris.ui.game

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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
            Button(
                onClick = onPause,
                enabled = !isGameOver,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50)
                )
            ) {
                Icon(
                    imageVector = if (isPaused) Icons.Default.PlayArrow else Icons.Default.Pause,
                    contentDescription = if (isPaused) "Play" else "Pause"
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = onReset,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF44336)
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Reset"
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Movement controls
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Left button
            ControlButton(
                onClick = onMoveLeft,
                enabled = !isPaused && !isGameOver
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Move Left",
                    modifier = Modifier.size(32.dp)
                )
            }

            // Down button
            ControlButton(
                onClick = onMoveDown,
                enabled = !isPaused && !isGameOver
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Move Down",
                    modifier = Modifier.size(32.dp)
                )
            }

            // Right button
            ControlButton(
                onClick = onMoveRight,
                enabled = !isPaused && !isGameOver
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Move Right",
                    modifier = Modifier.size(32.dp)
                )
            }

            // Rotate button
            ControlButton(
                onClick = onRotate,
                enabled = !isPaused && !isGameOver,
                backgroundColor = Color(0xFF9C27B0)
            ) {
                Icon(
                    imageVector = Icons.Default.RotateRight,
                    contentDescription = "Rotate",
                    modifier = Modifier.size(32.dp)
                )
            }

            // Hard drop button
            ControlButton(
                onClick = onHardDrop,
                enabled = !isPaused && !isGameOver,
                backgroundColor = Color(0xFFFF9800)
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardDoubleArrowDown,
                    contentDescription = "Hard Drop",
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Composable
private fun ControlButton(
    onClick: () -> Unit,
    enabled: Boolean,
    backgroundColor: Color = Color(0xFF2196F3),
    content: @Composable () -> Unit
) {
    IconButton(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.size(56.dp),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = if (enabled) backgroundColor else Color.Gray,
            contentColor = Color.White,
            disabledContainerColor = Color.DarkGray,
            disabledContentColor = Color.Gray
        )
    ) {
        content()
    }
}
