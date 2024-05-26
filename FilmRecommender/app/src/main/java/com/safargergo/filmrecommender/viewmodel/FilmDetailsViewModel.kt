package com.safargergo.filmrecommender.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.safargergo.filmrecommender.api.GeminiApiService
import com.safargergo.filmrecommender.api.GeminiResponse
import com.safargergo.filmrecommender.database.FilmDatabase
import com.safargergo.filmrecommender.models.Film
import com.safargergo.filmrecommender.repository.FilmRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Properties

class FilmDetailsViewModel(application: Application, filmId: Int, shouldRecommend: Boolean) : AndroidViewModel(application) {
    private val repository: FilmRepository

    val selected: StateFlow<Film?> get() = _selected
    private val _selected = MutableStateFlow<Film?>(null)

    // Replace with your TMDB API Key
    private val _apiKey = "a24f6366d21c5ae9945d1b07179ba511"

    //val geminiResponse: StateFlow<GeminiResponse?> get() = _geminiResponse
    //private val _geminiResponse = MutableStateFlow<GeminiResponse?>(null)

    val recommendedFilms: StateFlow<List<Film?>> get() = _recommendedFilms
    private val _recommendedFilms = MutableStateFlow<List<Film?>>(emptyList())

    init {
        val favoriteFilmDao = FilmDatabase.getDatabase(application).favoriteFilmDao()
        repository = FilmRepository(favoriteFilmDao)
        fetchFilmDetails(filmId)
        if (shouldRecommend) {
            askRecommendationsFromGemini(filmId)
        } else {
            _recommendedFilms.value = emptyList()
        }
    }

    private fun fetchFilmDetails(filmId: Int) {
        viewModelScope.launch {
            Log.d("FilmDetailsViewModel", "Fetching film details for ID: $filmId")
            _selected.value = repository.getFilmById(_apiKey, filmId)
        }
    }

    private fun askRecommendationsFromGemini(filmId: Int) {
        viewModelScope.launch {
            val title = getFilmDetails(filmId).title
            val response = GeminiApiService().generateContent(filmId, title)
            var recommendedFilms: List<Film> = mutableListOf()
            for (recommendation in response.idList) {
                try {
                    recommendedFilms = recommendedFilms + getFilmDetails(recommendation)
                } catch (e: Exception) {
                    Log.e("FilmDetailsViewModel", "Error fetching film details: ${e.message}")
                }
            }
            _recommendedFilms.value = recommendedFilms
        }
    }

    private suspend fun getFilmDetails(filmId: Int): Film {
        return repository.getFilmById(_apiKey, filmId)
    }
}