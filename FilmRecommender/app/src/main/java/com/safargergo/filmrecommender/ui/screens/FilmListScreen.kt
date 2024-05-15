package com.safargergo.filmrecommender.ui.screens

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.safargergo.filmrecommender.FilmApplication
import com.safargergo.filmrecommender.models.FavoriteFilm
import com.safargergo.filmrecommender.models.Film
import com.safargergo.filmrecommender.R
import com.safargergo.filmrecommender.viewmodel.FilmViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmListScreen(viewModel: FilmViewModel = FilmViewModel(FilmApplication())) {
    val films = viewModel.films.collectAsState().value
    val favorites = viewModel.favorites.collectAsState().value

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = androidx.compose.material3.MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Outlined.AccountCircle,
                            contentDescription = null
                        )
                    }
                },
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        androidx.compose.material3.Text("CineMemo", fontFamily = FontFamily.Cursive)
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = androidx.compose.material3.MaterialTheme.colorScheme.primaryContainer,
                contentColor = androidx.compose.material3.MaterialTheme.colorScheme.primary,
            ) {
                androidx.compose.material3.Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Home"
                )
            }
        }
    ) {
        LazyColumn {
            items(films) { film ->
                FilmItem(
                    film = film,
                    isFavorite = favorites.any { it.id == film.id },
                    onFavoriteClick = { isFavorite ->
                        if (isFavorite) {
                            viewModel.addFavorite(
                                FavoriteFilm(
                                    film.id,
                                    film.title,
                                    film.overview,
                                    film.poster_path
                                )
                            )
                        } else {
                            viewModel.removeFavorite(film.id)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun FilmItem(
    film: Film,
    isFavorite: Boolean,
    onFavoriteClick: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { }
    ) {
        Image(
            painter = rememberImagePainter("https://image.tmdb.org/t/p/w500${film.poster_path}"),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.CenterVertically),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = film.title,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Start
            )
            Text(
                text = film.overview,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                textAlign = TextAlign.Start
            )
        }
        IconButton(
            onClick = { onFavoriteClick(!isFavorite) }
        ) {
            Icon(
                painter = if (isFavorite) painterResource(id = R.drawable.ic_favorite_filled) else painterResource(
                    id = R.drawable.ic_favorite_border
                ),
                contentDescription = null
            )
        }
    }
}