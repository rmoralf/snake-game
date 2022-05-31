package com.rmoralf.snake.presentation.game

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmoralf.snake.core.utils.Constants.GAME_VERSION
import com.rmoralf.snake.presentation.game.engine.GameEngine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class GameScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var gameVersionState = mutableStateOf(0)
    var scoreState = mutableStateOf(0)

    val isPlaying = mutableStateOf(true)
    private lateinit var gameEngine: GameEngine
    lateinit var gameState: MutableStateFlow<GameEngine.GameState>

    init {
        savedStateHandle.get<String>(GAME_VERSION)?.let { gameVersion ->
            gameVersionState.value = gameVersion.toInt()
        }
        startGame()
    }

    private fun startGame() {
        gameEngine = GameEngine(
            gameVersion = gameVersionState.value,
            scope = viewModelScope,
            onGameEnded = {
                if (isPlaying.value) {
                    isPlaying.value = false
                }
            },
            onFoodEaten = { scoreState.value++ }
        )
        gameState = gameEngine.state
    }

    fun moveSnake(direction: Int) {
        gameEngine.moveSnake(direction)
    }

    fun resetGame() {
        scoreState.value = 0
        gameEngine.reset()
        isPlaying.value = true
    }

}