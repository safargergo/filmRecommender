package com.safargergo.filmrecommender.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_films")
data class FavoriteFilm(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String
)