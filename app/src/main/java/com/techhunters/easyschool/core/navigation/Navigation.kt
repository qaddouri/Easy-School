package com.techhunters.easyschool.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.techhunters.easyschool.DetailsScreen
import com.techhunters.easyschool.MainScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController)
        }

        composable(
            route = Screen.DetailsScreen.route + "/{name}",
            arguments = listOf(
                navArgument(name = "name") {
                    type = NavType.StringType
                    defaultValue = "Abdo"
                    nullable = true
                })
        ) { entry ->
            DetailsScreen(name = entry.arguments?.getString("name"))

        }
    }
}