package com.rmoralf.snake.presentation.game.engine

import androidx.compose.runtime.mutableStateOf
import com.rmoralf.snake.core.utils.Constants.SCREEN_SIZE
import com.rmoralf.snake.domain.game.GraphicsVersion
import com.rmoralf.snake.domain.game.SnakeDirection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.*
import kotlin.math.abs

class GameEngine(
    private val gameVersion: Int,
    private val scope: CoroutineScope,
    private val onGameEnded: () -> Unit,
    private val onFoodEaten: () -> Unit
) {
    private val mutex = Mutex()

    private val _state = MutableStateFlow(
        GameState(
            snake = listOf(Pair(3, 3)),
            food = Pair(12, 3),
            currentDirection = SnakeDirection.Right
        )
    )
    val state: MutableStateFlow<GameState> = _state

    private val currentDirection = mutableStateOf(SnakeDirection.Right)

    private var move = Pair(1, 0)
        set(value) {
            scope.launch {
                mutex.withLock {
                    field = value
                }
            }
        }

    fun moveSnake(direction: Int) {
        //Same or reverse direction move cannot be made
        if (abs(direction) == abs(currentDirection.value))
            return

        when (direction) {
            SnakeDirection.Up -> {
                move = Pair(0, -1)
                currentDirection.value = SnakeDirection.Up
            }
            SnakeDirection.Down -> {
                move = Pair(0, 1)
                currentDirection.value = SnakeDirection.Down
            }
            SnakeDirection.Left -> {
                move = Pair(-1, 0)
                currentDirection.value = SnakeDirection.Left
            }
            SnakeDirection.Right -> {
                move = Pair(1, 0)
                currentDirection.value = SnakeDirection.Right
            }
        }

    }

    fun reset() {
        _state.update {
            it.copy(
                snake = listOf(Pair(3, 3)),
                food = Pair(12, 3),
                currentDirection = SnakeDirection.Right
            )
        }
        currentDirection.value = SnakeDirection.Right
        move = Pair(1, 0)
    }

    init {
        scope.launch {
            var snakeLength = 2
            while (true) {
                delay(150)
                _state.update {

                    val newPosition = it.snake.first().let { oldPos ->
                        if (gameVersion == GraphicsVersion.V1) {
                            val limitsAreReached =
                                (oldPos.first + move.first) !in 0 until SCREEN_SIZE
                                        || (oldPos.second + move.second) !in 0 until SCREEN_SIZE
                            if (limitsAreReached) {
                                snakeLength = 2
                                onGameEnded.invoke()
                            }
                        }
                        mutex.withLock {
                            Pair(
                                (oldPos.first + move.first + SCREEN_SIZE) % SCREEN_SIZE,
                                (oldPos.second + move.second + SCREEN_SIZE) % SCREEN_SIZE
                            )
                        }
                    }

                    if (newPosition == it.food) {
                        onFoodEaten.invoke()
                        snakeLength++
                    }

                    if (it.snake.contains(newPosition)) {
                        snakeLength = 2
                        onGameEnded.invoke()
                    }

                    it.copy(
                        snake = listOf(newPosition) + it.snake.take(snakeLength - 1),
                        food = if (newPosition == it.food) Pair(
                            Random().nextInt(SCREEN_SIZE),
                            Random().nextInt(SCREEN_SIZE)
                        ) else it.food,
                        currentDirection = currentDirection.value,
                    )
                }
            }
        }
    }

    data class GameState(
        val snake: List<Pair<Int, Int>>,
        val food: Pair<Int, Int>,
        val currentDirection: Int
    )
}