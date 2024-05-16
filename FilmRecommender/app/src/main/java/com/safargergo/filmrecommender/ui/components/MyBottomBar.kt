package com.safargergo.filmrecommender.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun MyBottomBar() {
    BottomAppBar(
        containerColor = androidx.compose.material3.MaterialTheme.colorScheme.primaryContainer,
        contentColor = androidx.compose.material3.MaterialTheme.colorScheme.primary,
    ) {
        androidx.compose.material3.Text(
            "CineMemo",
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Cursive,
            fontSize = 32.sp,
            modifier = Modifier.fillMaxWidth()
        )
    }
}