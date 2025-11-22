package com.example.app_tetris.ui.game

import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
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
            .background(Color(0xFF0D0D1A))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = "TETRIS",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
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

        // Pause overlay
        if (gameState.isPaused && !gameState.isGameOver) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.7f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "PAUSED",
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        // Game Over dialog
        if (gameState.isGameOver) {
            AlertDialog(
                onDismissRequest = { },
                title = {
                    Text(
                        text = "GAME OVER",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                text = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Final Score: ${gameState.score}")
                        Text(text = "Level: ${gameState.level}")
                        Text(text = "Lines: ${gameState.linesCleared}")
                    }
                },
                confirmButton = {
                    Button(
                        onClick = viewModel::resetGame,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4CAF50)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Play Again")
                    }
                },
                containerColor = Color(0xFF1A1A2E),
                titleContentColor = Color.White,
                textContentColor = Color.White
            )
        }
    }
}
