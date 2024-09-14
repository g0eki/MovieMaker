package com.firstapp.moviemaker.ui.elements

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.firstapp.moviemaker.R
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun Rating(rating: Double) {
    val maxStars = 5
    val filledStars = floor(rating).toInt()
    val unfilledStars = (maxStars - ceil(rating)).toInt()
    val halfStar = !(rating.rem(1).equals(0.0))

    Row {
        repeat(filledStars) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_star_32),
                contentDescription = "Rating Star Filled",
                tint = MaterialTheme.colorScheme.primary,
            )
        }
        if (halfStar) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_star_half_32),
                contentDescription = "Rating Star Filled Half",
                tint = MaterialTheme.colorScheme.primary
            )
        }
        repeat(unfilledStars) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_star_outline_32),
                contentDescription = "Rating Star Not Filled",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview
@Composable
fun RatingPreviewHalf() {
    Rating(2.5)
}

@Preview
@Composable
fun RatingPreviewFull() {
    Rating(2.0)
}