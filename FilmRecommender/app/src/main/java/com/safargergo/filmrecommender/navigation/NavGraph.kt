package com.safargergo.filmrecommender.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityOptionsCompat
import androidx.navigation.ActivityNavigatorExtras
import androidx.navigation.NavDirections
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.Navigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.safargergo.filmrecommender.FilmApplication
import com.safargergo.filmrecommender.ui.screens.FavoritesScreen
import com.safargergo.filmrecommender.ui.screens.FilmDetailsScreen
import com.safargergo.filmrecommender.ui.screens.FilmListScreen
import com.safargergo.filmrecommender.viewmodel.FilmDetailsViewModel
import com.safargergo.filmrecommender.viewmodel.FilmViewModel

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            FilmListScreen(FilmViewModel(FilmApplication()),
                onHomeNavigateClick = {},
                onFavNavigateClick = {
                    navController.popBackStack()
                    navController.navigate("favorites")
                })
        }
        composable("favorites") {
            FavoritesScreen(FilmViewModel(FilmApplication()), onHomeNavigateClick = {
                navController.popBackStack()
                navController.navigate("home")
            }, onFavNavigateClick = { }, onDetailsClick = {
                navController.navigate("filmDetails/${it.id}")
            })
        }
        composable(
            route = "filmDetails/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.IntType
            }),
        ) {
            //val arguments = it.arguments?.getInt("id").toString()
            //Log.d("Arguments", arguments)
            it.arguments?.getInt("id")?.let { it1 ->
                FilmDetailsScreen(
                    viewModel = FilmDetailsViewModel(FilmApplication(), it1, true),
                    id = it1,
                    onRecommendationClick = { recommendationID ->
                        navController.navigate("filmDetails/${it1}/${recommendationID}")
                    }
                )
            }
        }

        composable(
            route = "filmDetails/{id}/{recommendationID}",
            arguments = listOf(navArgument("id") {
                type = NavType.IntType
            }, navArgument("recommendationID") {
                type = NavType.IntType
            }),
        ) {
            it.arguments?.getInt("recommendationID")?.let { it1 ->
                FilmDetailsScreen(
                    viewModel = FilmDetailsViewModel(FilmApplication(), it1, false),
                    id = it1,
                    onRecommendationClick = {}
                )
            }
        }
    }
}