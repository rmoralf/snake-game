package com.rmoralf.snake.presentation.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rmoralf.snake.domain.game.GraphicsVersion
import com.rmoralf.snake.presentation.navigation.Screen

@Composable
fun MenuScreen(
    navController: NavController,
) {
    val startGame = remember { mutableStateOf(0) }
    if (startGame.value != 0) {
        StartGame(navController = navController, graphicsVersion = startGame.value)
    }

    Scaffold {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = "SNAKE",
                color = Color.Black,
                fontSize = 54.sp,
                modifier = Modifier.padding(bottom = 64.dp)
            )
            Button(onClick = {
                startGame.value = GraphicsVersion.V1
            }) {
                Text(text = "Version 1")
            }

            Button(onClick = {
                startGame.value = GraphicsVersion.V2
            }) {
                Text(text = "Version 2")
            }
        }
    }
}

@Composable
fun StartGame(navController: NavController, graphicsVersion: Int) {
    LaunchedEffect(Unit) {
        navController.navigate("${Screen.GAME.route}/${graphicsVersion}")
    }
}