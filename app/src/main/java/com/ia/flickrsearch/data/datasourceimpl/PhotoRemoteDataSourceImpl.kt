package com.ia.flickrsearch.data.datasourceimpl

import com.ia.flickrsearch.data.model.FlickrItem
import com.ia.flickrsearch.data.model.FlickrPhoto
import com.ia.flickrsearch.data.service.PhotoService
import com.ia.flickrsearch.datasource.PhotoRemoteDataSource

/**
 * Copyright (c) 2024 AngelLira
 * Todos os direitos reservados.
 *
 * Autor: Guilherme Ferrari Bréscia
 */
class PhotoRemoteDataSourceImpl(
    private val service: PhotoService
) : PhotoRemoteDataSource {
    override suspend fun fetchPhotos(query: String): List<FlickrItem> {
        return service.fetchPhotos(query)
    }
}