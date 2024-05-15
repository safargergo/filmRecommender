package com.safargergo.filmrecommender

import android.app.Application
import androidx.room.Room
import com.safargergo.filmrecommender.database.FilmDatabase

class FilmApplication : Application() {

    companion object {
        lateinit var db: FilmDatabase
    }

    override fun onCreate() {
        super.onCreate()
        // Initialization logic here
        db = Room.databaseBuilder(
            applicationContext,
            FilmDatabase::class.java,
            "todo_database"
        ).fallbackToDestructiveMigration().build()
    }
}