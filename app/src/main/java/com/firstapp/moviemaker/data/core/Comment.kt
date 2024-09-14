package com.firstapp.moviemaker.data.core

interface Comment {
    fun goodMovie(): String
    fun badMovie(): String
}

class BaseComment : Comment {
    override fun goodMovie(): String = "Ein toller Film."
    override fun badMovie(): String = "Ein mieser Film."
}

class RandomComment : Comment {
    override fun goodMovie(): String = goodBadPairs.random().first
    override fun badMovie(): String = goodBadPairs.random().second

    companion object {
        val goodBadPairs = listOf<Pair<String, String>>(
            Pair("Ein Meisterwerk", "Ein glatte Fehlleistung"),
            Pair("Wundervoller Film", "Furchtbarer Film"),
            Pair("Ganz großartiger Film", "Ganz schlechter Film"),
            Pair("Herrliches Kinoerlebnis", "Nicht sein Geld wert")
        )
    }
}

class StarDecorator(val stars: String, val base: BaseComment) : Comment {
    override fun goodMovie(): String = stars + base.goodMovie() + stars
    override fun badMovie(): String = stars + base.badMovie() + stars
}

class MaxLength(val max: Int, val decoratedComment: Comment) : Comment {
    override fun goodMovie(): String =
        decoratedComment.goodMovie().substring(0, max)

    override fun badMovie(): String =
        decoratedComment.badMovie().substring(0, max)
}

class PraiseDecorator(val decoratedComment: Comment) : Comment by decoratedComment {
    private val praise = listOf("Wow!", "Super!", "Klasse!", "Yay!")

    override fun goodMovie(): String =
        "${praise.random()} ${decoratedComment.goodMovie()}"
}