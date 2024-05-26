package com.safargergo.filmrecommender.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.safargergo.filmrecommender.database.FilmDatabase
import com.safargergo.filmrecommender.models.Film
import com.safargergo.filmrecommender.repository.FilmRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Properties

class FilmDetailsViewModel(application: Application, filmId: Int) : AndroidViewModel(application) {
    private val repository: FilmRepository

    val selected: StateFlow<Film?> get() = _selected
    private val _selected = MutableStateFlow<Film?>(null)

    // Replace with your TMDB API Key
    private val _apiKey = "a24f6366d21c5ae9945d1b07179ba511"

    init {
        val favoriteFilmDao = FilmDatabase.getDatabase(application).favoriteFilmDao()
        repository = FilmRepository(favoriteFilmDao)
        fetchFilmDetails(filmId)
    }

    private fun fetchFilmDetails(filmId: Int) {
        viewModelScope.launch {
            Log.d("FilmDetailsViewModel", "Fetching film details for ID: $filmId")
            _selected.value = repository.getFilmById(_apiKey, filmId)
        }
    }
}