package com.ia.flickrsearch.photo.data.repositoryimpl

import com.ia.flickrsearch.photo.data.model.FlickrItem
import com.ia.flickrsearch.photo.data.datasource.PhotoRemoteDataSource
import com.ia.flickrsearch.photo.domain.repository.PhotoRepository

/**
 * Copyright (c) 2024
 * Todos os direitos reservados.
 *
 * Autor: Guilherme Ferrari Bréscia
 */
class PhotoRepositoryImpl(
    private val remoteDataSource: PhotoRemoteDataSource
) : PhotoRepository {
    override suspend fun fetchPhotos(query: String): List<FlickrItem> {
        return remoteDataSource.fetchPhotos(query)
    }
}