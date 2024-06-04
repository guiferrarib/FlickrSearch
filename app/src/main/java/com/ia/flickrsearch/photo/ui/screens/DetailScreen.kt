package com.ia.flickrsearch.photo.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.ia.flickrsearch.photo.data.model.FlickrPhoto

/**
 * Copyright (c) 2024 AngelLira
 * Todos os direitos reservados.
 *
 * Autor: Guilherme Ferrari Bréscia
 */
@Composable
fun DetailScreen(image: FlickrPhoto) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(image.mediaUrl),
            contentDescription = image.title,
            modifier = Modifier.fillMaxWidth()
        )
        Text(text = image.title, style = MaterialTheme.typography.titleMedium)
        Text(text = image.description, style = MaterialTheme.typography.bodySmall)
        Text(text = "Author: ${image.author}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Published: ${image.formattedPublishedDate}", style = MaterialTheme.typography.bodyMedium)
    }
}