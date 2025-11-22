package com.example.app_tetris.ui.game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_tetris.model.Tetromino
import com.example.app_tetris.ui.theme.NeonAccentPrimary
import com.example.app_tetris.ui.theme.NeonBoardBackground

@Composable
fun NextPiecePreview(
    nextPiece: Tetromino,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Neon title
        Text(
            text = "NEXT",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = NeonAccentPrimary,
            modifier = Modifier.drawBehind {
                // Text glow effect
                drawRoundRect(
                    color = NeonAccentPrimary.copy(alpha = 0.3f),
                    cornerRadius = CornerRadius(4f, 4f),
                    topLeft = Offset(-8f, -4f),
                    size = Size(size.width + 16f, size.height + 8f)
                )
            }
        )

        Box(
            modifier = Modifier
                .padding(top = 12.dp)
                .size(90.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            NeonBoardBackground,
                            Color(0xFF050515),
                            NeonBoardBackground
                        )
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
                .drawBehind {
                    // Outer glow
                    for (i in 3 downTo 1) {
                        drawRoundRect(
                            color = NeonAccentPrimary.copy(alpha = 0.1f * i),
                            cornerRadius = CornerRadius(12f + i * 2, 12f + i * 2),
                            style = Stroke(width = (i * 2).toFloat()),
                            topLeft = Offset(-i * 2f, -i * 2f),
                            size = Size(size.width + i * 4f, size.height + i * 4f)
                        )
                    }
                    // Border
                    drawRoundRect(
                        color = NeonAccentPrimary,
                        cornerRadius = CornerRadius(12f, 12f),
                        style = Stroke(width = 2f)
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.size(65.dp)) {
                val shape = nextPiece.getShape(0)
                val pieceWidth = shape[0].size
                val pieceHeight = shape.size

                val cellSize = minOf(
                    size.width / pieceWidth,
                    size.height / pieceHeight
                )

                val offsetX = (size.width - pieceWidth * cellSize) / 2
                val offsetY = (size.height - pieceHeight * cellSize) / 2

                for (row in shape.indices) {
                    for (col in shape[row].indices) {
                        if (shape[row][col] == 1) {
                            val padding = 2f
                            val x = offsetX + col * cellSize + padding
                            val y = offsetY + row * cellSize + padding
                            val w = cellSize - padding * 2
                            val h = cellSize - padding * 2

                            // Glow effect
                            drawRoundRect(
                                color = nextPiece.glowColor,
                                topLeft = Offset(x - 2f, y - 2f),
                                size = Size(w + 4f, h + 4f),
                                cornerRadius = CornerRadius(4f, 4f)
                            )

                            // Main cell
                            drawRoundRect(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        nextPiece.color,
                                        nextPiece.color.copy(alpha = 0.7f)
                                    ),
                                    startY = y,
                                    endY = y + h
                                ),
                                topLeft = Offset(x, y),
                                size = Size(w, h),
                                cornerRadius = CornerRadius(3f, 3f)
                            )

                            // Highlight
                            drawRoundRect(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.White.copy(alpha = 0.5f),
                                        Color.Transparent
                                    ),
                                    startY = y,
                                    endY = y + h * 0.5f
                                ),
                                topLeft = Offset(x + 1f, y + 1f),
                                size = Size(w - 2f, h * 0.35f),
                                cornerRadius = CornerRadius(2f, 2f)
                            )
                        }
                    }
                }
            }
        }
    }
}
