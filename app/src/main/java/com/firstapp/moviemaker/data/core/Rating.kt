package com.firstapp.moviemaker.data.core

import kotlin.random.Random

fun interface Rating {
    fun getScore(movie: Movie): Int
    fun getInfoText(movie: Movie): String =
        if (getScore(movie) > 5)
            "Toller Film"
        else
            "Mieser Film"
}

class RatingBasedOnMatchingCast : Rating {
    override fun getScore(movie: Movie): Int {
        var score = 0
        if (movie.mainActor.genres.contains(movie.genre)) {
            score += 5
        }
        if (movie.director.preferredActor == movie.mainActor) {
            score += 5
        }
        return score
    }
}

class RatingBasedOnExperience : Rating {
    override fun getScore(movie: Movie): Int =
        movie.mainActor.skill.currentSkill + movie.director.skill.currentSkill

    override fun getInfoText(movie: Movie): String {
        val score = getScore(movie)
        return when {
            score > 7 -> "Toller Film"
            score > 4 -> "Mittelmäßiger Film"
            score <= 4 -> "Schlechter film"
            else -> "Kein Kommentar"
        }
    }
}

class Reviewer(firstName: String, lastName: String) : Person(firstName, lastName), Rating {
    private val preferredActor = GameData.getRandomActor()
    private var mood = 1

    override fun increaseSalary() {
        mood += 1
    }

    override fun getScore(movie: Movie): Int {
        val baseScore = if (movie.mainActor == preferredActor) 5 else 3
        return baseScore * mood
    }
}

class RandomRating : Rating {
    val randomScore = Random.nextInt(0, 20)

    override fun getScore(movie: Movie): Int = randomScore
}