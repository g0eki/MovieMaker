package com.firstapp.moviemaker.data.core

interface OutputStrategy {
    fun introduction(): String
    fun description(movie: Movie): String
    fun analyze(movie: Movie): String
}

class DummyOutput : OutputStrategy {
    override fun introduction(): String = "Tada!"
    override fun description(movie: Movie): String = "Fertig."
    override fun analyze(movie: Movie): String = "Statistikausgabe-Platzhalter"
}

class BasicOutput : OutputStrategy {
    override fun introduction(): String = "*** Neuer Film fertig ***"

    override fun description(movie: Movie): String =
        "Der Film ${movie.title} von ${movie.director} mit ${movie.mainActor}"

    override fun analyze(movie: Movie): String =
        if (movie.profit > 0) "Gewinn: ${movie.profit}" else "Verlust: ${-movie.profit}"
}

class SmartOutput : OutputStrategy {
    override fun introduction(): String = "Wir präsentieren den neusten Film."

    override fun description(movie: Movie): String = """
        |${movie.title} ist ein ${movie.genre} Film.
        |In der Hauptrolle spielt ${movie.mainActor}
        |Regie führte ${movie.director}
        |Der Film hat ${movie.revenue} Dollar eingespielt.""".trimMargin()

    override fun analyze(movie: Movie): String {
        val info = StringBuilder()
        val genreMatch = movie.mainActor.genres.contains(movie.genre)
        info.appendLine("Schauspieler: ${movie.mainActor}")
        info.appendLine("Bevorzugter Schauspieler: ${movie.director.preferredActor}")
        info.appendLine("Genre des Films: ${movie.genre}")
        info.appendLine("Genres des Schauspielers: ${movie.mainActor.genres}")
        info.appendLine(if (genreMatch) "Genre passt" else "Genre passt nicht")
        info.appendLine("Ausgaben: ${movie.costs}")
        info.appendLine("Einnahmen ${movie.revenue}")
        return info.toString()
    }
}