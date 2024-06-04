package com.ia.flickrsearch.data.repository

import com.ia.flickrsearch.data.model.FlickrItem
import com.ia.flickrsearch.data.model.FlickrPhoto
import com.ia.flickrsearch.datasource.PhotoRemoteDataSource
import com.ia.flickrsearch.domain.PhotoRepository

/**
 * Copyright (c) 2024
 * Todos os direitos reservados.
 *
 * Autor: Guilherme Ferrari Bréscia
 */
class PhotoRepositoryImpl(
    private val remoteDataSource:  PhotoRemoteDataSource
) : PhotoRepository {
    override suspend fun fetchPhotos(query: String): List<FlickrItem> {
        return remoteDataSource.fetchPhotos(query)
    }
}