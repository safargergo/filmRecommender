package com.safargergo.filmrecommender.ui.components

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.safargergo.filmrecommender.models.Film
import com.safargergo.filmrecommender.R

@Composable
fun FilmItem(
    film: Film,
    isFavorite: Boolean,
    onFavoriteClick: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { }
    ) {
        Image(
            painter = rememberImagePainter("https://image.tmdb.org/t/p/w500${film.poster_path}"),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.CenterVertically),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = film.title,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Start
            )
            Text(
                text = film.overview,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                textAlign = TextAlign.Start
            )
        }
        IconButton(
            onClick = { onFavoriteClick(!isFavorite) }
        ) {
            Icon(
                painter = if (isFavorite) painterResource(id = R.drawable.ic_favorite_filled) else painterResource(
                    id = R.drawable.ic_favorite_border
                ),
                contentDescription = null
            )
        }
    }
}