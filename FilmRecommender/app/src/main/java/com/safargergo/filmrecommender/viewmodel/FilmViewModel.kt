package com.safargergo.filmrecommender.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safargergo.filmrecommender.models.Film
import com.safargergo.filmrecommender.repository.FilmRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FilmViewModel : ViewModel() {
    private val repository = FilmRepository()
    private val _films = MutableStateFlow<List<Film>>(emptyList())
    val films: StateFlow<List<Film>> get() = _films

    init {
        fetchFilms()
    }

    private fun fetchFilms() {
        viewModelScope.launch {
            val apiKey = "a24f6366d21c5ae9945d1b07179ba511" // Replace with your TMDB API Key
            val response = repository.getPopularMovies(apiKey, 1)
            _films.value = response.results
        }
    }
}