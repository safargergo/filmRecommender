package com.safargergo.filmrecommender.api

import com.safargergo.filmrecommender.models.Film
import com.safargergo.filmrecommender.models.FilmResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): FilmResponse

    @GET("movie/{id}")
    suspend fun getMovieById(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String,
    ): Film
}