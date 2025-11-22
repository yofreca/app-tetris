package com.example.app_tetris.ui.game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.app_tetris.model.Tetromino
import com.example.app_tetris.ui.theme.BoardBackground

@Composable
fun NextPiecePreview(
    nextPiece: Tetromino,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "NEXT",
            style = MaterialTheme.typography.titleMedium,
            color = Color.White
        )

        Box(
            modifier = Modifier
                .padding(top = 8.dp)
                .size(80.dp)
                .background(BoardBackground, RoundedCornerShape(8.dp))
                .border(2.dp, Color.White, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.size(60.dp)) {
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
                            val padding = 1f
                            drawRect(
                                color = nextPiece.color,
                                topLeft = Offset(
                                    offsetX + col * cellSize + padding,
                                    offsetY + row * cellSize + padding
                                ),
                                size = Size(cellSize - padding * 2, cellSize - padding * 2)
                            )
                            // Highlight effect
                            drawRect(
                                color = Color.White.copy(alpha = 0.3f),
                                topLeft = Offset(
                                    offsetX + col * cellSize + padding,
                                    offsetY + row * cellSize + padding
                                ),
                                size = Size(cellSize - padding * 2, (cellSize - padding * 2) * 0.3f)
                            )
                        }
                    }
                }
            }
        }
    }
}
