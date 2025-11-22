package com.example.app_tetris.ui.game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
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
import com.example.app_tetris.model.Tetromino
import com.example.app_tetris.ui.theme.GhostPieceColor
import com.example.app_tetris.ui.theme.NeonBoardBackground
import com.example.app_tetris.ui.theme.NeonBorderGlow
import com.example.app_tetris.ui.theme.NeonGridLine

@Composable
fun GameBoard(
    gameState: GameState,
    modifier: Modifier = Modifier
) {
    val aspectRatio = GameState.BOARD_WIDTH.toFloat() / GameState.BOARD_HEIGHT.toFloat()

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
