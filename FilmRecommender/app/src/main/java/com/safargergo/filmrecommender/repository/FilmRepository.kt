package com.safargergo.filmrecommender.repository

import android.util.Log
import com.safargergo.filmrecommender.api.RetrofitInstance
import com.safargergo.filmrecommender.dao.FavoriteFilmDao
import com.safargergo.filmrecommender.models.FavoriteFilm
import com.safargergo.filmrecommender.models.Film
import com.safargergo.filmrecommender.models.FilmResponse
import kotlinx.coroutines.flow.Flow

class FilmRepository(private val favoriteFilmDao: FavoriteFilmDao) {
    suspend fun getPopularMovies(apiKey: String, page: Int): FilmResponse {
        return RetrofitInstance.api.getPopularMovies(apiKey, page)
    }

    suspend fun addFavorite(film: FavoriteFilm) {
        favoriteFilmDao.insert(film)
    }

    suspend fun removeFavorite(filmId: Int) {
        return favoriteFilmDao.deleteById(filmId)
    }

    fun getFavorites(): Flow<List<FavoriteFilm>> {
        return favoriteFilmDao.getAllFavorites()
    }

    fun getFavoriteById(filmId: Int): Flow<FavoriteFilm?> {
        return favoriteFilmDao.getFavoriteById(filmId)
    }

    suspend fun getFilmById(apiKey: String, filmId: Int): Film {
        val response = RetrofitInstance.api.getMovieById(filmId, apiKey)
        Log.d("FilmRepository", "getFilmById: $response")
        return response
    }
}