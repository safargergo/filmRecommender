package com.safargergo.filmrecommender.database

import android.content.Context
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.safargergo.filmrecommender.FilmApplication
import com.safargergo.filmrecommender.dao.FavoriteFilmDao
import com.safargergo.filmrecommender.models.FavoriteFilm

@Database(entities = [FavoriteFilm::class], version = 1)
abstract class FilmDatabase : RoomDatabase() {
    abstract fun favoriteFilmDao(): FavoriteFilmDao

    companion object {
        @Volatile
        private var INSTANCE: FilmDatabase? = null

        fun getDatabase(context: Context): FilmDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = FilmApplication.db
                INSTANCE = instance
                instance
            }
        }
    }
}