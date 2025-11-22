package com.example.app_tetris.game

import androidx.compose.ui.graphics.Color
import com.example.app_tetris.model.GameState
import com.example.app_tetris.model.HardDropAnimation
import com.example.app_tetris.model.LineClearAnimation
import com.example.app_tetris.model.MoveDirection
import com.example.app_tetris.model.Tetromino

object TetrisGameLogic {

    fun checkCollision(
        board: List<List<Color?>>,
        piece: Tetromino,
        pieceX: Int,
        pieceY: Int,
        rotation: Int
    ): Boolean {
        val shape = piece.getShape(rotation)
        for (row in shape.indices) {
            for (col in shape[row].indices) {
                if (shape[row][col] == 1) {
                    val boardX = pieceX + col
                    val boardY = pieceY + row

                    // Check boundaries
                    if (boardX < 0 || boardX >= GameState.BOARD_WIDTH) return true
                    if (boardY < 0 || boardY >= GameState.BOARD_HEIGHT) return true

                    // Check collision with placed pieces
                    if (boardY >= 0 && board[boardY][boardX] != null) return true
                }
            }
        }
        return false
    }

    fun movePiece(state: GameState, direction: MoveDirection): GameState {
        if (state.isGameOver || state.isPaused) return state

        val newX = when (direction) {
            MoveDirection.LEFT -> state.pieceX - 1
            MoveDirection.RIGHT -> state.pieceX + 1
            MoveDirection.DOWN -> state.pieceX
        }
        val newY = when (direction) {
            MoveDirection.DOWN -> state.pieceY + 1
            else -> state.pieceY
        }

        return if (!checkCollision(state.board, state.currentPiece, newX, newY, state.pieceRotation)) {
            state.copy(pieceX = newX, pieceY = newY)
        } else if (direction == MoveDirection.DOWN) {
            // Lock piece and spawn new one
            lockPieceAndSpawnNew(state)
        } else {
            state
        }
    }

    fun rotatePiece(state: GameState): GameState {
        if (state.isGameOver || state.isPaused) return state

        val newRotation = (state.pieceRotation + 1) % 4

        // Try normal rotation
        if (!checkCollision(state.board, state.currentPiece, state.pieceX, state.pieceY, newRotation)) {
            return state.copy(pieceRotation = newRotation)
        }

        // Wall kick attempts
        val kicks = listOf(-1, 1, -2, 2)
        for (kick in kicks) {
            if (!checkCollision(state.board, state.currentPiece, state.pieceX + kick, state.pieceY, newRotation)) {
                return state.copy(pieceX = state.pieceX + kick, pieceRotation = newRotation)
            }
        }

        return state
    }

    fun hardDrop(state: GameState): GameState {
        if (state.isGameOver || state.isPaused) return state

        var newY = state.pieceY
        while (!checkCollision(state.board, state.currentPiece, state.pieceX, newY + 1, state.pieceRotation)) {
            newY++
        }

        // Get piece positions for animation
        val piecePositions = getPiecePositions(state.currentPiece, state.pieceX, newY, state.pieceRotation)
        val hardDropAnim = HardDropAnimation(
            impactY = newY,
            piecePositions = piecePositions,
            color = state.currentPiece.color
        )

        val newState = lockPieceAndSpawnNew(state.copy(pieceY = newY))
        return newState.copy(hardDropAnimation = hardDropAnim)
    }

    private fun getPiecePositions(piece: Tetromino, pieceX: Int, pieceY: Int, rotation: Int): List<Pair<Int, Int>> {
        val positions = mutableListOf<Pair<Int, Int>>()
        val shape = piece.getShape(rotation)
        for (row in shape.indices) {
            for (col in shape[row].indices) {
                if (shape[row][col] == 1) {
                    positions.add(Pair(pieceX + col, pieceY + row))
                }
            }
        }
        return positions
    }

    private fun lockPieceAndSpawnNew(state: GameState): GameState {
        val newBoard = lockPiece(state)
        val (clearedBoard, linesCount, clearedRows) = clearLinesWithRows(newBoard)
        val newScore = state.score + calculateScore(linesCount, state.level)
        val newLinesCleared = state.linesCleared + linesCount
        val newLevel = (newLinesCleared / 10) + 1

        val nextPiece = state.nextPiece
        val spawnX = GameState.BOARD_WIDTH / 2 - 2
        val spawnY = 0

        // Check if game is over
        val isGameOver = checkCollision(clearedBoard, nextPiece, spawnX, spawnY, 0)

        // Create line clear animation if lines were cleared
        val lineClearAnim = if (clearedRows.isNotEmpty()) {
            LineClearAnimation(clearedRows = clearedRows)
        } else null

        return state.copy(
            board = clearedBoard,
            currentPiece = nextPiece,
            nextPiece = Tetromino.random(),
            pieceX = spawnX,
            pieceY = spawnY,
            pieceRotation = 0,
            score = newScore,
            level = newLevel,
            linesCleared = newLinesCleared,
            isGameOver = isGameOver,
            lineClearAnimation = lineClearAnim
        )
    }

    private fun lockPiece(state: GameState): List<List<Color?>> {
        val newBoard = state.board.map { it.toMutableList() }.toMutableList()
        val shape = state.currentPiece.getShape(state.pieceRotation)

        for (row in shape.indices) {
            for (col in shape[row].indices) {
                if (shape[row][col] == 1) {
                    val boardX = state.pieceX + col
                    val boardY = state.pieceY + row
                    if (boardY in 0 until GameState.BOARD_HEIGHT &&
                        boardX in 0 until GameState.BOARD_WIDTH
                    ) {
                        newBoard[boardY][boardX] = state.currentPiece.color
                    }
                }
            }
        }

        return newBoard
    }

    private fun clearLinesWithRows(board: List<List<Color?>>): Triple<List<List<Color?>>, Int, List<Int>> {
        val newBoard = board.map { it.toMutableList() }.toMutableList()
        val clearedRows = mutableListOf<Int>()

        // Find all full rows first
        for (row in 0 until GameState.BOARD_HEIGHT) {
            if (newBoard[row].all { it != null }) {
                clearedRows.add(row)
            }
        }

        // Remove cleared rows from bottom to top
        for (row in clearedRows.sortedDescending()) {
            newBoard.removeAt(row)
            newBoard.add(0, MutableList(GameState.BOARD_WIDTH) { null })
        }

        return Triple(newBoard, clearedRows.size, clearedRows)
    }

    private fun calculateScore(linesCleared: Int, level: Int): Int {
        val baseScore = when (linesCleared) {
            1 -> 100
            2 -> 300
            3 -> 500
            4 -> 800 // Tetris!
            else -> 0
        }
        return baseScore * level
    }

    fun getDropSpeed(level: Int): Long {
        return maxOf(100L, 1000L - (level - 1) * 100L)
    }

    fun getGhostY(state: GameState): Int {
        var ghostY = state.pieceY
        while (!checkCollision(state.board, state.currentPiece, state.pieceX, ghostY + 1, state.pieceRotation)) {
            ghostY++
        }
        return ghostY
    }
}
