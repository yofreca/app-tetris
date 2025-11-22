package com.example.app_tetris.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_tetris.game.TetrisGameLogic
import com.example.app_tetris.model.GameState
import com.example.app_tetris.model.MoveDirection
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TetrisViewModel : ViewModel() {

    private val _gameState = MutableStateFlow(GameState())
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()

    private var gameLoopJob: Job? = null

    init {
        startGame()
    }

    fun startGame() {
        _gameState.value = GameState()
        startGameLoop()
    }

    fun pauseGame() {
        _gameState.update { it.copy(isPaused = !it.isPaused) }
        if (!_gameState.value.isPaused) {
            startGameLoop()
        } else {
            gameLoopJob?.cancel()
        }
    }

    fun resetGame() {
        gameLoopJob?.cancel()
        startGame()
    }

    fun moveLeft() {
        _gameState.update { TetrisGameLogic.movePiece(it, MoveDirection.LEFT) }
    }

    fun moveRight() {
        _gameState.update { TetrisGameLogic.movePiece(it, MoveDirection.RIGHT) }
    }

    fun moveDown() {
        _gameState.update { TetrisGameLogic.movePiece(it, MoveDirection.DOWN) }
    }

    fun rotate() {
        _gameState.update { TetrisGameLogic.rotatePiece(it) }
    }

    fun hardDrop() {
        _gameState.update { TetrisGameLogic.hardDrop(it) }
    }

    private fun startGameLoop() {
        gameLoopJob?.cancel()
        gameLoopJob = viewModelScope.launch {
            while (true) {
                val state = _gameState.value
                if (state.isGameOver || state.isPaused) {
                    break
                }

                delay(TetrisGameLogic.getDropSpeed(state.level))

                _gameState.update { currentState ->
                    if (!currentState.isGameOver && !currentState.isPaused) {
                        TetrisGameLogic.movePiece(currentState, MoveDirection.DOWN)
                    } else {
                        currentState
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        gameLoopJob?.cancel()
    }
}
