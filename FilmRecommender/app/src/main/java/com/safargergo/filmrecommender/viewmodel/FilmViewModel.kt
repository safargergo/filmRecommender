package com.safargergo.filmrecommender.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.safargergo.filmrecommender.database.FilmDatabase
import com.safargergo.filmrecommender.models.FavoriteFilm
import com.safargergo.filmrecommender.models.Film
import com.safargergo.filmrecommender.repository.FilmRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FilmViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: FilmRepository
    private val _films = MutableStateFlow<List<Film>>(emptyList())
    val films: StateFlow<List<Film>> get() = _films
    private val _favorites = MutableStateFlow<List<FavoriteFilm>>(emptyList())
    val favorites: StateFlow<List<FavoriteFilm>> get() = _favorites

    init {
        val favoriteFilmDao = FilmDatabase.getDatabase(application).favoriteFilmDao()
        repository = FilmRepository(favoriteFilmDao)
        fetchFilms()
        fetchFavorites()
    }

    private fun fetchFilms() {
        viewModelScope.launch {
            val apiKey = "a24f6366d21c5ae9945d1b07179ba511" // Replace with your TMDB API Key
            val response = repository.getPopularMovies(apiKey, 1)
            _films.value = response.results
        }
    }

    private fun fetchFavorites() {
        viewModelScope.launch {
            repository.getFavorites().collect {
                _favorites.value = it
            }
        }
    }

    fun addFavorite(film: FavoriteFilm) {
        viewModelScope.launch {
            repository.addFavorite(film)
        }
    }

    fun removeFavorite(filmId: Int) {
        viewModelScope.launch {
            repository.removeFavorite(filmId)
        }
    }
}