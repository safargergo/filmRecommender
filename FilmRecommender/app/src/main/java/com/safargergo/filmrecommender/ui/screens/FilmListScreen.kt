package com.safargergo.filmrecommender.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.safargergo.filmrecommender.FilmApplication
import com.safargergo.filmrecommender.models.FavoriteFilm
import com.safargergo.filmrecommender.ui.components.FilmItem
import com.safargergo.filmrecommender.ui.components.MyBottomBar
import com.safargergo.filmrecommender.ui.components.MyTopAppBar
import com.safargergo.filmrecommender.viewmodel.FilmViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmListScreen(
    viewModel: FilmViewModel = FilmViewModel(FilmApplication()),
    onHomeNavigateClick: () -> Unit,
    onFavNavigateClick: () -> Unit
) {
    val films = viewModel.films.collectAsState().value
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
                    title = "Home"
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
    }
}

