package com.example.app_tetris.model

import androidx.compose.ui.graphics.Color

enum class Tetromino(
    val shapes: List<List<List<Int>>>,
    val color: Color
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
        color = Color(0xFF00FFFF) // Cyan
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
        color = Color(0xFFFFFF00) // Yellow
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
        color = Color(0xFF800080) // Purple
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
        color = Color(0xFF00FF00) // Green
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
        color = Color(0xFFFF0000) // Red
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
        color = Color(0xFF0000FF) // Blue
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
        color = Color(0xFFFF7F00) // Orange
    );

    fun getShape(rotation: Int): List<List<Int>> {
        return shapes[rotation % shapes.size]
    }

    companion object {
        fun random(): Tetromino = entries.random()
    }
}
