package com.ia.flickrsearch.domain

import com.ia.flickrsearch.data.model.FlickrItem
import com.ia.flickrsearch.data.model.FlickrPhoto

/**
 * Copyright (c) 2024 AngelLira
 * Todos os direitos reservados.
 *
 * Autor: Guilherme Ferrari Bréscia
 */
interface PhotoRepository {
    suspend fun fetchPhotos(query: String): List<FlickrItem>
}