package com.ia.flickrsearch.photo.domain.repository

import com.ia.flickrsearch.photo.data.model.FlickrItem
import com.ia.flickrsearch.photo.data.model.FlickrPhoto

/**
 * Copyright (c) 2024 AngelLira
 * Todos os direitos reservados.
 *
 * Autor: Guilherme Ferrari Bréscia
 */
interface PhotoRepository {
    suspend fun fetchPhotos(query: String): List<FlickrItem>
}