package com.safargergo.filmrecommender.repository

import com.safargergo.filmrecommender.api.RetrofitInstance
import com.safargergo.filmrecommender.models.FilmResponse

class FilmRepository {
    suspend fun getPopularMovies(apiKey: String, page: Int): FilmResponse {
        return RetrofitInstance.api.getPopularMovies(apiKey, page)
    }
}