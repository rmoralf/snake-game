package com.rmoralf.snake.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rmoralf.snake.core.utils.Constants.GAME_VERSION
import com.rmoralf.snake.core.utils.Utils.Companion.createSubroute
import com.rmoralf.snake.presentation.game.GameScreen
import com.rmoralf.snake.presentation.menu.MenuScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.MENU.route,
    ) {
        composable(route = Screen.MENU.route) {
            MenuScreen(navController = navController)
        }
        composable(route = createSubroute(Screen.GAME.route, GAME_VERSION)) {
            GameScreen(navController = navController)
        }
    }
}