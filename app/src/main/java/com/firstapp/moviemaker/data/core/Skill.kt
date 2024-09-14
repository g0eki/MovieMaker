package com.firstapp.moviemaker.data.core

import kotlin.random.Random

class Skill {
    val maxSkill = Random.nextInt(MAX_INIT, 400)
    var currentSkill = Random.nextInt(MIN_INIT, maxSkill)
        set(value) {
            if (value > field && value <= currentSkill) {
                field = value
            }
        }
    val learningSpeed = Random.nextInt(1, 10)

    companion object {
        const val MIN_INIT = 100
        const val MAX_INIT = 200
        var highestLevel = MIN_INIT // Die Eigenschaft ist nur intern veränderlich
            private set
    }
}