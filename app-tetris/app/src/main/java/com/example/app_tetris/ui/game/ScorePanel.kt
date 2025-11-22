package com.example.app_tetris.ui.game

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.app_tetris.ui.theme.BoardBackground

@Composable
fun ScorePanel(
    score: Int,
    level: Int,
    linesCleared: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(BoardBackground, RoundedCornerShape(8.dp))
            .border(2.dp, Color.White, RoundedCornerShape(8.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScoreItem(label = "SCORE", value = score.toString())
        ScoreItem(label = "LEVEL", value = level.toString())
        ScoreItem(label = "LINES", value = linesCleared.toString())
    }
}

@Composable
private fun ScoreItem(
    label: String,
    value: String
) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = Color.Gray
        )
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}
