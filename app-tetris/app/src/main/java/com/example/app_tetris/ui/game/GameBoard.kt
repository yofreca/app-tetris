package com.example.app_tetris.ui.game

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.app_tetris.game.TetrisGameLogic
import com.example.app_tetris.model.GameState
import com.example.app_tetris.model.HardDropAnimation
import com.example.app_tetris.model.LineClearAnimation
import com.example.app_tetris.model.Tetromino
import com.example.app_tetris.ui.theme.NeonBoardBackground
import com.example.app_tetris.ui.theme.NeonBorderGlow
import com.example.app_tetris.ui.theme.NeonGridLine

@Composable
fun GameBoard(
    gameState: GameState,
    modifier: Modifier = Modifier
) {
    val aspectRatio = GameState.BOARD_WIDTH.toFloat() / GameState.BOARD_HEIGHT.toFloat()

    // Line clear animation
    var lineClearProgress by remember { mutableFloatStateOf(0f) }
    val animatedLineClearProgress by animateFloatAsState(
        targetValue = lineClearProgress,
        animationSpec = tween(durationMillis = 300),
        label = "lineClear"
    )

    LaunchedEffect(gameState.lineClearAnimation) {
        if (gameState.lineClearAnimation != null) {
            lineClearProgress = 1f
        } else {
            lineClearProgress = 0f
        }
    }

    // Hard drop animation
    var hardDropProgress by remember { mutableFloatStateOf(0f) }
    val animatedHardDropProgress by animateFloatAsState(
        targetValue = hardDropProgress,
        animationSpec = tween(durationMillis = 200),
        label = "hardDrop"
    )

    LaunchedEffect(gameState.hardDropAnimation) {
        if (gameState.hardDropAnimation != null) {
            hardDropProgress = 1f
        } else {
            hardDropProgress = 0f
        }
    }

    Box(
        modifier = modifier
            .fillMaxHeight(0.7f)
            .aspectRatio(aspectRatio)
    ) {
        // Outer glow border
        Canvas(
            modifier = Modifier
                .matchParentSize()
                .padding(4.dp)
        ) {
            // Draw outer glow
            for (i in 3 downTo 1) {
                drawRoundRect(
                    color = NeonBorderGlow.copy(alpha = 0.1f * i),
                    cornerRadius = CornerRadius(8f + i * 2, 8f + i * 2),
                    style = Stroke(width = (i * 3).toFloat()),
                    topLeft = Offset(-i * 3f, -i * 3f),
                    size = Size(size.width + i * 6f, size.height + i * 6f)
                )
            }
        }

        // Main board canvas
        Canvas(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            NeonBoardBackground,
                            Color(0xFF050515),
                            NeonBoardBackground
                        )
                    )
                )
                .padding(2.dp)
        ) {
            val cellWidth = size.width / GameState.BOARD_WIDTH
            val cellHeight = size.height / GameState.BOARD_HEIGHT

            // Draw grid lines with subtle glow
            for (i in 1 until GameState.BOARD_WIDTH) {
                drawLine(
                    color = NeonGridLine.copy(alpha = 0.3f),
                    start = Offset(i * cellWidth, 0f),
                    end = Offset(i * cellWidth, size.height),
                    strokeWidth = 1f
                )
            }
            for (i in 1 until GameState.BOARD_HEIGHT) {
                drawLine(
                    color = NeonGridLine.copy(alpha = 0.3f),
                    start = Offset(0f, i * cellHeight),
                    end = Offset(size.width, i * cellHeight),
                    strokeWidth = 1f
                )
            }

            // Draw placed pieces on board with neon effect
            for (row in 0 until GameState.BOARD_HEIGHT) {
                for (col in 0 until GameState.BOARD_WIDTH) {
                    gameState.board[row][col]?.let { color ->
                        val glowColor = Tetromino.getGlowColorForColor(color)
                        drawNeonCell(col, row, cellWidth, cellHeight, color, glowColor)
                    }
                }
            }

            // Draw line clear animation (flash effect)
            gameState.lineClearAnimation?.let { animation ->
                drawLineClearEffect(
                    animation = animation,
                    progress = animatedLineClearProgress,
                    cellWidth = cellWidth,
                    cellHeight = cellHeight
                )
            }

            // Draw hard drop animation (impact effect)
            gameState.hardDropAnimation?.let { animation ->
                drawHardDropEffect(
                    animation = animation,
                    progress = animatedHardDropProgress,
                    cellWidth = cellWidth,
                    cellHeight = cellHeight
                )
            }

            // Draw ghost piece with neon outline
            if (!gameState.isGameOver && !gameState.isPaused) {
                val ghostY = TetrisGameLogic.getGhostY(gameState)
                val shape = gameState.currentPiece.getShape(gameState.pieceRotation)
                val ghostColor = gameState.currentPiece.color.copy(alpha = 0.3f)

                for (row in shape.indices) {
                    for (col in shape[row].indices) {
                        if (shape[row][col] == 1) {
                            val boardX = gameState.pieceX + col
                            val boardY = ghostY + row
                            if (boardY >= 0 && boardY < GameState.BOARD_HEIGHT &&
                                boardX >= 0 && boardX < GameState.BOARD_WIDTH
                            ) {
                                drawGhostCell(boardX, boardY, cellWidth, cellHeight, ghostColor)
                            }
                        }
                    }
                }
            }

            // Draw current piece with full neon effect
            if (!gameState.isGameOver) {
                val shape = gameState.currentPiece.getShape(gameState.pieceRotation)
                for (row in shape.indices) {
                    for (col in shape[row].indices) {
                        if (shape[row][col] == 1) {
                            val boardX = gameState.pieceX + col
                            val boardY = gameState.pieceY + row
                            if (boardY >= 0 && boardY < GameState.BOARD_HEIGHT &&
                                boardX >= 0 && boardX < GameState.BOARD_WIDTH
                            ) {
                                drawNeonCell(
                                    boardX, boardY, cellWidth, cellHeight,
                                    gameState.currentPiece.color,
                                    gameState.currentPiece.glowColor
                                )
                            }
                        }
                    }
                }
            }

            // Draw neon border
            drawRoundRect(
                color = NeonBorderGlow,
                cornerRadius = CornerRadius(4f, 4f),
                style = Stroke(width = 2f)
            )
        }
    }
}

