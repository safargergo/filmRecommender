package com.safargergo.filmrecommender.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.safargergo.filmrecommender.models.FavoriteFilm
import com.safargergo.filmrecommender.ui.components.MyBottomBar
import com.safargergo.filmrecommender.ui.components.MyTopAppBar
import com.safargergo.filmrecommender.viewmodel.FilmViewModel
import kotlinx.coroutines.launch

@Composable
fun FavoritesScreen(
    viewModel: FilmViewModel = viewModel(),
    onHomeNavigateClick: () -> Unit,
    onFavNavigateClick: () -> Unit
) {
    val favorites = viewModel.favorites.collectAsState().value

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(150.dp)
            ) {
                Text(
                    "Cine\nMemo",
                    modifier = Modifier.padding(32.dp),
                    fontFamily = FontFamily.Cursive,
                    fontSize = 32.sp
                )
                Divider()
                NavigationDrawerItem(
                    label = { Text(text = "Home") },
                    selected = false,
                    onClick = onHomeNavigateClick
                )
                NavigationDrawerItem(
                    label = { Text(text = "Favorites") },
                    selected = false,
                    onClick = onFavNavigateClick
                )
            }
        },
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                MyTopAppBar(
                    onNavIconClick = {
                        scope.launch { drawerState.open() }
                    },
                    title = "Favorites"
                )
            },
            topBar = {
                MyBottomBar()
            }
        ) {
            Surface(
                modifier = Modifier.padding(top = it.calculateTopPadding())
            ) {
                LazyColumn {
                    items(favorites) { film ->
                        FavoriteFilmItem(film)
                    }
                }
            }
        }
    }
}

@Composable
fun FavoriteFilmItem(film: FavoriteFilm) {
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
    }
}