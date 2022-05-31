package com.rmoralf.snake.presentation.game.engine

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.rmoralf.snake.core.utils.Constants.SCREEN_SIZE
import com.rmoralf.snake.domain.game.GraphicsVersion
import com.rmoralf.snake.presentation.ui.theme.V1Background
import com.rmoralf.snake.presentation.ui.theme.V1Elements
import com.rmoralf.snake.presentation.ui.theme.V2Background
import com.rmoralf.snake.presentation.ui.theme.V2Elements

@Composable
fun Graphics(
    state: GameEngine.GameState,
    graphicsVersion: Int
) {
    val (colorBg, colorMain, snakeShape) = when (graphicsVersion) {
        GraphicsVersion.V1 -> Triple(V1Background, V1Elements, RectangleShape)
        GraphicsVersion.V2 -> Triple(V2Background, V2Elements, CutCornerShape(4.dp))
        else -> Triple(V1Background, V1Elements, RectangleShape)
    }

    BoxWithConstraints(
        modifier = Modifier
            .padding(16.dp)
            .background(colorBg)
            .border(2.dp, colorMain)
    ) {
        val tileSize = maxWidth / SCREEN_SIZE

        //Board
        Box(
            Modifier
                .size(maxWidth)
                .border(2.dp, colorBg)
        )

        //Snake
        state.snake.forEach {
            Box(
                modifier = Modifier
                    .offset(x = tileSize * it.first, y = tileSize * it.second)
                    .size(tileSize)
                    .background(colorMain, snakeShape)
            )
        }

        //Food
        Box(
            Modifier
                .offset(x = tileSize * state.food.first, y = tileSize * state.food.second)
                .size(tileSize)
                .background(Color.Transparent, CutCornerShape(20.dp))
                .border(2.dp, colorMain, CutCornerShape(20.dp))
        )
    }
}
