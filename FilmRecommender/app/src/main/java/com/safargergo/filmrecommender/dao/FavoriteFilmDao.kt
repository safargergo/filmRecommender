package com.safargergo.filmrecommender.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.safargergo.filmrecommender.models.FavoriteFilm
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteFilmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(film: FavoriteFilm)

    //@Query("DELETE FROM favorite_films WHERE id = :filmId")
    //suspend fun deleteById(filmId: Int): Int

    @Query("SELECT * FROM favorite_films")
    fun getAllFavorites(): Flow<List<FavoriteFilm>>
}