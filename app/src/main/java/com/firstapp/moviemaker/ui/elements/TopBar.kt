package com.firstapp.moviemaker.ui.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.firstapp.moviemaker.ui.MovieMakerViewModel

@Composable
fun TopBar(viewModel: MovieMakerViewModel = viewModel()) {
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        BudgetScreen(viewModel = viewModel)
    }
}