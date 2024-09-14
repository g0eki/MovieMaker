package com.firstapp.moviemaker.ui.elements

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.firstapp.moviemaker.ui.MovieMakerViewModel

@Composable
fun Navigation(viewModel: MovieMakerViewModel = viewModel()) {
    val navController = rememberNavController()

    MovieProductionErrorScreen(viewModel)

    MovieProducedScreen(
        viewModel,
        navigateToStartScreen = { navController.navigate("start-screen") },
        navigateToProduceMovieScreen = { navController.navigate("produce-movie-screen") }
    )

    NavHost(navController = navController, startDestination = "start-screen") {
        composable("start-screen") {
            StartScreen {
                navController.navigate("produce-movie-screen")
            }
        }
        composable("produce-movie-screen") {
            ProduceMovieScreen(viewModel)
        }
    }
}