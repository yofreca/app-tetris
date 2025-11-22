package com.example.app_tetris.model

import androidx.compose.ui.graphics.Color

enum class Tetromino(
    val shapes: List<List<List<Int>>>,
    val color: Color,
    val glowColor: Color
) {
    I(
        shapes = listOf(
            listOf(
                listOf(0, 0, 0, 0),
                listOf(1, 1, 1, 1),
                listOf(0, 0, 0, 0),
                listOf(0, 0, 0, 0)
            ),
            listOf(
                listOf(0, 0, 1, 0),
                listOf(0, 0, 1, 0),
                listOf(0, 0, 1, 0),
                listOf(0, 0, 1, 0)
            ),
            listOf(
                listOf(0, 0, 0, 0),
                listOf(0, 0, 0, 0),
                listOf(1, 1, 1, 1),
                listOf(0, 0, 0, 0)
            ),
            listOf(
                listOf(0, 1, 0, 0),
                listOf(0, 1, 0, 0),
                listOf(0, 1, 0, 0),
                listOf(0, 1, 0, 0)
            )
        ),
        color = Color(0xFF00FFFF),
        glowColor = Color(0x8000FFFF)
    ),

    O(
        shapes = listOf(
            listOf(
                listOf(1, 1),
                listOf(1, 1)
            ),
            listOf(
                listOf(1, 1),
                listOf(1, 1)
            ),
            listOf(
                listOf(1, 1),
                listOf(1, 1)
            ),
            listOf(
                listOf(1, 1),
                listOf(1, 1)
            )
        ),
        color = Color(0xFFFFFF00),
        glowColor = Color(0x80FFFF00)
    ),

    T(
        shapes = listOf(
            listOf(
                listOf(0, 1, 0),
                listOf(1, 1, 1),
                listOf(0, 0, 0)
            ),
            listOf(
                listOf(0, 1, 0),
                listOf(0, 1, 1),
                listOf(0, 1, 0)
            ),
            listOf(
                listOf(0, 0, 0),
                listOf(1, 1, 1),
                listOf(0, 1, 0)
            ),
            listOf(
                listOf(0, 1, 0),
                listOf(1, 1, 0),
                listOf(0, 1, 0)
            )
        ),
        color = Color(0xFFFF00FF),
        glowColor = Color(0x80FF00FF)
    ),

    S(
        shapes = listOf(
            listOf(
                listOf(0, 1, 1),
                listOf(1, 1, 0),
                listOf(0, 0, 0)
            ),
            listOf(
                listOf(0, 1, 0),
                listOf(0, 1, 1),
                listOf(0, 0, 1)
            ),
            listOf(
                listOf(0, 0, 0),
                listOf(0, 1, 1),
                listOf(1, 1, 0)
            ),
            listOf(
                listOf(1, 0, 0),
                listOf(1, 1, 0),
                listOf(0, 1, 0)
            )
        ),
        color = Color(0xFF00FF66),
        glowColor = Color(0x8000FF66)
    ),

    Z(
        shapes = listOf(
            listOf(
                listOf(1, 1, 0),
                listOf(0, 1, 1),
                listOf(0, 0, 0)
            ),
            listOf(
                listOf(0, 0, 1),
                listOf(0, 1, 1),
                listOf(0, 1, 0)
            ),
            listOf(
                listOf(0, 0, 0),
                listOf(1, 1, 0),
                listOf(0, 1, 1)
            ),
            listOf(
                listOf(0, 1, 0),
                listOf(1, 1, 0),
                listOf(1, 0, 0)
            )
        ),
        color = Color(0xFFFF0055),
        glowColor = Color(0x80FF0055)
    ),

    J(
        shapes = listOf(
            listOf(
                listOf(1, 0, 0),
                listOf(1, 1, 1),
                listOf(0, 0, 0)
            ),
            listOf(
                listOf(0, 1, 1),
                listOf(0, 1, 0),
                listOf(0, 1, 0)
            ),
            listOf(
                listOf(0, 0, 0),
                listOf(1, 1, 1),
                listOf(0, 0, 1)
            ),
            listOf(
                listOf(0, 1, 0),
                listOf(0, 1, 0),
                listOf(1, 1, 0)
            )
        ),
        color = Color(0xFF0066FF),
        glowColor = Color(0x800066FF)
    ),

    L(
        shapes = listOf(
            listOf(
                listOf(0, 0, 1),
                listOf(1, 1, 1),
                listOf(0, 0, 0)
            ),
            listOf(
                listOf(0, 1, 0),
                listOf(0, 1, 0),
                listOf(0, 1, 1)
            ),
            listOf(
                listOf(0, 0, 0),
                listOf(1, 1, 1),
                listOf(1, 0, 0)
            ),
            listOf(
                listOf(1, 1, 0),
                listOf(0, 1, 0),
                listOf(0, 1, 0)
            )
        ),
        color = Color(0xFFFF6600),
        glowColor = Color(0x80FF6600)
    );

    fun getShape(rotation: Int): List<List<Int>> {
        return shapes[rotation % shapes.size]
    }

    companion object {
        fun random(): Tetromino = entries.random()

        fun getGlowColorForColor(color: Color): Color {
            return entries.find { it.color == color }?.glowColor ?: Color(0x80FFFFFF)
        }
    }
}
