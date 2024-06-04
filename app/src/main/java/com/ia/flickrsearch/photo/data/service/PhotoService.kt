package com.ia.flickrsearch.photo.data.service

import com.ia.flickrsearch.photo.data.model.FlickrItem
import com.ia.flickrsearch.photo.data.model.FlickrPhoto
import com.ia.flickrsearch.photo.data.model.FlickrResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.readText
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 * Copyright (c) 2024 AngelLira
 * Todos os direitos reservados.
 *
 * Autor: Guilherme Ferrari Bréscia
 */
class PhotoService(
    private val client: HttpClient
) {
    suspend fun fetchPhotos(query: String): List<FlickrItem> {
        val response: HttpResponse = client.get("https://api.flickr.com/services/feeds/photos_public.gne?format=json&tags=$query&nojsoncallback=1")

        return Json.decodeFromString<FlickrResponse>(response.bodyAsText()).items
    }
}