private fun DrawScope.drawLineClearEffect(
    animation: LineClearAnimation,
    progress: Float,
    cellWidth: Float,
    cellHeight: Float
) {
    // Flash effect for cleared rows
    val flashAlpha = if (progress < 0.5f) {
        progress * 2f // Fade in
    } else {
        (1f - progress) * 2f // Fade out
    }

    for (row in animation.clearedRows) {
        // White flash across the row
        drawRect(
            color = Color.White.copy(alpha = flashAlpha * 0.8f),
            topLeft = Offset(0f, row * cellHeight),
            size = Size(size.width, cellHeight)
        )

        // Cyan glow effect
        drawRect(
            color = Color(0xFF00FFFF).copy(alpha = flashAlpha * 0.5f),
            topLeft = Offset(0f, row * cellHeight - 4f),
            size = Size(size.width, cellHeight + 8f)
        )

        // Expanding horizontal lines
        val expandWidth = size.width * progress
        val centerX = size.width / 2
        drawLine(
            color = Color.White.copy(alpha = flashAlpha),
            start = Offset(centerX - expandWidth / 2, row * cellHeight + cellHeight / 2),
            end = Offset(centerX + expandWidth / 2, row * cellHeight + cellHeight / 2),
            strokeWidth = 3f
        )
    }
}

