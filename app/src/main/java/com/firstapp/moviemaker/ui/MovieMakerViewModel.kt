package com.firstapp.moviemaker.ui

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.firstapp.moviemaker.R
import com.firstapp.moviemaker.data.core.Actor
import com.firstapp.moviemaker.data.core.Director
import com.firstapp.moviemaker.data.core.GameData
import com.firstapp.moviemaker.data.core.Genre
import com.firstapp.moviemaker.data.core.Movie
import com.firstapp.moviemaker.data.core.OutputStrategy
import com.firstapp.moviemaker.data.core.Person
import com.firstapp.moviemaker.data.storage.MovieMakerStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

import android.util.Log

class MovieMakerViewModel(application: Application) : AndroidViewModel(application) {
    private val store = MovieMakerStore(application, initial = GameData.moneyOnAccount)

    private var movieProductionErrorState = mutableStateOf<String?>(null)
    private var movieProducedState = mutableStateOf<Movie?>(null)

    // Public API: State

    val movieProductionError: String?
        get() = movieProductionErrorState.value

    val movieProduced: Movie?
        get() = movieProducedState.value

    val budget: Flow<Int>
        get() = store.budget

    val actors: List<Actor>
        get() = GameData.actors

    val directors: List<Director>
        get() = GameData.directors

    val genres: List<Genre>
        get() = GameData.genres.toList()

    val outputStrategy: OutputStrategy
        get() = GameData.outputStrategy

    // Public API: User-Events

    fun resetMovieProductionError() {
        movieProductionErrorState.value = null
    }

    fun resetMovieProduced() {
        movieProducedState.value = null
    }

    fun produceMovie(
        title: String,
        actorIndex: Int,
        genreIndex: Int,
        directorIndex: Int,
        budget: Int,
    ) {
        if (title.isEmpty() || budget <= 0) {
            movieProductionErrorState.value = "Titel angeben oder Budget setzen"
            return
        }
        val movie =
            Movie(title, directors[directorIndex], actors[actorIndex], budget, genres[genreIndex])
        Log.i("Moive-Debug", movie.toString())
        Log.i("Moive-Debug", movieProducedState.toString())
        Log.i("Moive-Debug", movieProducedState.value.toString())
        movie.produce()
        movieProducedState.value = movie
        Log.i("Moive-Debug", movieProducedState.value.toString())
        updateBudget(movie.budget)
    }

    private fun updateBudget(newBudget: Int) {
        viewModelScope.launch {
            store.incrementBudget(newBudget)
        }
    }

    fun getImageFor(person: Person): Int =
        when ("${person.firstName} ${person.lastName}") {
            "Emma Thompson" -> R.drawable.person_emma_thompson
            "Susi Sonnenschein" -> R.drawable.person_susi_sonnenschein
            "Hanna Heiter" -> R.drawable.person_hanna_heiter
            "John Goodman" -> R.drawable.person_john_goodman
            "Fridolin Fröhlich" -> R.drawable.person_fridolin_froehlich
            "Steven Spielberg" -> R.drawable.person_steven_spielberg
            "Roland Emmerich" -> R.drawable.person_roland_emmerich
            "Lars Lustig" -> R.drawable.person_lars_lustig
            else -> 0
        }

    fun getImageFor(genre: Genre): Int =
        when (genre) {
            Genre.ACTION -> R.drawable.genre_action
            Genre.ADVENTURE -> R.drawable.genre_adventure
            Genre.COMEDY -> R.drawable.genre_comedy
            Genre.CRIME -> R.drawable.genre_crime
            Genre.DOCUMENTATION -> R.drawable.genre_documentary
            Genre.DRAMA -> R.drawable.genre_drama
            Genre.HORROR -> R.drawable.genre_horror
            Genre.ROMANCE -> R.drawable.genre_romance
            Genre.FANTASY -> R.drawable.genre_fantasy
            Genre.THRILLER -> R.drawable.genre_thriller
        }
}