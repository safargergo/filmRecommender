package com.safargergo.filmrecommender.api

import android.util.Log
import com.google.ai.client.generativeai.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel

class GeminiApiService {
    private val generativeModel = GenerativeModel(
        // The Gemini 1.5 models are versatile and work with both text-only and multimodal prompts
        modelName = "gemini-1.5-flash",
        // Access your API key as a Build Configuration variable (see "Set up your API key" above)
        apiKey = "GEMINI_API_KEY" // Replace with your API key
    )

    suspend fun generateContent(filmId: Int, filmTitle: String): GeminiResponse {
        //val prompt1 =
        //    "Please recommend me 5 other films like the film available on TMDB API with id $filmId! On the response only show the title of the films and the TMDB API ids of them! Dont show unnecessary texts! Show each film in new line and format the lines like: title;id"

        val prompt = "I have seen a film whit TMDB id = $filmId and tile = $filmTitle and i really like that." +
                " Please recommend me 5 more films like $filmTitle! " +
                "On the response only show the title of the films and the TMDB API ids of them! " +
                "Dont show unnecessary texts! Show each film in new line and format the lines like: title;id"

        val response = generativeModel.generateContent(prompt)
        Log.d("GeminiApiService", "Response:      $response")
        Log.d("GeminiApiService", "Response.text: ${response.text}")
        val idList = extractIds(response.text ?: "")
        Log.d("GeminiApiService", "idList: $idList")
        return GeminiResponse(response.text ?: "", idList)
    }

    private fun extractIds(input: String): List<Int> {
        return input.lines() // Split the input string into lines
            .filter { it.isNotBlank() } // Filter out any blank lines
            .map { line ->
                line.trim() // Trim leading and trailing whitespace
                    .split(";") // Split by semicolon
                    .last() // Take the last part (the ID)
                    .trim() // Trim any extra whitespace around the ID
                    .toInt() // Convert the ID to an integer
            }
    }
}

class GeminiResponse(val text: String, val idList: List<Int>) {
}