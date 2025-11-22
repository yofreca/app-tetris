package com.example.app_tetris.ui.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import com.example.app_tetris.ui.theme.NeonAccentPrimary
import com.example.app_tetris.ui.theme.NeonAccentSecondary
import com.example.app_tetris.ui.theme.NeonBoardBackground
import com.example.app_tetris.ui.theme.NeonButtonGreen
import com.example.app_tetris.ui.theme.NeonTextSecondary

@Composable
fun ScorePanel(
    score: Int,
    level: Int,
    linesCleared: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
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
                        color = NeonAccentSecondary.copy(alpha = 0.08f * i),
                        cornerRadius = CornerRadius(12f + i * 2, 12f + i * 2),
                        style = Stroke(width = (i * 2).toFloat()),
                        topLeft = Offset(-i * 2f, -i * 2f),
                        size = Size(size.width + i * 4f, size.height + i * 4f)
                    )
                }
                // Border
                drawRoundRect(
                    color = NeonAccentSecondary,
                    cornerRadius = CornerRadius(12f, 12f),
                    style = Stroke(width = 2f)
                )
            }
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScoreItem(label = "SCORE", value = score.toString(), accentColor = NeonAccentPrimary)
        ScoreItem(label = "LEVEL", value = level.toString(), accentColor = NeonButtonGreen)
        ScoreItem(label = "LINES", value = linesCleared.toString(), accentColor = NeonAccentSecondary)
    }
}

@Composable
private fun ScoreItem(
    label: String,
    value: String,
    accentColor: Color
) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            color = NeonTextSecondary,
            letterSpacing = 2.sp
        )
        Text(
            text = value,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = accentColor,
            modifier = Modifier.drawBehind {
                // Value glow
                drawRoundRect(
                    color = accentColor.copy(alpha = 0.2f),
                    cornerRadius = CornerRadius(4f, 4f),
                    topLeft = Offset(-6f, -2f),
                    size = Size(size.width + 12f, size.height + 4f)
                )
            }
        )
    }
}
