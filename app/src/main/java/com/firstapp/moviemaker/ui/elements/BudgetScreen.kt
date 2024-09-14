package com.firstapp.moviemaker.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.firstapp.moviemaker.R
import com.firstapp.moviemaker.ui.MovieMakerViewModel

@Composable
fun BudgetScreen(viewModel: MovieMakerViewModel) {
    val budget by viewModel.budget.collectAsState(initial = 0)
    Budget(budget = budget)
}

@Composable
fun Budget(budget: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(50.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(start = 8.dp, end = 16.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_coins),
            contentDescription = "Current Budget",
            tint = Color.Unspecified,
            modifier = Modifier
                .aspectRatio(1.0f) // 1:1 w und h
                .padding(end = 8.dp)
        )
        Text(
            text = budget.toString(),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

@Composable
@Preview(widthDp = 200, heightDp = 100)
fun BudgetPreview() {
    Budget(budget = 50_000)
}