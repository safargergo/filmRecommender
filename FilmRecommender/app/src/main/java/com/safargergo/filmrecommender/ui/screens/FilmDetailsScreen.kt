package com.safargergo.filmrecommender.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.safargergo.filmrecommender.models.Film
import com.safargergo.filmrecommender.viewmodel.FilmDetailsViewModel
import com.safargergo.filmrecommender.viewmodel.FilmViewModel
import kotlinx.coroutines.flow.map


@OptIn(ExperimentalCoilApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FilmDetailsScreen(
    viewModel: FilmDetailsViewModel = viewModel(),
    id: Int
) {
    val film: Film? = viewModel.selected.collectAsState().value

    if (film == null) {
        Text("Loading...", modifier = Modifier.fillMaxSize(), textAlign = TextAlign.Center)
    } else {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Image(
                    painter = rememberImagePainter("https://image.tmdb.org/t/p/w500${film.poster_path}"),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = { /* TODO: Handle button click */ }) {
                        Text(text = "Button 1")
                    }
                    Button(onClick = { /* TODO: Handle button click */ }) {
                        Text(text = "Button 2")
                    }
                    Button(onClick = { /* TODO: Handle button click */ }) {
                        Text(text = "Button 3")
                    }
                }

                Text(
                    text = film.overview,
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
        }
    }
}