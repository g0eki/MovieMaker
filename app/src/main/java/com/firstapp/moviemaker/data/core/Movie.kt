package com.firstapp.moviemaker.data.core

import kotlin.random.Random

fun format(double: Double): String = "%.2f".format(double)

class Movie(
    val title: String,
    val director: Director,
    val mainActor: Actor,
    val budget: Int,
    val genre: Genre
) {
    private val ratings = mutableListOf<Double>()

    val costs = director.salary + mainActor.salary + budget

    var revenue = 0
        private set

    var profit = 0
        private set

    private val commentGenerator: Comment = PraiseDecorator(BaseComment())

    val comment: String
        get() = if (ratings.sum() / ratings.size > 5) commentGenerator.goodMovie()
        else commentGenerator.badMovie()

    val averageRating
        get() = ratings.average()

    fun produce() {
        println("Produziere: $title, Gesamtkosten: $costs")

        val baseRevenue = Random.nextInt(1_000_000, 2_000_0000)

        rate()

        var totalRevenue = 0

        for (rating in ratings) {
            val additionalRevenue = (baseRevenue * rating).toInt()
            totalRevenue += additionalRevenue
            println("Film wurde bewertet mit ${format(rating)} Punkten. Einnahmen:$additionalRevenue")
        }

        this.revenue = totalRevenue

        println("Gesamteinnahmen des Films: $revenue")

        profit = revenue - costs
        if (profit >= 0) {
            println("Gewinn: ${profit}")
        } else {
            println("Verlust: ${-profit}")
        }
        mainActor.movieSuccessfullyProduced()
        director.movieSuccessfullyProduced()
    }

    fun rate() {
        for (x in 1..5) { // fünf Bewertungen erzeugen
            val rating = Random.nextDouble(0.0, 5.0) // Zufallswert zwischen 0.0 und 5.0
            ratings.add(rating)
        }
    }
}