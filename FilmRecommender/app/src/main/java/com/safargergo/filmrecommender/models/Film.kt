package com.safargergo.filmrecommender.models

data class Film(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String
)