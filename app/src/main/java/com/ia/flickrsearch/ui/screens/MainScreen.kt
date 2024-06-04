package com.ia.flickrsearch.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.ia.flickrsearch.R
import com.ia.flickrsearch.data.model.FlickrPhoto
import com.ia.flickrsearch.presentation.PhotoViewModel
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.compose.koinViewModel

/**
 * Copyright (c) 2024
 * Todos os direitos reservados.
 *
 * Autor: Guilherme Ferrari Bréscia
 */
@OptIn(FlowPreview::class)
@Composable
fun MainScreen(viewModel: PhotoViewModel = koinViewModel(), onImageClick: (FlickrPhoto) -> Unit) {
    val state by viewModel.state.collectAsState()
    var query by remember { mutableStateOf("") }

    Column {
        SearchBar(
            query = query,
            onQueryChanged = {
                query = it
                viewModel.updateSearchQuery(it)
            }
        )
        when {
            state.loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            state.error != null -> {
                Text(text = "Error: ${state.error}")
            }
            else -> {
                ImageGrid(images = state.items, onImageClick = onImageClick)
            }
        }
    }
}

@Composable
fun SearchBar(query: String, onQueryChanged: (String) -> Unit) {
    TextField(
        value = query,
        onValueChange = onQueryChanged,
        label = { Text("Search") },
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    )
}

@Composable
fun ImageGrid(images: List<FlickrPhoto>, onImageClick: (FlickrPhoto) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier =
        Modifier.fillMaxSize(),
    ) {
        items(images) { image ->
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .clickable { onImageClick(image) }
                    .aspectRatio(1f),
                contentAlignment = Alignment.Center
            ) {
                SubcomposeAsyncImage(
                    image.mediaUrl,
                    contentDescription = image.title,
                    contentScale = ContentScale.Fit
                ) {
                    val state = painter.state
                    if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                        CircularProgressIndicator()
                    } else {
                        SubcomposeAsyncImageContent()
                    }
                }
            }
        }
    }
}