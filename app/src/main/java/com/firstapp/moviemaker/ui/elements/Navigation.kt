package com.firstapp.moviemaker.ui.elements

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.firstapp.moviemaker.ui.MovieMakerViewModel

import android.util.Log
import androidx.compose.runtime.mutableIntStateOf


var cnt = mutableIntStateOf(1)

@Composable
fun Navigation(viewModel: MovieMakerViewModel = viewModel()) {
    val navController = rememberNavController()


    Log.i("Navigation", "Navigation: ${cnt.value}")

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
    cnt.value = cnt.value + 1
}