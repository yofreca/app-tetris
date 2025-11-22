package com.example.app_tetris.ui.game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.app_tetris.game.TetrisGameLogic
import com.example.app_tetris.model.GameState
import com.example.app_tetris.ui.theme.BoardBackground
import com.example.app_tetris.ui.theme.BoardGridLine
import com.example.app_tetris.ui.theme.GhostPieceColor

@Composable
fun GameBoard(
    gameState: GameState,
    modifier: Modifier = Modifier
) {
    val aspectRatio = GameState.BOARD_WIDTH.toFloat() / GameState.BOARD_HEIGHT.toFloat()

    Canvas(
        modifier = modifier
            .fillMaxHeight(0.7f)
            .aspectRatio(aspectRatio)
            .background(BoardBackground)
            .border(2.dp, Color.White)
    ) {
        val cellWidth = size.width / GameState.BOARD_WIDTH
        val cellHeight = size.height / GameState.BOARD_HEIGHT

        // Draw grid lines
        for (i in 1 until GameState.BOARD_WIDTH) {
            drawLine(
                color = BoardGridLine,
                start = Offset(i * cellWidth, 0f),
                end = Offset(i * cellWidth, size.height),
                strokeWidth = 1f
            )
        }
        for (i in 1 until GameState.BOARD_HEIGHT) {
            drawLine(
                color = BoardGridLine,
                start = Offset(0f, i * cellHeight),
                end = Offset(size.width, i * cellHeight),
                strokeWidth = 1f
            )
        }

        // Draw placed pieces on board
        for (row in 0 until GameState.BOARD_HEIGHT) {
            for (col in 0 until GameState.BOARD_WIDTH) {
                gameState.board[row][col]?.let { color ->
                    drawCell(col, row, cellWidth, cellHeight, color)
                }
            }
        }

        // Draw ghost piece (preview of where piece will land)
        if (!gameState.isGameOver && !gameState.isPaused) {
            val ghostY = TetrisGameLogic.getGhostY(gameState)
            val shape = gameState.currentPiece.getShape(gameState.pieceRotation)
            for (row in shape.indices) {
                for (col in shape[row].indices) {
                    if (shape[row][col] == 1) {
                        val boardX = gameState.pieceX + col
                        val boardY = ghostY + row
                        if (boardY >= 0 && boardY < GameState.BOARD_HEIGHT &&
                            boardX >= 0 && boardX < GameState.BOARD_WIDTH
                        ) {
                            drawRect(
                                color = GhostPieceColor,
                                topLeft = Offset(boardX * cellWidth, boardY * cellHeight),
                                size = Size(cellWidth, cellHeight),
                                style = Stroke(width = 2f)
                            )
                        }
                    }
                }
            }
        }

        // Draw current piece
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
                            drawCell(boardX, boardY, cellWidth, cellHeight, gameState.currentPiece.color)
                        }
                    }
                }
            }
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawCell(
    col: Int,
    row: Int,
    cellWidth: Float,
    cellHeight: Float,
    color: Color
) {
    val padding = 1f
    drawRect(
        color = color,
        topLeft = Offset(col * cellWidth + padding, row * cellHeight + padding),
        size = Size(cellWidth - padding * 2, cellHeight - padding * 2)
    )
    // Add highlight effect
    drawRect(
        color = Color.White.copy(alpha = 0.3f),
        topLeft = Offset(col * cellWidth + padding, row * cellHeight + padding),
        size = Size(cellWidth - padding * 2, (cellHeight - padding * 2) * 0.3f)
    )
}
