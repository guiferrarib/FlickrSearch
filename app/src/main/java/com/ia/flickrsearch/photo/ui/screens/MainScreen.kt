package com.ia.flickrsearch.photo.ui.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.sharp.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.ia.flickrsearch.photo.data.model.FlickrPhoto
import com.ia.flickrsearch.photo.presentation.viewmodel.PhotoViewModel
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun ImageGrid(images: List<FlickrPhoto>, onImageClick: (FlickrPhoto) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedImage by remember { mutableStateOf<FlickrPhoto?>(null) }
    val scale = animateFloatAsState(
        targetValue = if (showDialog) 1f else 0f,
        animationSpec = tween(1000),
        label = "scale"
    )
    val transition = updateTransition(showDialog, label = "scale")
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    BoxWithConstraints {
        val boxHeight = maxHeight
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxSize(),
        ) {
            items(images) { image ->
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable {
                            selectedImage = image
                            showDialog = true
                            keyboardController?.hide()
                        }
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

        if (showDialog) {
            val offsetY by transition.animateDp(
                transitionSpec = { tween(750, easing = LinearEasing) },
                label = "scale"
            ) { if (it) 0.dp else boxHeight }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable { showDialog = false },
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Top
                ) {
                    IconButton(onClick = {
                        val sendIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_STREAM, Uri.parse(selectedImage?.mediaUrl))
                            type = "image/*"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, null)
                        context.startActivity(shareIntent)
                    }) {
                        Icon(
                            Icons.Filled.Share,
                            contentDescription = "Share",
                            Modifier.size(48.dp),
                            tint = Color.White
                        )
                    }
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    SubcomposeAsyncImage(
                        selectedImage?.mediaUrl ?: "",
                        contentDescription = selectedImage?.title,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .scale(scale.value)
                            .fillMaxSize()
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

            // Box para os detalhes
            Box(
                modifier = Modifier
                    .offset(y = offsetY)
                    .background(Color.White)
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),  // Adicione um fundo branco à caixa de detalhes
                contentAlignment = Alignment.BottomCenter
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "Details",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black
                    )  // Adicione um título à caixa de detalhes
                    Text(
                        text = selectedImage?.title.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black
                    )
                    Text(
                        text = selectedImage?.description.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Black
                    )
                    Text(
                        text = "Author: ${selectedImage?.author}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black
                    )
                    Text(
                        text = "Published: ${selectedImage?.formattedPublishedDate}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black
                    )
                }
            }
        }
    }

}