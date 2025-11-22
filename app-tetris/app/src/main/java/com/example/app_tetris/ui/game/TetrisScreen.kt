package com.example.app_tetris.ui.game

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_tetris.ui.theme.NeonAccentPrimary
import com.example.app_tetris.ui.theme.NeonAccentSecondary
import com.example.app_tetris.ui.theme.NeonBackground
import com.example.app_tetris.ui.theme.NeonBoardBackground
import com.example.app_tetris.ui.theme.NeonButtonGreen
import com.example.app_tetris.ui.theme.NeonTextPrimary
import com.example.app_tetris.ui.theme.NeonTextSecondary
import com.example.app_tetris.viewmodel.TetrisViewModel

@Composable
fun TetrisScreen(
    viewModel: TetrisViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val gameState by viewModel.gameState.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        NeonBackground,
                        Color(0xFF050510),
                        Color(0xFF0A0A20),
                        NeonBackground
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Neon Title
            Text(
                text = "TETRIS",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = NeonAccentPrimary,
                letterSpacing = 8.sp,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .drawBehind {
                        // Title glow effect
                        for (i in 3 downTo 1) {
                            drawRoundRect(
                                color = NeonAccentPrimary.copy(alpha = 0.15f * i),
                                cornerRadius = CornerRadius(8f, 8f),
                                topLeft = Offset(-16f - i * 4, -8f - i * 2),
                                size = Size(size.width + 32f + i * 8, size.height + 16f + i * 4)
                            )
                        }
                    }
            )

            // Game area (board + side panel)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Game board
                GameBoard(
                    gameState = gameState,
                    modifier = Modifier.weight(1f, fill = false)
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Side panel
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    NextPiecePreview(nextPiece = gameState.nextPiece)

                    ScorePanel(
                        score = gameState.score,
                        level = gameState.level,
                        linesCleared = gameState.linesCleared
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Controls
            GameControls(
                isPaused = gameState.isPaused,
                isGameOver = gameState.isGameOver,
                onMoveLeft = viewModel::moveLeft,
                onMoveRight = viewModel::moveRight,
                onMoveDown = viewModel::moveDown,
                onRotate = viewModel::rotate,
                onHardDrop = viewModel::hardDrop,
                onPause = viewModel::pauseGame,
                onReset = viewModel::resetGame
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Pause overlay with neon effect
        if (gameState.isPaused && !gameState.isGameOver) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.8f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "PAUSED",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = NeonAccentPrimary,
                    letterSpacing = 8.sp,
                    modifier = Modifier.drawBehind {
                        // Pause text glow
                        for (i in 5 downTo 1) {
                            drawRoundRect(
                                color = NeonAccentPrimary.copy(alpha = 0.1f * i),
                                cornerRadius = CornerRadius(8f, 8f),
                                topLeft = Offset(-20f - i * 4, -10f - i * 2),
                                size = Size(size.width + 40f + i * 8, size.height + 20f + i * 4)
                            )
                        }
                    }
                )
            }
        }

        // Game Over dialog with neon style
        if (gameState.isGameOver) {
            AlertDialog(
                onDismissRequest = { },
                title = {
                    Text(
                        text = "GAME OVER",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = NeonAccentSecondary,
                        fontSize = 24.sp,
                        letterSpacing = 4.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                text = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        NeonStatItem("Final Score", gameState.score.toString(), NeonAccentPrimary)
                        NeonStatItem("Level", gameState.level.toString(), NeonButtonGreen)
                        NeonStatItem("Lines", gameState.linesCleared.toString(), NeonAccentSecondary)
                    }
                },
                confirmButton = {
                    Box(
                        modifier = Modifier
                            .drawBehind {
                                // Button glow
                                for (i in 3 downTo 1) {
                                    drawRoundRect(
                                        color = NeonButtonGreen.copy(alpha = 0.2f * i),
                                        cornerRadius = CornerRadius(12f + i * 2, 12f + i * 2),
                                        topLeft = Offset(-i * 2f, -i * 2f),
                                        size = Size(size.width + i * 4f, size.height + i * 4f)
                                    )
                                }
                                drawRoundRect(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            NeonButtonGreen,
                                            NeonButtonGreen.copy(alpha = 0.7f)
                                        )
                                    ),
                                    cornerRadius = CornerRadius(12f, 12f)
                                )
                                drawRoundRect(
                                    color = Color.White.copy(alpha = 0.3f),
                                    cornerRadius = CornerRadius(12f, 12f),
                                    style = Stroke(width = 1f)
                                )
                            }
                            .padding(horizontal = 24.dp, vertical = 12.dp)
                            .clickable { viewModel.resetGame() }
                    ) {
                        Text(
                            text = "PLAY AGAIN",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                },
                containerColor = NeonBoardBackground,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.drawBehind {
                    // Dialog glow
                    for (i in 4 downTo 1) {
                        drawRoundRect(
                            color = NeonAccentSecondary.copy(alpha = 0.05f * i),
                            cornerRadius = CornerRadius(16f + i * 2, 16f + i * 2),
                            topLeft = Offset(-i * 3f, -i * 3f),
                            size = Size(size.width + i * 6f, size.height + i * 6f)
                        )
                    }
                }
            )
        }
    }
}

@Composable
private fun NeonStatItem(
    label: String,
    value: String,
    color: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = NeonTextSecondary,
            fontSize = 14.sp
        )
        Text(
            text = value,
            color = color,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.drawBehind {
                drawRoundRect(
                    color = color.copy(alpha = 0.2f),
                    cornerRadius = CornerRadius(4f, 4f),
                    topLeft = Offset(-4f, -2f),
                    size = Size(size.width + 8f, size.height + 4f)
                )
            }
        )
    }
}
