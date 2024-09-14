package com.firstapp.moviemaker.data.core

object GameData {
    val actors = mutableListOf<Actor>()
    val directors = mutableListOf<Director>()
    val genres = Genre.values()

    init {
        actors.add(Actor("Emma", "Thompson", getRandomGenres()))
        actors.add(Actor("John", "Goodman", getRandomGenres()))
        actors.add(Actor("Susi", "Sonnenschein", getRandomGenres()))
        actors.add(Actor("Fridolin", "Fröhlich", getRandomGenres()))

        directors.add(Director("Steven", "Spielberg", getRandomActor()))
        directors.add(Director("Roland", "Emmerich", getRandomActor()))
        directors.add(Director("Hanna", "Heiter", getRandomActor()))
        directors.add(Director("Lars", "Lustig", getRandomActor()))
    }

    val ratingStrategies = listOf<Rating>(
        RatingBasedOnExperience(),
        RatingBasedOnMatchingCast(),
        RandomRating(),
        Reviewer("Gregor", "Grimmig"),
        Rating { movie -> if (movie.budget > 200000) 5 else 3 }
    )

    var moneyOnAccount: Int = 1_000_000

    val movies: MutableList<Movie> = mutableListOf()

    var bestMovie: Movie? = null
        private set

    var outputStrategy: OutputStrategy = SmartOutput()

    fun getRandomGenres(): List<Genre> = listOf(genres.random(), genres.random())

    fun getRandomActor(): Actor = actors.random()

    fun getRandomGenre(): Genre = genres.random()

    fun addMovie(movie: Movie) {
        movies.add(movie)
        if (movie.revenue > (bestMovie?.revenue ?: 0)) {
            bestMovie = movie
        }
    }
}