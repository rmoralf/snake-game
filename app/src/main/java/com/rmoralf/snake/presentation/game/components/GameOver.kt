package com.rmoralf.snake.presentation.game.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rmoralf.snake.presentation.game.GameScreenViewModel

@Composable
fun GameOver(
    navController: NavController,
    viewModel: GameScreenViewModel = hiltViewModel()
) {
    Scaffold {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()

        ) {
            Text(
                text = "Game Over",
                color = Black,
                fontSize = 36.sp,
                modifier = Modifier
                    .padding(bottom = 20.dp)
            )

            Text(
                text = "Your score: ${viewModel.scoreState.value}",
                color = Black,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(bottom = 20.dp)
            )

            Button(modifier = Modifier.padding(top = 30.dp),
                onClick = {
                    viewModel.resetGame()
                }) {
                Text(text = "Retry")
            }

            Button(onClick = {
                navController.popBackStack()
            }) {
                Text(text = "Exit")
            }
        }
    }
}