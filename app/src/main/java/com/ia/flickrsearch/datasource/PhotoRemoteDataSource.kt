package com.ia.flickrsearch.datasource

import com.ia.flickrsearch.data.model.FlickrItem
import com.ia.flickrsearch.data.model.FlickrPhoto

/**
 * Copyright (c) 2024 AngelLira
 * Todos os direitos reservados.
 *
 * Autor: Guilherme Ferrari Bréscia
 */
interface PhotoRemoteDataSource {
    suspend fun fetchPhotos(query: String): List<FlickrItem>
}