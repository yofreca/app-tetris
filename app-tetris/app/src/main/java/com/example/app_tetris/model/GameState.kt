package com.example.app_tetris.model

import androidx.compose.ui.graphics.Color

data class GameState(
    val board: List<List<Color?>> = createEmptyBoard(),
    val currentPiece: Tetromino = Tetromino.random(),
    val nextPiece: Tetromino = Tetromino.random(),
    val pieceX: Int = BOARD_WIDTH / 2 - 2,
    val pieceY: Int = 0,
    val pieceRotation: Int = 0,
    val score: Int = 0,
    val level: Int = 1,
    val linesCleared: Int = 0,
    val isGameOver: Boolean = false,
    val isPaused: Boolean = false
) {
    companion object {
        const val BOARD_WIDTH = 10
        const val BOARD_HEIGHT = 20

        fun createEmptyBoard(): List<List<Color?>> {
            return List(BOARD_HEIGHT) { List(BOARD_WIDTH) { null } }
        }
    }
}

enum class MoveDirection {
    LEFT, RIGHT, DOWN
}
