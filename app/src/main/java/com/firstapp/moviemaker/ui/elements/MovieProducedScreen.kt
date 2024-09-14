package com.firstapp.moviemaker.ui.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.firstapp.moviemaker.R
import com.firstapp.moviemaker.data.core.BasicOutput
import com.firstapp.moviemaker.data.core.GameData
import com.firstapp.moviemaker.data.core.Movie
import com.firstapp.moviemaker.data.core.OutputStrategy
import com.firstapp.moviemaker.data.core.SmartOutput
import com.firstapp.moviemaker.ui.MovieMakerViewModel

@Composable
fun MovieProducedScreen(
    viewModel: MovieMakerViewModel,
    navigateToStartScreen: () -> Unit,
    navigateToProduceMovieScreen: () -> Unit
) {
    val movie = viewModel.movieProduced ?: return
    MovieProduced(
        movie = movie,
        outputStrategy = viewModel.outputStrategy,
        onDismiss = viewModel::resetMovieProduced,
        onNavigateToStartScreen = {
            viewModel.resetMovieProduced()
            navigateToStartScreen()
        },
        onNavigateToProduceMovieScreen = {
            viewModel.resetMovieProduced()
            navigateToProduceMovieScreen()
        }
    )
}

@Composable
private fun MovieProduced(
    movie: Movie,
    outputStrategy: OutputStrategy,
    onDismiss: () -> Unit,
    onNavigateToStartScreen: () -> Unit,
    onNavigateToProduceMovieScreen: () -> Unit
) {
    AlertDialog(
        title = { Text(text = outputStrategy.introduction()) },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Description(movie = movie, outputStrategy = outputStrategy)
                Spacer(modifier = Modifier.height(8.dp))
                Profit(movie = movie)
                Spacer(modifier = Modifier.height(8.dp))
                Rating(rating = movie.averageRating)
                Spacer(modifier = Modifier.height(8.dp))
                Comment(movie = movie)
            }
        },
        confirmButton = {
            Row {
                Button(onClick = onNavigateToStartScreen) {
                    Text(text = "Hauptmenü")
                }
                Button(onClick = onNavigateToProduceMovieScreen) {
                    Text(text = "Nächster Film")
                }
            }
        },
        onDismissRequest = onDismiss
    )
}

@Composable
private fun Description(
    movie: Movie,
    outputStrategy: OutputStrategy
) {
    Text(
        text = outputStrategy.description(movie)
            .lines()
            .joinToString("\n") { it.trim() },
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.secondary,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun Profit(movie: Movie) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.height(IntrinsicSize.Min)
    ) {
        val iconId = if (movie.profit > 0) R.drawable.ic_profit else R.drawable.ic_loss
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = "Profit or Loss",
            tint = Color.Unspecified,
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1.0f)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = movie.profit.toString(),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
private fun Comment(movie: Movie) {
    Text(
        text = movie.comment,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.secondary
    )
}

@Preview
@Composable
fun MovieProducedSmartOutputPreview() {
    val movie = Movie(
        "TestMovie",
        GameData.directors.random(),
        GameData.actors.random(),
        50000,
        GameData.genres.random()
    )
    movie.produce()
    MovieProduced(
        movie = movie,
        outputStrategy = SmartOutput(),
        onDismiss = { },
        onNavigateToStartScreen = { },
        onNavigateToProduceMovieScreen = { }
    )
}

@Preview
@Composable
fun MovieProducedBasicOutputPreview() {
    val movie = Movie(
        "TestMovie",
        GameData.directors.random(),
        GameData.actors.random(),
        100000000,
        GameData.genres.random()
    )
    movie.produce()
    MovieProduced(
        movie = movie,
        outputStrategy = BasicOutput(),
        onDismiss = { },
        onNavigateToStartScreen = { },
        onNavigateToProduceMovieScreen = { }
    )
}