package com.rmoralf.snake.presentation.game

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rmoralf.snake.domain.game.GraphicsVersion
import com.rmoralf.snake.domain.game.SnakeDirection
import com.rmoralf.snake.presentation.game.components.GameOver
import com.rmoralf.snake.presentation.game.engine.Graphics
import com.rmoralf.snake.presentation.ui.theme.V1Background
import com.rmoralf.snake.presentation.ui.theme.V1Elements
import com.rmoralf.snake.presentation.ui.theme.V2Background
import com.rmoralf.snake.presentation.ui.theme.V2Elements
import kotlin.math.abs

@Composable
fun GameScreen(
    navController: NavController,
    viewModel: GameScreenViewModel = hiltViewModel()
) {

    val gameVersionState = viewModel.gameVersionState.value
    val gameState = viewModel.gameState.collectAsState()

    val (bgColor, fontColor) = when (gameVersionState) {
        GraphicsVersion.V1 -> Pair(V1Background, V1Elements)
        GraphicsVersion.V2 -> Pair(V2Background, V2Elements)
        else -> Pair(V1Background, V1Elements)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(bgColor)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        if (change.consumed.positionChange) {
                            val (x, y) = dragAmount
                            if (abs(x) > abs(y) && x > 0)
                                viewModel.moveSnake(SnakeDirection.Right)
                            else if (abs(x) > abs(y) && x < 0)
                                viewModel.moveSnake(SnakeDirection.Left)
                            else if (y > 0)
                                viewModel.moveSnake(SnakeDirection.Down)
                            else
                                viewModel.moveSnake(SnakeDirection.Up)
                        }
                    }
                }
        ) {
            if (viewModel.isPlaying.value) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Score: ${viewModel.scoreState.value}",
                        color = fontColor,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                    )
                    Graphics(gameState.value, gameVersionState)
                }
            } else {
                GameOver(navController = navController)
            }
        }
    }
}