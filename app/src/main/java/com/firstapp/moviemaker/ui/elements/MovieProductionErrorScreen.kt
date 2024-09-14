package com.firstapp.moviemaker.ui.elements

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.firstapp.moviemaker.ui.MovieMakerViewModel

@Composable
fun MovieProductionErrorScreen(viewModel: MovieMakerViewModel) {
    val errorMessage = viewModel.movieProductionError ?: return
    MovieProductionError(
        errorMessage = errorMessage,
        onDismiss = viewModel::resetMovieProductionError
    )
}

@Composable
private fun MovieProductionError(errorMessage: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Fehler beim produzieren eines Films") },
        text = { Text(text = errorMessage) },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text(text = "Okay")
            }
        }
    )
}

@Preview
@Composable
fun MovieProductionErrorPreview() {
    MovieProductionError("Oops, ein Fehler ist passiert") {}
}