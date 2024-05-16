package com.safargergo.filmrecommender.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.safargergo.filmrecommender.FilmApplication
import com.safargergo.filmrecommender.ui.screens.FavoritesScreen
import com.safargergo.filmrecommender.ui.screens.FilmListScreen
import com.safargergo.filmrecommender.viewmodel.FilmViewModel

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            FilmListScreen(
                FilmViewModel(FilmApplication()),
                onHomeNavigateClick = {},
                onFavNavigateClick = {
                    navController.navigate("favorites")
                    navController.popBackStack()
                }
            )
        }
        composable("favorites") {
            FavoritesScreen(
                FilmViewModel(FilmApplication()),
                onHomeNavigateClick = {
                    navController.navigate("home")
                    navController.popBackStack()
                },
                onFavNavigateClick = {}
            )
        }
    }
}