private fun DrawScope.drawHardDropEffect(
    animation: HardDropAnimation,
    progress: Float,
    cellWidth: Float,
    cellHeight: Float
) {
    // Impact wave effect
    val waveAlpha = (1f - progress) * 0.6f
    val waveExpand = progress * 20f

    for ((col, row) in animation.piecePositions) {
        val centerX = col * cellWidth + cellWidth / 2
        val centerY = row * cellHeight + cellHeight / 2

        // Expanding glow ring
        drawCircle(
            color = animation.color.copy(alpha = waveAlpha),
            radius = cellWidth / 2 + waveExpand,
            center = Offset(centerX, centerY),
            style = Stroke(width = 3f - progress * 2f)
        )

        // Inner bright flash
        if (progress < 0.3f) {
            drawCircle(
                color = Color.White.copy(alpha = (0.3f - progress) * 2f),
                radius = cellWidth / 3,
                center = Offset(centerX, centerY)
            )
        }
    }

    // Vertical impact lines going down
    val lineAlpha = (1f - progress) * 0.4f
    val lineLength = progress * cellHeight * 3

    for ((col, row) in animation.piecePositions) {
        val x = col * cellWidth + cellWidth / 2

        // Draw trailing lines below impact point
        drawLine(
            color = animation.color.copy(alpha = lineAlpha),
            start = Offset(x, row * cellHeight + cellHeight),
            end = Offset(x, row * cellHeight + cellHeight + lineLength),
            strokeWidth = 2f
        )
    }

    // Screen shake simulation via slight offset glow
    if (progress < 0.2f) {
        val shakeOffset = (0.2f - progress) * 4f
        drawRect(
            color = Color.White.copy(alpha = (0.2f - progress) * 0.3f),
            topLeft = Offset(-shakeOffset, -shakeOffset),
            size = Size(size.width + shakeOffset * 2, size.height + shakeOffset * 2)
        )
    }
}

private fun DrawScope.drawNeonCell(
    col: Int,
    row: Int,
    cellWidth: Float,
    cellHeight: Float,
    color: Color,
    glowColor: Color
) {
    val padding = 2f
    val x = col * cellWidth + padding
    val y = row * cellHeight + padding
    val w = cellWidth - padding * 2
    val h = cellHeight - padding * 2

    // Outer glow
    drawRoundRect(
        color = glowColor,
        topLeft = Offset(x - 2f, y - 2f),
        size = Size(w + 4f, h + 4f),
        cornerRadius = CornerRadius(4f, 4f)
    )

    // Main cell with gradient
    drawRoundRect(
        brush = Brush.verticalGradient(
            colors = listOf(
                color,
                color.copy(alpha = 0.8f),
                color.copy(alpha = 0.6f)
            ),
            startY = y,
            endY = y + h
        ),
        topLeft = Offset(x, y),
        size = Size(w, h),
        cornerRadius = CornerRadius(3f, 3f)
    )

    // Top highlight (shine effect)
    drawRoundRect(
        brush = Brush.verticalGradient(
            colors = listOf(
                Color.White.copy(alpha = 0.5f),
                Color.White.copy(alpha = 0.1f),
                Color.Transparent
            ),
            startY = y,
            endY = y + h * 0.5f
        ),
        topLeft = Offset(x + 1f, y + 1f),
        size = Size(w - 2f, h * 0.4f),
        cornerRadius = CornerRadius(2f, 2f)
    )

    // Inner border glow
    drawRoundRect(
        color = Color.White.copy(alpha = 0.3f),
        topLeft = Offset(x + 1f, y + 1f),
        size = Size(w - 2f, h - 2f),
        cornerRadius = CornerRadius(2f, 2f),
        style = Stroke(width = 1f)
    )
}

private fun DrawScope.drawGhostCell(
    col: Int,
    row: Int,
    cellWidth: Float,
    cellHeight: Float,
    color: Color
) {
    val padding = 2f
    val x = col * cellWidth + padding
    val y = row * cellHeight + padding
    val w = cellWidth - padding * 2
    val h = cellHeight - padding * 2

    // Ghost outline with glow
    drawRoundRect(
        color = color,
        topLeft = Offset(x, y),
        size = Size(w, h),
        cornerRadius = CornerRadius(3f, 3f),
        style = Stroke(width = 2f)
    )

    // Inner fill very transparent
    drawRoundRect(
        color = color.copy(alpha = 0.1f),
        topLeft = Offset(x, y),
        size = Size(w, h),
        cornerRadius = CornerRadius(3f, 3f)
    )
}
