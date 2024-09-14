package com.firstapp.moviemaker.data.core

import kotlin.random.Random

fun earnsMoreMoney(p1: Person, p2: Person): Person {
    return if (p1.salary > p2.salary) p1 else p2
}

abstract class Person(val firstName: String, val lastName: String) {
    var salary = Random.nextInt(100_000, 2_000_000)
    val skill = Skill()

    abstract fun increaseSalary()

    open fun movieSuccessfullyProduced() {
        skill.currentSkill += skill.learningSpeed
    }

    open fun movieSuccessfullyProduced(rating: Double) {
        skill.currentSkill += (skill.learningSpeed * rating).toInt()
    }

    override fun toString(): String = "$firstName $lastName"
}

class Actor(
    firstName: String,
    lastName: String,
    val genres: List<Genre>
) : Person(firstName, lastName) {
    override fun toString(): String = "Schauspieler*in ${super.toString()}"

    override fun movieSuccessfullyProduced() {
        if (skill.currentSkill > skill.maxSkill * 0.8) {
            salary += 20000
        }
    }

    override fun increaseSalary() {
        salary += 100
    }
}

class Director(
    firstName: String,
    lastName: String,
    val preferredActor: Actor
) : Person(firstName, lastName) {
    override fun toString(): String = "Regisseur*in ${super.toString()}"

    override fun movieSuccessfullyProduced() {
        if (skill.currentSkill < skill.maxSkill) {
            skill.currentSkill += 1
        }
    }

    override fun increaseSalary() {
        salary = (salary * 1.1).toInt()
    }
}