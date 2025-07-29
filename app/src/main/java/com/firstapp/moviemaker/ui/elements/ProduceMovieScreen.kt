package com.firstapp.moviemaker.ui.elements

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.firstapp.moviemaker.R
import com.firstapp.moviemaker.data.core.GameData
import com.firstapp.moviemaker.data.core.Genre
import com.firstapp.moviemaker.data.core.Person
import com.firstapp.moviemaker.ui.MovieMakerViewModel
import com.firstapp.moviemaker.data.core.Actor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProduceMovieScreen(viewModel: MovieMakerViewModel = viewModel()) {
    val maxBudget by viewModel.budget.collectAsState(initial = 0)
    var title by remember { mutableStateOf("") }
    var budget by remember { mutableStateOf(0) }
    val actorPager = rememberPagerState(pageCount = {viewModel.actors.size})
    val directorPager = rememberPagerState(pageCount = {viewModel.directors.size})
    val genrePager = rememberPagerState(pageCount = {viewModel.genres.size})

    ProduceMovie(
        title = title,
        onTitleChange = { title = it },
        actorPager = actorPager,
        actors = viewModel.actors,
        getImageForPerson = viewModel::getImageFor,
        directorPager = directorPager,
        directors = viewModel.directors,
        genrePager = genrePager,
        genres = viewModel.genres,
        getImageForGenre = viewModel::getImageFor,
        currentBudget = budget,
        maxBudget = maxBudget,
        onBudgetChange = { budget = it },
        onProduceMovie = {
            viewModel.produceMovie(
                title,
                actorPager.currentPage,
                genrePager.currentPage,
                directorPager.currentPage,
                budget
            )
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ProduceMovie(
    title: String,
    onTitleChange: (String) -> Unit,
    actorPager: PagerState,
    actors: List<Person>,
    getImageForPerson: (Person) -> Int,
    directorPager: PagerState,
    directors: List<Person>,
    genrePager: PagerState,
    genres: List<Genre>,
    getImageForGenre: (Genre) -> Int,
    currentBudget: Int,
    maxBudget: Int,
    onBudgetChange: (Int) -> Unit,
    onProduceMovie: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HeaderText()
        TitleTextField(
            title = title,
            onTitleChange = onTitleChange
        )
        PeoplePager(
            title = "Hauptdarsteller",
            pagerState = actorPager,
            people = actors,
            getImageFor = getImageForPerson
        )
        GenrePage(
            pagerState = genrePager,
            genres = genres,
            getImageFor = getImageForGenre
        )
        PeoplePager(
            title = "Regisseur",
            pagerState = directorPager,
            people = directors,
            getImageFor = getImageForPerson
        )
        BudgetSlider(
            currentBudget = currentBudget,
            maxBudget = maxBudget,
            onBudgetChange = onBudgetChange
        )
        ProduceMovieButton(onProduceMovie = onProduceMovie)
    }
}

@Composable
private fun BudgetSlider(
    currentBudget: Int,
    maxBudget: Int,
    onBudgetChange: (Int) -> Unit
) {
    Column(
        modifier = Modifier.padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Budget",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = currentBudget.toString(),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.secondary
        )
        Slider(
            value = currentBudget.toFloat(),
            onValueChange = { onBudgetChange(it.toInt()) },
            valueRange = (0f..maxBudget.toFloat()),
        )
    }
}

@Composable
private fun HeaderText() {
    Text(
        text = "Neuer Film",
        color = MaterialTheme.colorScheme.secondary,
        style = MaterialTheme.typography.displayLarge,
        modifier = Modifier.padding(top = 32.dp)
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun GenrePage(
    pagerState: PagerState,
    genres: List<Genre>,
    getImageFor: (Genre) -> Int
) {
    PagerView(
        title = "Genre",
        pagerState = pagerState,
        entries = genres
    ) { genre ->
        Card(
            Modifier.size(width = 250.dp, height = 200.dp),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(getImageFor(genre)),
                    contentDescription = "Genre",
                    modifier = Modifier
                        .fillMaxHeight(0.7f)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Fit,
                )
                Text(
                    text = genre.prettyName,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun <A> PagerView(
    title: String,
    pagerState: PagerState,
    entries: List<A>,
    content: @Composable (A) -> Unit
) {
    Column(
        modifier = Modifier.padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.displaySmall
        )
        HorizontalPager(
            modifier = Modifier.padding(top = 8.dp),
            state = pagerState,
            pageSpacing = 16.dp,
            // pageCount = entries.size,
            contentPadding = PaddingValues(horizontal = 45.dp)
        ) { page ->
            content(entries[page])
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PeoplePager(
    title: String,
    pagerState: PagerState,
    people: List<Person>,
    getImageFor: (Person) -> Int
) {
    PagerView(
        title = title,
        pagerState = pagerState,
        entries = people
    ) { person ->
        Card(
            Modifier.size(250.dp),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(getImageFor(person)),
                    contentDescription = "???",
                    modifier = Modifier
                        .fillMaxHeight(0.7f)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop,
                )
                Text(
                    text = "${person.firstName} ${person.lastName}",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_coin),
                        contentDescription = "Money",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .fillMaxHeight()
                            .aspectRatio(0.6f)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = person.salary.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}

@Composable
private fun ProduceMovieButton(onProduceMovie: () -> Unit) {
    Button(
        modifier = Modifier.padding(top = 16.dp),
        onClick = onProduceMovie,
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.primary,
        )
    ) {
        Text(
            text = "Produktion abschließen",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview
@Composable
fun ProduceMovieButtonPreview(){
    ProduceMovieButton(onProduceMovie = {})
}



@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TitleTextField(title: String, onTitleChange: (String) -> Unit) {
    OutlinedTextField(
        value = title,
        onValueChange = onTitleChange,
        label = { Text(text = "Titel") },
        textStyle = MaterialTheme.typography.bodyLarge,
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Preview(widthDp = 300, heightDp = 150)
@Composable
fun BudgetSliderPreview() {
    var currentBudget by remember { mutableStateOf(0) }
    BudgetSlider(
        currentBudget = currentBudget,
        maxBudget = 5000,
        onBudgetChange = { currentBudget = it }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(widthDp = 300, heightDp = 300)
@Composable
fun GenrePagerPreview() {
    // Hole das ViewModel innerhalb eines @Composable Kontexts

    val pagerState = rememberPagerState(pageCount = {GameData.genres.size})
    val genres = GameData.genres.toList()

    GenrePage(
        pagerState = pagerState,
        genres = genres,
        getImageFor = { R.drawable.genre_action }
    )
}

