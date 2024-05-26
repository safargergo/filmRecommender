package com.safargergo.filmrecommender.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.safargergo.filmrecommender.models.FavoriteFilm
import com.safargergo.filmrecommender.models.Film
import com.safargergo.filmrecommender.ui.components.FilmItem
import com.safargergo.filmrecommender.viewmodel.FilmDetailsViewModel
import com.safargergo.filmrecommender.viewmodel.FilmViewModel
import kotlinx.coroutines.flow.map


@OptIn(ExperimentalCoilApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FilmDetailsScreen(
    viewModel: FilmDetailsViewModel = viewModel(), id: Int, onRecommendationClick: (Int) -> Unit
) {
    val film: Film? = viewModel.selected.collectAsState().value
    val recommendations: List<Film?> = viewModel.recommendedFilms.collectAsState().value

    if (film == null) {
        Text("Loading...", modifier = Modifier.fillMaxSize(), textAlign = TextAlign.Center)
    } else {

        /*Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Image(
                    painter = rememberImagePainter("https://image.tmdb.org/t/p/w500${film.poster_path}"),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = { /* TODO: Handle button click */ }) {
                        Text(text = "Button 1")
                    }
                    Button(onClick = { /* TODO: Handle button click */ }) {
                        Text(text = "Button 2")
                    }
                    Button(onClick = { /* TODO: Handle button click */ }) {
                        Text(text = "Button 3")
                    }
                }

                Text(
                    text = film.overview, fontSize = 16.sp, color = Color.Black
                )
            }

        }

        LazyColumn {
            items(recommendations) { film ->
                if (film != null) {
                    FilmPoster(
                        onClick = { /*TODO*/ },
                        imageUrl = film.poster_path,
                        placeholder = film.title.hashCode()
                    )
                }
            }
        }*/
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Image(
                painter = rememberImagePainter("https://image.tmdb.org/t/p/w500${film.poster_path}"),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = film.title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = film.overview,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Recommended Films",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                items(recommendations) { film ->
                    if (film != null) {
                        RecommendedFilmItem(film)
                    }
                }
            }
        }
    }
}

@Composable
fun FilmPoster(onClick: () -> Unit, imageUrl: String, placeholder: Int) {
    Box(
        modifier = Modifier
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = rememberImagePainter(
                data = imageUrl,
                builder = {
                    crossfade(true)
                    placeholder(placeholder)
                }
            ),
            contentDescription = "Film Poster",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun RecommendedFilmItem(film: Film) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .width(120.dp)
    ) {
        Image(
            painter = rememberImagePainter("https://image.tmdb.org/t/p/w500${film.poster_path}"),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )
        Text(
            text = film.title,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}
